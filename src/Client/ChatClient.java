package Client;

import Network.BaseChatConnection;
import Network.Message;


import java.io.IOException;
import java.net.Socket;

public class ChatClient {

    BaseChatConnection chatConnection;

    public ChatClient(String ip, int port) throws IOException {
        chatConnection = new BaseChatConnection(new Socket(ip, port));
        startSendingThread();
        startReceiving();
    }

    boolean isReceiving = true;

    private void startSendingThread() {
        new Thread(() -> {
            while (true) {
                try {
                    chatConnection.send();
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
