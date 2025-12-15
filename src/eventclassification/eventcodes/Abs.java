package eventclassification.eventcodes;

import java.util.HashMap;

public enum Abs implements EventCode {
    X(0x00),
    Y(0x01),
    Z(0x02),
    RX(0x03),
    RY(0x04),
    RZ(0x05),
    THROTTLE(0x06),
    RUDDER(0x07),
    WHEEL(0x08),
    GAS(0x08),
    BRAKE(0x0a),

    HAT0X(0x10),
    HAT0Y(0x11),
    HAT1X(0x12),
    HAT1Y(0x13),
    HAT2X(0x14),
    HAT2Y(0x15),
    HAT3X(0x16),
    HAT3Y(0x17),

    PRESSURE(0x18),
    DISTANCE(0x19),
    TILT_X(0x1a),
    TILT_Y(0x1b),
    TOOL_WIDTH(0x1c);    

    private final int value;
    private static final HashMap<Integer, Abs> VALUE_MAP;

    static {
        VALUE_MAP = new HashMap<>();
        for (Abs eventCode : Abs.values()) {
            VALUE_MAP.put(eventCode.getValue(), eventCode);

        }

    }

    private Abs(int value) {
        this.value = value;
    }

    @Override
    public int getValue(){
        return value;

    }

    public static Abs byValue(int value) {
        return (Abs) VALUE_MAP.get(value);
    }



}
