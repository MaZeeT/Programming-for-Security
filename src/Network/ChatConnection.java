package Network;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ChatConnection implements Runnable{
    Socket socket;
    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;
    boolean isReceiving = false;
    List<Message> returnMessages;

    public ChatConnection(Socket socket) throws IOException {
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

    public void startReceiving(List<Message> returnMessages) {
        this.returnMessages = returnMessages;
        isReceiving = true;
    }

    public void stopReceiving() {
        isReceiving = false;
    }

    @Override
    public void run() {
        while (isReceiving) {
            try {
                Message message = receive();
                returnMessages.add(message);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }
}
