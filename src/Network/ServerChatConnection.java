package Network;

import java.io.IOException;
import java.net.ServerSocket;


public class ServerChatConnection extends BaseChatConnection {
    public ServerChatConnection(int port) throws IOException {
        super(new ServerSocket(port).accept());
    }

    
}
