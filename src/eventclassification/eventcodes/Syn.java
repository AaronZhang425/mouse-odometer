package eventclassification.eventcodes;

import java.util.HashMap;

public enum Syn implements EventCode {
    REPORT(0),
    CONFIG(1),
    MT_REPORT(2),
    DROPPED(3);    

    private final int value;
    private static final HashMap<Integer, Syn> VALUE_MAP;

    static {
        VALUE_MAP = new HashMap<>();
        for (Syn eventCode : Syn.values()) {
            VALUE_MAP.put(eventCode.getValue(), eventCode);

        }

    }

    private Syn(int value) {
        this.value = value;
    }

    @Override
    public int getValue(){
        return value;

    }

    public static Syn byValue(int value) {
        return (Syn) VALUE_MAP.get(value);
    }



}
