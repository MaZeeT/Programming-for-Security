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

public class ChatServer {
    ChatConnection chatConnection;
    ArrayList<ChatConnection> chatConnections;
    List<Message> messageList;
    Socket socket;

    public ChatServer(int port) throws IOException {
        messageList = new LinkedList<>();
        chatConnections = new ArrayList<>();
        acceptConnections(new ServerSocket(port));
        startSendingThread();
        // startReceiving();
        printMessages();
    }

    private void printMessages() {
        while (true) {
            if (messageList.size() > 0) {
                System.out.println(messageList.get(0));
                messageList.remove(0);
            }
        }
    }

    private void acceptConnections(ServerSocket serverSocket) {
        new Thread(() -> {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    chatConnection = new ChatConnection(socket);
                    chatConnections.add(chatConnection);
                    chatConnection.startReceiving(messageList);
                    new Thread(chatConnection);
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
                    //   chatConnection.send(message);
                    for (ChatConnection connection : chatConnections) {
                        connection.send(message);
                    }
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
            /*    Message message = chatConnections.get(0).receive();
                System.out.println(message);
*/
                for (ChatConnection connection : chatConnections) {
                    messageList.add(connection.receive());
                }
                System.out.println(messageList.size());
                for (int i = 0; i < messageList.size(); i++) {
                    System.out.println(messageList.get(0));
                    messageList.remove(0);
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }

    public void stopReceiving() {
        isReceiving = false;
    }

}
