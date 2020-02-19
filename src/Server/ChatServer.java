package Server;

import Network.BaseChatConnection;
import Network.ServerChatConnection;

import java.io.IOException;

public class ChatServer {


    BaseChatConnection chatConnection;
    public ChatServer(int port) throws IOException {
        chatConnection = new ServerChatConnection(port);
    }

    boolean keepRunning = true;
    public void run() throws IOException {
        while (keepRunning){
            chatConnection.receiveMessages();
        }
    }

}
