package Client;

import ChatEvents.*;
import Logic.*;
import Network.*;


import java.io.IOException;
import java.net.Socket;
import java.security.interfaces.RSAPublicKey;

public class ChatClient implements EventSubscriber<Message> {
    ChatConnection chatConnection;
    AESEncryption aesEncryption;
    KeyMaster keyMaster;

    public ChatClient(String ip, int port, KeyMaster keyMaster) throws IOException {
        this.chatConnection = new ChatConnection(new Socket(ip, port));
        this.aesEncryption = new AESEncryption(keyMaster.secretKey());
        this.keyMaster = keyMaster;
        EventNotifier.messageSent.subscribe(this);
        startReceiving();
    }


    private void sendMessage(Message message) {
        SignatureSigner.sign(message, keyMaster.keyPair());
        CipherMessage cMessage = aesEncryption.Encrypt(message);
        chatConnection.send(cMessage);
    }

    boolean isReceiving = true;

    public void startReceiving() {
        new Thread(() -> {
            isReceiving = true;
            while (isReceiving) {
                announceInput(chatConnection.receive());
            }
        }).start();
    }

    //CipherMessage is encrypted messages from clients typical signed, where a Message is message from the server not signed.
    private void announceInput(Object input) {
        if (input instanceof CipherMessage) {
            CipherMessage cMessage = (CipherMessage) input;
            Message message = aesEncryption.Decrypt(cMessage);
            if (verified(message)) {
                EventNotifier.messageReceived.publishEvent(message);
            }
        } else if (input instanceof Message) {
            Message message = (Message) input;
            EventNotifier.messageReceived.publishEvent(message);
        }
    }

    private boolean verified(Message message) {
        RSAPublicKey publicKey = keyMaster.publicKeyOf(message.username());
        return SignatureSigner.verify(message, publicKey);
    }

    @Override
    public void eventUpdate(Message message, String eventName) {
        sendMessage(message);
    }
}
