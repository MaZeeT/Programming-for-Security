package Client;

import ChatEvents.EventStore;
import ChatEvents.EventSubscriber;
import Network.Message;

public class ClientEventReceive implements EventSubscriber<Message> {
    public ClientEventReceive(){
        EventStore.receiveMessageEvent.subscribe(this);
    }

    @Override
    public void eventUpdate(Message message) {
        System.out.println("Event - Message received: " + message);
    }
}
