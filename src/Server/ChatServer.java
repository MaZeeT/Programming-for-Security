package Server;

import Network.BaseChatConnection;
import Network.Message;

import java.io.IOException;
import java.net.ServerSocket;

public class ChatServer {
    BaseChatConnection chatConnection;

    public ChatServer(int port) throws IOException {
        chatConnection = new BaseChatConnection(new ServerSocket(port).accept());
        startSendingThread();
    }

    boolean isReceiving = true;

    private void startSendingThread(){
        new Thread(() -> {
            while(true){
                System.out.println("hello");
            }
        } ).start();
    }

    public void startReceiving() {
        isReceiving = true;
        while (isReceiving) {
            try {
                chatConnection.receive();
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
