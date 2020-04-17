package Client;

import ChatEvents.EventNotifier;
import ChatEvents.EventSubscriber;
import Logic.AESEncryption;
import Logic.KeyMaster;
import Logic.SignatureSigner;
import Network.ChatConnection;
import Network.CipherMessage;
import Network.Message;

import java.io.IOException;
import java.net.Socket;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ChatClient implements EventSubscriber<Message> {
    ChatConnection chatConnection;
    AESEncryption aesEncryption;
    KeyMaster keyMaster;
    List<Message> messageHistory;

    public ChatClient(String ip, int port, KeyMaster keyMaster) throws IOException {
        chatConnection = new ChatConnection(new Socket(ip, port));
        aesEncryption = new AESEncryption(keyMaster.symmetricKey());
        this.keyMaster = keyMaster;

        messageHistory = new LinkedList<>();
        EventNotifier.messageSent.subscribe(this);
        startReceiving();
    }


    private void sendMessage(Message message) {
        System.out.println("Signed Message: " + SignatureSigner.sign(message, keyMaster.asymmetricKeyPair()));

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

    private void announceInput(Object input) {
        if (input instanceof CipherMessage) {
            CipherMessage cMessage = (CipherMessage) input;
            Message message = aesEncryption.Decrypt(cMessage);
            if (verified(message)) {
                EventNotifier.messageReceived.publishEvent(message);
                System.out.println(Arrays.toString(cMessage.signature()));
                messageHistory.add(message);
            }

        } else if (input instanceof Message) {
            Message message = (Message) input;
            EventNotifier.messageReceived.publishEvent(message);
            messageHistory.add(message);
        }
    }

    private boolean verified(Message message) {
        RSAPublicKey publicKey = keyMaster.publicKeyOf(message.username());

        boolean result = SignatureSigner.verify(message, publicKey);
        System.out.println("is verifing - " + result);

        return result;

    }

    public void stopReceiving() {
        isReceiving = false;
    }

    public List<Message> getMessageHistory() {
        return messageHistory;
    }

    @Override
    public void eventUpdate(Message message, String eventName) {
        sendMessage(message);
    }
}
