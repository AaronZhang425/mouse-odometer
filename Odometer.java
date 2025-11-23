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

    public Time getEventTime() {
        byte[] buffer = eventFileReader();

        // Loop backwards for big endian and push bigest byte left
        long microSeconds = 0;
        for (byte i = 7; i >= 4; i--) {
            //bit shift by 1 byte to the left and bit mask buffer
            microSeconds = (microSeconds << 8) | (buffer[i] & 0xFF);
        }

        long seconds = 0;
        for (byte i = 3; i >= 0; i--) {
            // seconds <<= 8;
            // seconds += buffer[i];
            seconds = (seconds << 8) | (buffer[i] & 0xFF);
        }
    

        // long[] time = {seconds, microSeconds};
        return new Time(seconds, microSeconds);
    }

    // public int byteArrayToInt() {
        
    //     int

    // }

    public byte[] eventFileReader() {
        byte[] buffer = new byte[24];
        
        try {

            FileInputStream reader = new FileInputStream(
                mice.getMouseHandlerFile()
            );

    
            reader.read(buffer);
            reader.close();

            // while (true) { 
            //     reader.read(buffer);

            //     for (byte input : buffer) {
            //         System.out.print(input + ", ");
            //     }

            //     System.out.println();

            // }
            
        } catch (IOException error) {
            System.out.println(error);

        }
        

        return buffer;


    }


}