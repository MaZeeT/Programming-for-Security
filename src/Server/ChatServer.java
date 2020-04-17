package Server;

import ChatEvents.EventNotifier;
import ChatEvents.EventSubscriber;
import Network.ChatConnection;
import Network.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
        if (input instanceof Message) {
            Message message = (Message) input;
            sendMessage(message);
            EventNotifier.messageReceived.publishEvent(message);
        }
    }

    public void stopReceiving() {
        isReceiving = false;
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
