package devicemanagement;

import eventclassification.EventTypes;
import eventclassification.eventcodes.EventCode;

public record EventData(
    long[] time,
    EventTypes eventType,
    EventCode eventCode,
    int value
) {}