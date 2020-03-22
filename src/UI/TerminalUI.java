package UI;

import ChatEvents.EventNotifier;
import ChatEvents.EventSubscriber;
import Network.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TerminalUI implements EventSubscriber<Message> {
    private String username;

    public TerminalUI(String username) {
        this.username = username;
        EventNotifier.messageSent.subscribe(this);
        EventNotifier.messageReceived.subscribe(this);
        startTerminalInput();
    }

    @Override
    public void eventUpdate(Message message, String eventName) {
        switch (eventName) {
            case "messageSent":
                System.out.println(message);
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
