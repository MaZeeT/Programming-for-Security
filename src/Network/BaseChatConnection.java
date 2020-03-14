package Network;

import java.io.*;
import java.net.Socket;

public class BaseChatConnection {
    Socket socket;
    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;

    public BaseChatConnection(Socket socket) throws IOException {
        this.socket = socket;
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
    }

    public Message receive() throws IOException, ClassNotFoundException {
        return (Message) inputStream.readObject();
    }

    public void send(Message message) throws IOException {
        outputStream.writeObject(message);
        outputStream.flush();
    }

}
