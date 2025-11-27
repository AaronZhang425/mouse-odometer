public enum EventTypes {
    SYN(0x00),
    KEY(0x01),
    REL(0x02),
    ABS(0x03),
    MSC(0x04),
    SW(0x05),
    LED(0x11),
    SND(0x12),
    REP(0x14),
    FF(0x15),
    PWR(0x16),
    FF_STATUS(0x17),
    MAX(0x1f),
    CNT(EventTypes.MAX.getEventCode() + 1),
    NONE(-1);
    
    private int eventTypeValue;

    private EventTypes(int eventTypeValue) {
        this.eventTypeValue = eventTypeValue;
    }

    private EventTypes(byte[] arr) {
        eventTypeValue = ByteArrayConverson.toInt(arr);
    }

    private EventTypes(byte[] arr, int startIdx, int endIdx) {
        eventTypeValue = ByteArrayConverson.toInt(arr, startIdx, endIdx);
    }

    public EventTypes getEventCodeByValue(int value) {
        for (EventTypes eventCode : EventTypes.values()) {
            if (eventCode.getEventCode() == value) {
                return eventCode;
            }
        }

        return EventTypes.NONE;
        
    }

    public int getEventCode() {
        return eventTypeValue;
    }

}
