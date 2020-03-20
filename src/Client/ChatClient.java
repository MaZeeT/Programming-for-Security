package Client;

import Network.ChatConnection;
import Network.Message;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class ChatClient {

    ChatConnection chatConnection;
    List<Message> messageHistory;

    public ChatClient(String ip, int port) throws IOException {
        chatConnection = new ChatConnection(new Socket(ip, port));
        messageHistory = new LinkedList<>();
        startReceiving();
    }


    public void sendMessage(Message message) {
        try {
            chatConnection.send(message);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void startSendingThread() {
        new Thread(() -> {
            while (true) {
                try {
                    BufferedReader socketRead = new BufferedReader(new InputStreamReader(System.in));
                    Message message = new Message("Client", socketRead.readLine());
                    sendMessage(message);
                } catch (IOException e) {
                    System.out.println(e.toString());
                }
            }
        }).start();
    }

    boolean isReceiving = true;

    public void startReceiving() {
        new Thread(() -> {
            isReceiving = true;
            while (isReceiving) {
                try {
                    Message message = chatConnection.receive();
                    messageHistory.add(message);
                    System.out.println(message);
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }
        }).start();
    }

    public void stopReceiving() {
        isReceiving = false;
    }

    public List<Message> getMessageHistory(){
        return messageHistory;
    }

}
