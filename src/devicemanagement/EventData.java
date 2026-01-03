package devicemanagement;

import eventclassification.EventTypes;
import eventclassification.eventcodes.EventCode;

public record EventData (
    long[] time,
    EventTypes eventType,
    EventCode eventCode,
    int value
) {

    public EventData() {
        long[] defaultTime = {0, 0};
        this(defaultTime, null, null, 0);
    }

    public String toString() {
        return (
            "Input event info: " + "\n" +
            "Seconds: " + time[0] + "\n" +
            "Microseconds: " + time[1] + "\n" +
            "Event type: " + eventType + "\n" +
            "Event code: " + eventCode + "\n" +
            "Value: " + value + "\n"
        );

    }

}