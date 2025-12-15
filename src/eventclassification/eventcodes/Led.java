package eventclassification.eventcodes;

import java.util.HashMap;


public enum Led implements EventCode {
    TEMP(0);    

    private final int value;
    private static final HashMap<Integer, Led> VALUE_MAP;

    static {
        VALUE_MAP = new HashMap<>();
        for (Led eventCode : Led.values()) {
            VALUE_MAP.put(eventCode.getValue(), eventCode);

        }

    }

    private Led(int value) {
        this.value = value;
    }

    @Override
    public int getValue(){
        return value;

    }

    public static Led byValue(int value) {
        return (Led) VALUE_MAP.get(value);
    }



}
