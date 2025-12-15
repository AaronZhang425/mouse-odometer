package eventclassification.eventcodes;

import java.util.HashMap;

public enum Msc implements EventCode {
    SERIAL(0),
    PULSELED(1),
    GESTURE(2),
    RAW(3),
    SCAN(4),
    TIMESTAMP(5);    

    private final int value;
    private static final HashMap<Integer, Msc> VALUE_MAP;

    static {
        VALUE_MAP = new HashMap<>();
        for (Msc eventCode : Msc.values()) {
            VALUE_MAP.put(eventCode.getValue(), eventCode);

        }

    }

    private Msc(int value) {
        this.value = value;
    }

    @Override
    public int getValue(){
        return value;

    }

    public static Msc byValue(int value) {
        return (Msc) VALUE_MAP.get(value);
    }



}
