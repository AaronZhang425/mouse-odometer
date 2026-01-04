package devicemanagement;

import eventclassification.EventTypes;
import eventclassification.eventcodes.EventCode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class InputReader {
    // Input file is intialized in the constructor
    // Represents the pseudofile that contains the input events of a device
    private File inputFile;

    public InputReader(int eventNum) {
        this(new File("/dev/input/event" + eventNum));

    }

    public InputReader(String filePath) {
        this(new File(filePath));
    }

    public InputReader(File file) {
        inputFile = file;
    }

    public EventData getEventData() {
        byte[] buffer = eventFileReader();

        // Byte order of the buffer is assumed to be little endian
        long[] time = getEventTime(buffer);
        
        // Event types are represented by bytes 16-17 and are unsigned
        int eventTypeValue = ByteArrayConverson.toInt(buffer, 17, 16);
        
        // Event codes are represented by bytes 18-19 and are unsigned
        int eventCodeValue = ByteArrayConverson.toInt(buffer, 19, 18);

        // The value of the event are represented by bytes 20-23 and are signed
        int value = ByteArrayConverson.toInt(buffer, 23, 20);


        // Get event type constant by the event value obtained from event
        EventTypes eventType = EventTypes.byValue(eventTypeValue);

        // Get event code based on the event code value and the event type 
        EventCode eventCode = eventType.eventCodeByValue(eventCodeValue);

        return new EventData(
            time,
            eventType,
            eventCode,
            value
        );

    }

    private long[] getEventTime(byte[] buffer) {
        // bytes 8-15 represent fractions of a second in microseconds
        // assumed to be little endian (least significant byte on the left)
        long microSeconds = ByteArrayConverson.toLong(
            buffer,
            15,
            8
        );

        // bytes 0-7 represent unix time in seconds
        // assumed to be little endian (least significant byte on the left)
        long seconds = ByteArrayConverson.toLong(
            buffer,
            7,
            0
        );

        // put the unix seconds and microseconds representing fractions of a 
        // second as a long[] array and return
        long[] time = {seconds, microSeconds};
        return time;
    }
    
    public byte[] eventFileReader() {
        // Each event is composed of 24 bytes and writes bytes to buffer
        byte[] buffer = new byte[24];
        
        // try to get the event data from the file
        try (FileInputStream reader = new FileInputStream(inputFile)) {
            reader.read(buffer);
            
        } catch (IOException error) {
            System.out.println(error);
            
        }
        
        // return the event data
        return buffer;
                    
    }

}