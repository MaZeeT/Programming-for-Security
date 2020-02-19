package Network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

abstract class BaseSocket {
    Socket socket;

    protected BaseSocket() throws IOException {
    }

    OutputStream outputStream = socket.getOutputStream();
    PrintWriter socketSend = new PrintWriter(outputStream, true);

    public void sendMessage(String message) {
        socketSend.println(message);
        socketSend.flush();
    }

    InputStream inputStream = socket.getInputStream();
    BufferedReader socketRead = new BufferedReader(new InputStreamReader(inputStream));

    public String receiveMessages() throws IOException {
        return socketRead.readLine();
    }

}
