package Server;

import ChatEvents.EventNotifier;
import ChatEvents.EventSubscriber;
import Network.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChatConsoleUI implements EventSubscriber<Message> {
    public ChatConsoleUI(){
        EventNotifier.messageReceived.subscribe(this);
        startTerminalInput();
    }

    @Override
    public void eventUpdate(Message Message, String eventName) {
        System.out.println(Message);
    }

    private void startTerminalInput(){
        new Thread(() -> {
            while (true) {
                try {
                    BufferedReader socketRead = new BufferedReader(new InputStreamReader(System.in));
                    Message message = new Message("Server", socketRead.readLine());
                    EventNotifier.messageReceived.publishEvent(message);
                } catch (IOException e) {
                    System.out.println(e.toString());
                }
            }
        }).start();
    }
}
