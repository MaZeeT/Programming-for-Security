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
        startReceiving();
    }

    boolean isReceiving = true;

    private void startSendingThread(){
        new Thread(() -> {
            while(true){
                try {
                    chatConnection.send();
                } catch (IOException e) {
                    System.out.println(e.toString());
                }
            }
        } ).start();
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
