package eventclassification.eventcodes;

import java.util.HashMap;

import eventclassification.EventId;

public enum Syn implements EventId {
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

    @Override
    public EventId fromValue(int value) {
        return VALUE_MAP.get(value);
    }



}
