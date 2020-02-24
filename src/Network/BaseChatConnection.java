package Network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class BaseChatConnection {
    Socket socket;
    OutputStream outputStream;
    PrintWriter socketSend;

    InputStream inputStream;
    BufferedReader socketRead;

    protected BaseChatConnection(Socket socket) throws IOException {
        this.socket = socket;
        outputStream = socket.getOutputStream();
        socketSend = new PrintWriter(outputStream, true);

        inputStream = socket.getInputStream();
        socketRead = new BufferedReader(new InputStreamReader(inputStream));
    }

    public void sendMessage(String message) {
        socketSend.println(message);
        socketSend.flush();
    }


    public String receiveMessages() throws IOException {
        return socketRead.readLine();
    }

}
