import EventCodes.EventCode;

public record EventData(
    Time time,
    EventTypes eventType,
    EventCode eventCode,
    int value
) {}