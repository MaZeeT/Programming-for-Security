package ChatEvents;

public interface EventSubscriber<T> {
    void eventUpdate(T event, String eventName);
}
