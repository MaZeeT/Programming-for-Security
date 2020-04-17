package Network;

import java.io.*;
import java.net.Socket;

public class ChatConnection {
    Socket socket;
    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;

    public ChatConnection(Socket socket) throws IOException {
        this.socket = socket;
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
    }

    public Object receive() {
        try {
            return inputStream.readObject();
        } catch (Exception e) {
            return null;
        }
    }

    public void send(Object object) {
        try {
            outputStream.writeObject(object);
            outputStream.flush();
        } catch (IOException ignored) {
        }
    }

}
