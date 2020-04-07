package Client;

import ChatEvents.EventNotifier;
import ChatEvents.EventSubscriber;
import Network.ChatConnection;
import Network.Message;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class ChatClient implements EventSubscriber<Message> {
    ChatConnection chatConnection;
    List<Message> messageHistory;

    public ChatClient(String ip, int port) throws IOException {
        chatConnection = new ChatConnection(new Socket(ip, port));
        messageHistory = new LinkedList<>();
        EventNotifier.messageSent.subscribe(this);
        startReceiving();
    }


    private void sendMessage(Message message) {
        try {
            chatConnection.send(message);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    boolean isReceiving = true;

    public void startReceiving() {
        new Thread(() -> {
            isReceiving = true;
            while (isReceiving) {
                try {
                    Message message = chatConnection.receive();
                    EventNotifier.messageReceived.publishEvent(message);
                    messageHistory.add(message);
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }
        }).start();
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
