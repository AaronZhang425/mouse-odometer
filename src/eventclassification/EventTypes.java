package eventclassification;

import java.util.HashMap;

import eventclassification.eventcodes.*;

public enum EventTypes implements EventCategory{
    SYN(0x00),
    KEY(0x01),
    REL(0x02),
    ABS(0x03),
    MSC(0x04),
    SW(0x05),
    LED(0x11),
    SND(0x12),
    REP(0x14),
    FF(0x15),
    PWR(0x16),
    FF_STATUS(0x17);

    private final int value;
    private static final HashMap<Integer, EventTypes> VALUE_MAP;

    static {
        VALUE_MAP = new HashMap<>();
        for (EventTypes eventCode : EventTypes.values()) {
            VALUE_MAP.put(eventCode.getValue(), eventCode);

        }

    }

    private EventTypes(int value) {
        this.value = value;
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

    
    public static EventCategory fromValue(int value) {
        return VALUE_MAP.get(value);
    }

}
