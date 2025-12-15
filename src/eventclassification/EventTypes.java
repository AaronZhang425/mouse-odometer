package eventclassification;

import eventclassification.eventcodes.*;
import java.util.HashMap;
import java.util.function.Function;

public enum EventTypes implements EventCategory{
    SYN(0x00),
    KEY(0x01),
    REL(0x02),
    ABS(0x03),
    MSC(0x04),
    SW(0x05),
    LED(0x11),
    SND(0x12),
    REP(0x14);
    // FF(0x15),
    // PWR(0x16),
    // FF_STATUS(0x17);

    // public abstract EventCategory getEventCodeSet();

    private final int VALUE;
    // private final Class<? extends EventCategory> eventCodeSet;
    private static final HashMap<Integer, EventTypes> VALUE_MAP;
    private static final HashMap<EventTypes, Function<Integer, EventCode>> EVENTCODES_MAP;

    static {
        VALUE_MAP = new HashMap<>();
        for (EventTypes eventCode : EventTypes.values()) {
            VALUE_MAP.put(eventCode.getValue(), eventCode);

        }

        // methods manually put in to avoid java reflection due to
        // memory overhead

        EVENTCODES_MAP = new HashMap<>();

        // SYN
        EVENTCODES_MAP.put(
            EventTypes.SYN, num -> {
                return Syn.byValue(num);
            }
        );

        // KEY
        EVENTCODES_MAP.put(
            EventTypes.KEY, num -> {
                return Key.byValue(num);
            }
        );

        // REL
        EVENTCODES_MAP.put(
            EventTypes.REL, num -> {
                return Rel.byValue(num);
            }
        );

        // ABS
        EVENTCODES_MAP.put(
            EventTypes.ABS, num -> {
                return Abs.byValue(num);
            }
        );

        // MSC
        EVENTCODES_MAP.put(
            EventTypes.MSC, num -> {
                return Msc.byValue(num);
            }
        );

        // SW
        EVENTCODES_MAP.put(
            EventTypes.SW, num -> {
                return Sw.byValue(num);
            }
        );

        // LED
        EVENTCODES_MAP.put(
            EventTypes.LED, num -> {
                return Led.byValue(num);
            }
        );

        // SND
        EVENTCODES_MAP.put(
            EventTypes.SND, num -> {
                return Snd.byValue(num);
            }
        );

        // REP
        EVENTCODES_MAP.put(
            EventTypes.REP, num -> {
                return Rep.byValue(num);
            }
        );

        // Could be implemented for wider support
        // // FF
        // EVENTCODES_MAP.put(
        //     EventTypes.FF, num -> {
        //         return FF.byValue(num);
        //     }
        // );

        // // PWR
        // EVENTCODES_MAP.put(
        //     EventTypes.PWR, num -> {
        //         return PWR.byValue(num);
        //     }
        // );


    }

    private EventTypes(int value) {
        this.VALUE = value;
        // this.eventCodeSet = eventCodeSet;
    }

    @Override
    public int getValue() {
        return VALUE;
    }

    public EventCode eventCodeByValue(int num) {
        return EVENTCODES_MAP.get(this).apply(num);
    }

    
    public static EventTypes byValue(int value) {
        return (EventTypes) VALUE_MAP.get(value);
    }

}
