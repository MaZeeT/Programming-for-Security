package ChatEvents;

public interface EventPublisher<T> {
    void publishEvent(T event);

    void subscribe(EventSubscriber<T> subscriber);

}
