package devicemanagement;

import java.io.File;

import eventclassification.EventTypes;

// to be implemented
public record InputDevice(
    int[] id,
    String name,
    File physicalPath,
    File systemFileSystem,
    File handlerFile,
    EventTypes[] possibleEvents
) {}
