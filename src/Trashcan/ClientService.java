package Trashcan;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientService {
    Socket socket;

    public ClientService(int port) throws IOException {
        socket = new Socket("127.0.0.1", port);
    }

    public ClientService(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
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
