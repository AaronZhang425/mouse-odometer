package eventclassification;

import java.util.HashMap;

import eventclassification.eventcodes.*;

public enum EventTypes implements EventCategory{
    SYN(0x00, Syn.class),
    KEY(0x01, Key.class),
    REL(0x02, Rel.class),
    ABS(0x03, Abs.class),
    MSC(0x04, Msc.class),
    SW(0x05, Sw.class),
    LED(0x11, Led.class),
    SND(0x12, Snd.class),
    REP(0x14, Rep.class),
    FF(0x15, null),
    PWR(0x16, null),
    FF_STATUS(0x17, null);

    // public abstract EventCategory getEventCodeSet();

    private final int value;
    private final Class<?> eventCodeSet;
    private static final HashMap<Integer, EventTypes> VALUE_MAP;

    static {
        VALUE_MAP = new HashMap<>();
        for (EventTypes eventCode : EventTypes.values()) {
            VALUE_MAP.put(eventCode.getValue(), eventCode);

        }

    }

    private EventTypes(int value, Class<?> eventCodeSet) {
        this.value = value;
        this.eventCodeSet = eventCodeSet;
    }
    
    // public static EventTypes getEventTypeByValue(int value) {
    //     for (EventTypes eventCode : EventTypes.values()) {
    //         if (eventCode.getValue() == value) {
    //             return eventCode;
    //         }
    //     }

    //     return EventTypes.NONE;
        
    // }

    @Override
    public int getValue() {
        return value;
    }

    
    public static EventTypes fromValue(int value) {
        return (EventTypes) VALUE_MAP.get(value);
    }

}
