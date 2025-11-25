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
    CNT(EventCodes.MAX.getEventCode() + 1);
    
    private final int eventCode;

    private EventCodes(int eventCode) {
        this.eventCode = eventCode;
    }

    public int getEventCode() {
        return eventCode;
    }

}
