import java.io.IOException;

import java.io.File;
import java.io.FileInputStream;

public class Odometer {
    Mice mice;
    int dpi;

    public Odometer(int dpi) {
        this.dpi = dpi;
        mice = new Mice();
    }

    public Odometer(int dpi, int eventNum) {
        this(dpi, new File("/dev/input/event" + eventNum));

    }

    public Odometer(int dpi, String filePath) {
        this(dpi, new File(filePath));
    }

    public Odometer(int dpi, File file) {
        this.dpi = dpi;
        mice = new Mice(file);
    }

    public EventData getEventData() {
        byte[] buffer = eventFileReader();

        getEventTime(buffer);
        int type = ByteArrayConverson.toInt(buffer, 17, 16);
        int eventTypeValue = ByteArrayConverson.toInt(buffer, 18, 19);
        int value = ByteArrayConverson.toInt(buffer, 20, 23);

        return new EventData(
            getEventTime(buffer),
            type,
            EventTypes.MAX,
            value
        );

    }

    private Time getEventTime(byte[] buffer) {
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


        return new Time(seconds, microSeconds);
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