package ChatEvents;

import Network.Message;

public class EventNotifier {
    public static EventPublisher<Message> messageReceived = new Publisher<>();
    public static EventPublisher<Message> messageSent = new Publisher<>();

}
