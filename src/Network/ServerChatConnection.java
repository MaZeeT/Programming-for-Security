package Network;

import java.io.IOException;

public class ServerChatConnection extends BaseChatConnection {
    public ServerChatConnection(int port) throws IOException {
        socket = new java.net.ServerSocket(port).accept();
    }
}
