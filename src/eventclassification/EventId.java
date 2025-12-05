package eventclassification;

public interface EventId {
    int getValue();
    EventId fromValue(int value);
    
}