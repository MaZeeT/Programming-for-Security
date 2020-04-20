package ChatEvents;

import java.util.*;

public class Publisher<T> implements EventPublisher<T> {
    private List<EventSubscriber<T>> subscribers = new LinkedList<>();
    String eventName = "event";

    public Publisher() {
    }

    public Publisher(String eventName) {
        this.eventName = eventName;
    }


    public void subscribe(EventSubscriber<T> subscriber) {
        this.subscribers.add(subscriber);
    }

    @Override
    public void publishEvent(Object publishEvent) {
        try {
            T event = (T) publishEvent;
            for (EventSubscriber<T> subscriber : subscribers) {
                subscriber.eventUpdate(event, eventName);
            }
        } catch (ClassCastException e) {
            System.out.println("CastException at Publisher");
        }
    }

}
