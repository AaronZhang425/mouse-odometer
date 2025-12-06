package eventclassification;

import java.util.HashMap;

public interface EventCategory {
    public HashMap<Integer, EventCategory> VALUE_MAP = new HashMap<>();

    public int getValue();

    public static EventCategory fromValue(int value) {
        return VALUE_MAP.get(value);
    }
    
}