public enum EventTypes {
    EV_SYN(0x00) {
        enum EventCodes {
            SYN_REPORT(0),
            SYN_CONFIG(1),
            SYN_MT_REPORT(2),
            SYN_DROPPED(3),
            SYN_MAX(4),
            SYN_CNT(EventCodes.SYN_MAX.getEventCodeValue() + 1);

            int eventCodeValue;

            EventCodes(int eventCodeValue) {
                this.eventCodeValue = eventCodeValue;
            }

            int getEventCodeValue() {
                return eventCodeValue;
            }
        }

    },

    EV_KEY(0x01) {
        enum EventCodes {
            
        }
    },

    EV_REL(0x02) {
        enum EventCodes {
            REL_X(0),
            REL_Y(1),
            REL_Z(2),
            REL_RX(3),
            REL_RY(4),
            REL_RZ(5),
            REL_HWWEEL(6),
            REL_DIAL(7),
            REL_WHEEL(8),
            REL_MISC(9),
            REL_RESERVED(10),
            REL_WHEEL_HI_RES(11),
            REL_HWEEL_HI_RES(12),
            REL_MAX(16),
            REL_CNT(EventCodes.REL_MAX.getEventCodeValue() + 1);

            int eventCodeValue;

            EventCodes(int eventCodeValue) {
                this.eventCodeValue = eventCodeValue;
            }

            int getEventCodeValue() {
                return eventCodeValue;
            }           
        }
    },

    EV_ABS(0x03) {
        enum EventCodes {
            
        }
    },

    EV_MSC(0x04) {
        enum EventCodes {
            
        }
    },

    EV_SW(0x05) {
        enum EventCodes {
            
        }
    },

    EV_LED(0x11) {
        enum EventCodes {
            
        }
    },
    
    EV_SND(0x12) {
        enum EventCodes {
            
        }
    },
    
    EV_REP(0x14) {
        enum EventCodes {
            
        }
    },
    
    EV_FF(0x15) {
        enum EventCodes {
            
        }
    },
    
    EV_PWR(0x16) {
        enum EventCodes {
            
        }
    },
    
    EV_FF_STATUS(0x17) {
        enum EventCodes {
            
        }
    },
    
    EV_MAX(0x1f) {
        enum EventCodes {
            
        }
    },
    
    EV_CNT(EventTypes.EV_MAX.getEventCode() + 1) {
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

    public EventTypes getEventTypeByValue(int value) {
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
