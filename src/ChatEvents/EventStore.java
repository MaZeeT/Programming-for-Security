package ChatEvents;

import Network.Message;

public class EventStore {
    public static EventPublisher<Message> receiveMessageEvent = new Publisher<>();

}
