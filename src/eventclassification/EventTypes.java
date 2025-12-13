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
    private static final HashMap<EventTypes, Function<Integer, EventCategory>> EVENTCODES;

    static {
        VALUE_MAP = new HashMap<>();
        for (EventTypes eventCode : EventTypes.values()) {
            VALUE_MAP.put(eventCode.getValue(), eventCode);

        }

        EVENTCODES = new HashMap<>();

        // methods manually put in to avoid java reflection due to
        // memory overhead

        // SYN
        EVENTCODES.put(
            EventTypes.SYN, num -> {
                return Syn.fromValue(num);
            }
        );

        // KEY
        EVENTCODES.put(
            EventTypes.KEY, num -> {
                return Key.fromValue(num);
            }
        );

        // REL
        EVENTCODES.put(
            EventTypes.REL, num -> {
                return Rel.fromValue(num);
            }
        );

        // ABS
        EVENTCODES.put(
            EventTypes.ABS, num -> {
                return Abs.fromValue(num);
            }
        );

        // MSC
        EVENTCODES.put(
            EventTypes.MSC, num -> {
                return Msc.fromValue(num);
            }
        );

        // SW
        EVENTCODES.put(
            EventTypes.SW, num -> {
                return Sw.fromValue(num);
            }
        );

        // LED
        EVENTCODES.put(
            EventTypes.LED, num -> {
                return Led.fromValue(num);
            }
        );

        // SND
        EVENTCODES.put(
            EventTypes.SND, num -> {
                return Snd.fromValue(num);
            }
        );

        // REP
        EVENTCODES.put(
            EventTypes.REP, num -> {
                return Rep.fromValue(num);
            }
        );

        // Could be implemented for wider support
        // // FF
        // EVENTCODES.put(
        //     EventTypes.FF, num -> {
        //         return FF.fromValue(num);
        //     }
        // );

        // // PWR
        // EVENTCODES.put(
        //     EventTypes.PWR, num -> {
        //         return PWR.fromValue(num);
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

    
    public static EventTypes fromValue(int value) {
        return (EventTypes) VALUE_MAP.get(value);
    }

}
