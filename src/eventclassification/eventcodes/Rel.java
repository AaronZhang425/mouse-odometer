package eventclassification.eventcodes;

import eventclassification.EventId;
import java.util.HashMap;

public enum Rel implements EventId {
    X(0),
    Y(1),
    Z(2),
    RX(3),
    RY(4),
    RZ(5),
    HWHEEL(6),
    DIAL(7),
    WHEEL(8),
    MISC(9),
    RESERVED(10),
    WHEEL_HI_RES(11),
    HWHEEL_HI_RES(12);

    private final int value;
    private static final HashMap<Integer, Rel> VALUE_MAP;

    static {
        VALUE_MAP = new HashMap<>();
        for (Rel eventCode : Rel.values()) {
            VALUE_MAP.put(eventCode.getValue(), eventCode);

        }

    }

    private Rel(int value) {
        this.value = value;
    }

    @Override
    public int getValue(){
        return value;

    }           

    public static EventId fromValue(int value) {
        return VALUE_MAP.get(value);
    }


}
