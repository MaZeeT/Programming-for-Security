package Network;

import java.io.IOException;
import java.net.Socket;

public class ClientChatConnection extends BaseChatConnection {
    public ClientChatConnection(int port) throws IOException {
        socket = new Socket("127.0.0.1", port);
    }

    public ClientChatConnection(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
    }
}
