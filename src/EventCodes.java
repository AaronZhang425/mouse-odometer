public enum EventCodes {
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
    CNT(EventCodes.MAX.getEventCode() + 1),
    NONE(-1);
    
    private int eventCodeValue;

    private EventCodes(int eventCodeValue) {
        this.eventCodeValue = eventCodeValue;
    }

    private EventCodes(byte[] arr) {
        eventCodeValue = ByteArrayConverson.toInt(arr);
    }

    private EventCodes(byte[] arr, int startIdx, int endIdx) {
        eventCodeValue = ByteArrayConverson.toInt(arr, startIdx, endIdx);
    }

    public EventCodes getEventCodeByValue(int value) {
        for (EventCodes eventCode : EventCodes.values()) {
            if (eventCode.getEventCode() == value) {
                return eventCode;
            }
        }

        return EventCodes.NONE;
        
    }

    public int getEventCode() {
        return eventCodeValue;
    }

}
