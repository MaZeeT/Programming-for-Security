package Network;

import java.io.IOException;
import java.net.Socket;

public class ClientSocket extends BaseSocket {
    public ClientSocket(int port) throws IOException {
        socket = new Socket("127.0.0.1", port);
    }

    public ClientSocket(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
    }
}
