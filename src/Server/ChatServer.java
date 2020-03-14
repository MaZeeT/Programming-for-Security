package Server;

import Network.ChatConnection;
import Network.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;

public class ChatServer {
    ChatConnection chatConnection;

    public ChatServer(int port) throws IOException {
        chatConnection = new ChatConnection(new ServerSocket(port).accept());
        startSendingThread();
        startReceiving();
    }

    boolean isReceiving = true;

    private void startSendingThread() {
        new Thread(() -> {
            while (true) {
                try {
                    BufferedReader socketRead = new BufferedReader(new InputStreamReader(System.in));
                    Message message = new Message("Console", socketRead.readLine());
                    sendMessage(message);
                } catch (IOException e) {
                    System.out.println(e.toString());
                }
            }
        }).start();
    }

    public void startReceiving() {
        isReceiving = true;
        while (isReceiving) {
            try {
                Message message = chatConnection.receive();
                System.out.println(message);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }

    public void stopReceiving() {
        isReceiving = false;
    }

    public void sendMessage(Message message) {
        try {
            chatConnection.send(message);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}
