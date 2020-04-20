package Server;

import ChatEvents.*;
import Network.*;

import java.io.IOException;
import java.net.*;

public class ChatServer implements EventSubscriber<Message> {
    ChatConnection chatConnection;

    public ChatServer(int port) throws IOException {
        EventNotifier.messageSent.subscribe(this);
        EventNotifier.messageReceived.subscribe(this);
        acceptConnections(new ServerSocket(port));
    }

    private void acceptConnections(ServerSocket serverSocket) {
        new Thread(() -> {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    chatConnection = new ChatConnection(socket);
                    startReceiving();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void sendMessage(Object message) {
        chatConnection.send(message);
    }

    public void startReceiving() {
        new Thread(() -> {
            while (true) {
                announceInput(chatConnection.receive());
            }
        }).start();
    }

    private void announceInput(Object input) {
        if (input instanceof Message) {
            Message message = (Message) input;
            sendMessage(message);
            EventNotifier.messageReceived.publishEvent(message);
        }
    }

    @Override
    public void eventUpdate(Message message, String eventName) {
        switch (eventName) {
            case "messageSent":
                sendMessage(message);
                break;
            case "messageReceived":
                //TODO Not in use, could maybe be refactored to simplify EventSystem
                //EventNotifier.messageSent.publishEvent(message);
                break;
            default:
                break;
        }
    }

}
