package Network;

import java.io.IOException;

public class ServerSocket extends BaseSocket {
    public ServerSocket(int port) throws IOException {
        socket = new java.net.ServerSocket(port).accept();
    }
}
