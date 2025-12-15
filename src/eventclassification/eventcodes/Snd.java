package eventclassification.eventcodes;

import java.util.HashMap;

public enum Snd implements EventCode {
    CLICK(0x00),
    BELL(0x01),
    TONE(0x02);    

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
