package Trashcan;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerService {
    Socket socket;

    public ServerService(int port) throws IOException {
        socket = new ServerSocket(port).accept();
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
