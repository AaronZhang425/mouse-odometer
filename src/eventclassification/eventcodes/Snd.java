package eventclassification.eventcodes;

import java.util.HashMap;

public enum Snd implements EventCode {
    TEMP(0);    

    private final int value;
    private static final HashMap<Integer, Snd> VALUE_MAP;

    static {
        VALUE_MAP = new HashMap<>();
        for (Snd eventCode : Snd.values()) {
            VALUE_MAP.put(eventCode.getValue(), eventCode);

        }

    }

    private Snd(int value) {
        this.value = value;
    }

    @Override
    public int getValue(){
        return value;

    }

    public static Snd byValue(int value) {
        return (Snd) VALUE_MAP.get(value);
    }



}
