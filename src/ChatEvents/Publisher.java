package ChatEvents;

import java.util.LinkedList;
import java.util.List;

public class Publisher<T> implements EventPublisher<T> {
    private List<EventSubscriber<T>> subscribers = new LinkedList<>();

    public void subscribe(EventSubscriber<T> subscriber) {
        this.subscribers.add(subscriber);
    }

    @Override
    public void publishEvent(Object event) {
        try {
            T e = (T) event;
            for (EventSubscriber<T> subscriber : subscribers) {
                subscriber.eventUpdate(e);
            }
        } catch (ClassCastException e) {
            System.out.println("CastException at Publisher");
        }
    }
}
