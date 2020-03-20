package ChatEvents;

public interface EventPublisher<T> {
    void publishEvent(T event);

}
