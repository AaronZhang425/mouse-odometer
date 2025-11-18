import java.io.IOException;

import java.io.File;
import java.io.FileInputStream;

public class Odometer {
    Mouse mouse;
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
        mouse = new Mouse(file);
    }

    public void eventFileReader() {
        try {
            byte[] buffer = new byte[24];

            FileInputStream reader = new FileInputStream(
                mouse.getMouseHandlerFile()
            );

            while (true) { 
                reader.read(buffer);

                for (byte input : buffer) {
                    System.out.print(input + ", ");
                }

                System.out.println();

            }
            
        } catch (IOException error) {
            System.out.println(error);

        }



    }


}