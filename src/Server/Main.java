package Server;

import Network.ServerChatConnection;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello world, I am mr. server");
        ChatServer server = new ChatServer(3000);
        Thread thread = new Thread(server);
        thread.start();
    }
}
