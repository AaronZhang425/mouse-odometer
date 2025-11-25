import java.io.IOException;

import java.io.File;
import java.io.FileInputStream;

public class Odometer {
    Mice mice;
    int dpi;

    public Odometer(int dpi) {
        this.dpi = dpi;
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
        int type = byteArrayToInt(buffer, 16, 17);
        int eventCode = byteArrayToInt(buffer, 18, 19);
        int value = byteArrayToInt(buffer, 20, 23);

        return new EventData(
            getEventTime(buffer), type, EventCodes.MAX, value
        );

    }

    private Time getEventTime(byte[] buffer) {
        // long microSeconds = byteArrayToLong(
        //     buffer,
        //     8,
        //     15
        // );

        long microSeconds = ByteArrayConverson.toLong(buffer, 15, 8);



        // long seconds = byteArrayToLong(
        //     buffer,
        //     0,
        //     7
        // );

        long seconds = ByteArrayConverson.toLong(buffer, 7, 0);


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
                

    private long byteArrayToLong(
        byte[] buffer,
        int startIdx,
        int endIdx
    ) {
    
        long num = 0;
        
        // apply bit mask to buffer to force change interpretation of bits
        // put bits into num and shift by 1 byte
        for(int i = endIdx; i >= startIdx; i--) {
            num = (num << 8) | (buffer[i] & 0xFF);
        }

        return num;

    }

    private int byteArrayToInt(
        byte[] buffer,
        int startIdx,
        int endIdx
    ) {
    
        int num = 0;
        
        for(int i = endIdx; i >= startIdx; i--) {
            num = (num << 8) | (buffer[i] & 0xFF);
        }

        return num;

    }


}