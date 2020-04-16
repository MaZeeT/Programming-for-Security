package ChatEvents;

import Network.Message;

public class EventNotifier {
    public static EventPublisher<Message> messageReceived = new Publisher<>("messageReceived");
    public static EventPublisher<Message> messageSent = new Publisher<>("messageSent");

    //todo are custom eventHandling still useful or doesn't it solve the problem it is intended for.
}
