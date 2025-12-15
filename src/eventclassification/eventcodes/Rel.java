package eventclassification.eventcodes;

import java.util.HashMap;

public enum Rel implements EventCode {
    X(0x00),
    Y(0x01),
    Z(0x02),
    RX(0x03),
    RY(0x04),
    RZ(0x05),
    HWHEEL(0x06),
    DIAL(0x07),
    WHEEL(0x08),
    MISC(0x09),

    RESERVED(0x0a),
    WHEEL_HI_RES(0x0b),
    HWHEEL_HI_RES(0x0c);

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

    public static Rel byValue(int value) {
        return (Rel) VALUE_MAP.get(value);
    }


}
