package eventclassification.eventcodes;

import java.util.HashMap;

public enum Sw implements EventCode {
    TEMP(0);    

    private final int value;
    private static final HashMap<Integer, Sw> VALUE_MAP;

    static {
        VALUE_MAP = new HashMap<>();
        for (Sw eventCode : Sw.values()) {
            VALUE_MAP.put(eventCode.getValue(), eventCode);

        }

    }

    private Sw(int value) {
        this.value = value;
    }

    @Override
    public int getValue(){
        return value;

    }

    public static Sw byValue(int value) {
        return (Sw) VALUE_MAP.get(value);
    }



}
