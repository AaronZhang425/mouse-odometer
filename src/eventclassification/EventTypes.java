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

    private final int VALUE;
    // private final Class<? extends EventCategory> eventCodeSet;
    private static final HashMap<Integer, EventTypes> VALUE_MAP;
    private static final HashMap<EventTypes, EventCategory> EVENTCODES;

    static {
        VALUE_MAP = new HashMap<>();
        for (EventTypes eventCode : EventTypes.values()) {
            VALUE_MAP.put(eventCode.getValue(), eventCode);

        }

        EVENTCODES = new HashMap<>();

    }

    private EventTypes(int value, Class<? extends EventCategory> eventCodeSet) {
        this.VALUE = value;
        // this.eventCodeSet = eventCodeSet;
    }

    // public Class<? extends EventCategory> getEventCodeSet() {
    //     return eventCodeSet;
    // }

    @Override
    public int getValue() {
        return VALUE;
    }

    
    public static EventTypes fromValue(int value) {
        return (EventTypes) VALUE_MAP.get(value);
    }

}
