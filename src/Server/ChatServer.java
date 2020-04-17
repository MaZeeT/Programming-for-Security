package Server;

import ChatEvents.EventNotifier;
import ChatEvents.EventSubscriber;
import Network.ChatConnection;
import Network.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class ChatServer implements EventSubscriber<Message> {
    ChatConnection chatConnection;
    List<Message> messageHistory;

    public ChatServer(int port) throws IOException {
        messageHistory = new LinkedList<>();
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
                    System.out.println("hello");
                }
            }
        }).start();
    }

    public void sendMessage(Message message) {
        chatConnection.send(message);
    }

    boolean isReceiving = true;

    public void startReceiving() {
        new Thread(() -> {
            isReceiving = true;
            while (isReceiving) {
                Message message = (Message) chatConnection.receive();
                if (!messageHistory.contains(message)) {
                    messageHistory.add(message);
                    EventNotifier.messageReceived.publishEvent(message);
                }
            }
        }).start();
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
                EventNotifier.messageSent.publishEvent(message);
                break;
            default:
                break;
        }
    }
}
