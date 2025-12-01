import EventCodes.EventCode;

public record EventData(
    long[] time,
    EventTypes eventType,
    EventCode eventCode,
    int value
) {}