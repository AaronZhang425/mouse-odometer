package inputanalysis;


import eventclassification.EventCategory;
import eventclassification.EventTypes;

public record EventData(
    long[] time,
    EventTypes eventType,
    EventCategory eventCode,
    int value
) {}