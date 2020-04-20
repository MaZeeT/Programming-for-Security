package UI;

import ChatEvents.*;
import Network.Message;

import java.io.*;

public class TerminalUI implements UI, EventSubscriber<Message> {
    private String username;

    public TerminalUI(String username) {
        this.username = username;
        EventNotifier.messageSent.subscribe(this);
        EventNotifier.messageReceived.subscribe(this);
        System.out.println("Hello world, I am mr. " + username);
        startTerminalInput();
    }

    @Override
    public void eventUpdate(Message message, String eventName) {
        switch (eventName) {
            case "messageSent":
                //Special case to let the server see its one messages since the clients will not echo the message back as the server will for the clients.
                if (message.username().equals("ServerTerminal")) System.out.println(message);
                break;
            case "messageReceived":
                System.out.println(message);
                break;
            default:
                break;
        }
    }

    private void startTerminalInput() {
        new Thread(() -> {
            while (true) {
                try {
                    BufferedReader socketRead = new BufferedReader(new InputStreamReader(System.in));
                    Message message = new Message(username, socketRead.readLine());
                    EventNotifier.messageSent.publishEvent(message);
                } catch (IOException e) {
                    System.out.println(e.toString());
                }
            }
        }).start();
    }

}
