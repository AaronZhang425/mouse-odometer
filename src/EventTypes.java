public enum EventTypes {
    SYN(0x00) {
        enum EventCodes {

        }

    },

    KEY(0x01) {
        enum EventCodes {
            
        }
    },

    REL(0x02) {
        enum EventCodes {
            
        }
    },

    ABS(0x03) {
        enum EventCodes {
            
        }
    },

    MSC(0x04) {
        enum EventCodes {
            
        }
    },

    SW(0x05) {
        enum EventCodes {
            
        }
    },

    LED(0x11) {
        enum EventCodes {
            
        }
    },
    
    SND(0x12) {
        enum EventCodes {
            
        }
    },
    
    REP(0x14) {
        enum EventCodes {
            
        }
    },
    
    FF(0x15) {
        enum EventCodes {
            
        }
    },
    
    PWR(0x16) {
        enum EventCodes {
            
        }
    },
    
    FF_STATUS(0x17) {
        enum EventCodes {
            
        }
    },
    
    MAX(0x1f) {
        enum EventCodes {
            
        }
    },
    
    CNT(EventTypes.MAX.getEventCode() + 1) {
        enum EventCodes {
            
        }
    },
    
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
