package Server;

import Network.BaseChatConnection;
import Network.ServerChatConnection;

import java.io.IOException;

public class ChatServer implements Runnable {


    BaseChatConnection chatConnection;
    public ChatServer(int port) throws IOException {
        chatConnection = new ServerChatConnection(port);
    }

    boolean keepRunning = true;
    public void run(){
        keepRunning = true;
        while (keepRunning){
            try{
                chatConnection.receiveMessages();
            }catch (Exception e){
                System.out.println(e.toString());
            }
        }
    }

    public void pause(){
        keepRunning = false;
    }

}
