package devicemanagement;


import eventclassification.EventCategory;
import eventclassification.EventTypes;
import eventclassification.eventcodes.Abs;
import eventclassification.eventcodes.Rel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class InputReader {
    Mice mice;

    public InputReader() {
        mice = new Mice();
    }

    public InputReader(int eventNum) {
        this(new File("/dev/input/event" + eventNum));

    }

    public InputReader(String filePath) {
        this(new File(filePath));
    }

    public InputReader(File file) {
        mice = new Mice(file);
    }

    public EventData getEventData() {
        byte[] buffer = eventFileReader();

        int eventTypeValue = ByteArrayConverson.toInt(buffer, 17, 16);
        int eventCodeValue = ByteArrayConverson.toInt(buffer, 18, 19);
        int value = ByteArrayConverson.toInt(buffer, 20, 23);

        // EventTypes eventType = EventTypes.fromValue(eventTypeValue);
        // Class<? extends EventCategory> eventCodeSet = eventType.getEventCodeSet();

        // try {
        //     Method getEventCodeFromValue = eventCodeSet.getMethod("fromValue", int.class);
        //     getEventCodeFromValue.invoke(null, eventCodeValue);

        // } catch (NoSuchMethodException error) {
        //     System.out.println(error);
        // }
        // // EventId eventType = 

        return new EventData(
            getEventTime(buffer),
            EventTypes.fromValue(eventTypeValue),
            Rel.X,
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
            
            FileInputStream reader = new FileInputStream(
            mice.getMouseHandlerFile());
            
            reader.read(buffer);
            reader.close();
            
        } catch (IOException error) {
            System.out.println(error);
            
        }
        
        return buffer;
                    
    }

}