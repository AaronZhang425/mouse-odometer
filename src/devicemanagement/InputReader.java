package devicemanagement;


import eventclassification.EventTypes;
import eventclassification.eventcodes.Rel;
import inputanalysis.EventData;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class InputReader {
    Mice mice;
    int dpi;

    public InputReader(int dpi) {
        this.dpi = dpi;
        mice = new Mice();
    }

    public InputReader(int dpi, int eventNum) {
        this(dpi, new File("/dev/input/event" + eventNum));

    }

    public InputReader(int dpi, String filePath) {
        this(dpi, new File(filePath));
    }

    public InputReader(int dpi, File file) {
        this.dpi = dpi;
        mice = new Mice(file);
    }

    public EventData getEventData() {
        byte[] buffer = eventFileReader();

        int eventTypeValue = ByteArrayConverson.toInt(buffer, 17, 16);
        int eventCodeValue = ByteArrayConverson.toInt(buffer, 18, 19);
        int value = ByteArrayConverson.toInt(buffer, 20, 23);

        // EventId eventType = 

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