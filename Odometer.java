import java.io.IOException;

import java.io.File;
import java.io.FileInputStream;
// import java.io.InputStream;
// import java.io.BufferedReader;
// import java.io.FileReader;

public class Odometer {
    Mouse mouse;
    int dpi;

    public Odometer(int dpi) {
        this.dpi = dpi;
    }
    
    public Odometer(int dpi, int eventNum) {
        // this.dpi = dpi;
        // mouse = new Mouse(eventNum);
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
            // final int BUFFER = 4;

            FileInputStream reader = new FileInputStream(
                mouse.getMouseHandlerFile()
            );
            // FileInputStream reader = new FileInputStream(
            //     new File("/dev/input/event2")
            // );
            
            reader.read(buffer);

            for (byte elem : buffer) {
                System.out.print(elem + ", ");
            }
            // BufferedReader reader = (
            //     new BufferedReader(
            //         new FileReader(mouse.getMouseHandlerFile()),
            //         BUFFER
            //     )
            // );

            // reader.lines().toArray();



            
        } catch (IOException error) {
            System.out.println(error);

        }



    }


}