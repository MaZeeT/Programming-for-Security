package Server;

import Network.ChatConnection;
import Network.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ChatServerMessagePassOn {
    ChatConnection chatConnection;
    List<Message> messageHistory;

    public ChatServerMessagePassOn(int port) throws IOException {
        messageHistory = new LinkedList<>();
        acceptConnections(new ServerSocket(port));
    }

    private void acceptConnections(ServerSocket serverSocket) {
        new Thread(() -> {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    chatConnection = new ChatConnection(socket);
                    startReceiving();
                    startSendingThread();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("hello");
                }
            }
        }).start();
    }

    boolean isReceiving = true;

    private void startSendingThread() {
        new Thread(() -> {
            while (true) {
                try {
                    BufferedReader socketRead = new BufferedReader(new InputStreamReader(System.in));
                    Message message = new Message("Console", socketRead.readLine());
                    sendMessage(message);
                    System.out.println("From " + message);

                } catch (IOException e) {
                    System.out.println(e.toString());
                }
            }
        }).start();
    }

    private void sendMessage(Message message) throws IOException {
        chatConnection.send(message);
    }

    public void startReceiving() {
        new Thread(() -> {
        isReceiving = true;
        while (isReceiving) {
            try {
                Message message = chatConnection.receive();
                System.out.println("Received: " + message);

                messageHistory.add(message);
                chatConnection.send(message);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        }).start();
    }

    public void stopReceiving() {
        isReceiving = false;
    }

}
