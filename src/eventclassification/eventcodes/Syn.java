package eventclassification.eventcodes;

public enum Syn implements EventCode {
    REPORT(0),
    CONFIG(1),
    MT_REPORT(2),
    DROPPED(3);    

    private int eventCodeValue;

    private Syn(int eventCodeValue) {
        this.eventCodeValue = eventCodeValue;
    }

    @Override
    public int getEventCodeValue(){
        return eventCodeValue;

    }



}
