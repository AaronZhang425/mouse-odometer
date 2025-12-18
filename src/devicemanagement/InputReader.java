package devicemanagement;

import eventclassification.EventTypes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class InputReader {
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

        for (byte elem : buffer) {
            System.out.print(elem + " ");
        }
        
        System.out.println();

        int eventTypeValue = ByteArrayConverson.toInt(buffer, 17, 16);
        int eventCodeValue = ByteArrayConverson.toInt(buffer, 19, 18);
        int value = ByteArrayConverson.toInt(buffer, 23, 20);

        EventTypes eventType = EventTypes.byValue(eventTypeValue);


        return new EventData(
            getEventTime(buffer),
            eventType,
            eventType.eventCodeByValue(eventCodeValue),
            value
        );

    }

    private long[] getEventTime(byte[] buffer) {
        long microSeconds = ByteArrayConverson.toLong(
            buffer,
            15,
            8
        );

        long seconds = ByteArrayConverson.toLong(
            buffer,
            7,
            0
        );

        long[] time = {seconds, microSeconds};
        return time;
    }
    
    public byte[] eventFileReader() {
        byte[] buffer = new byte[24];
        
        try {
            
            FileInputStream reader = new FileInputStream(inputFile);
            
            reader.read(buffer);
            reader.close();
            
        } catch (IOException error) {
            System.out.println(error);
            
        }
        
        return buffer;
                    
    }

}