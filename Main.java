import java.time.Instant;

public class Main {

    public static void main(String[] args) {
        // Mouse mouse = new Mouse(2);
        // System.out.println(mouse.getMouseHandlerFile());
        
        
        Odometer odometer = new Odometer(1000, 5);
        while (true) {
            // long[] time = odometer.getEventTime();
            Time time = odometer.getEventTime();
            System.out.println();

            byte[] buffer = odometer.eventFileReader();


            for (byte elem : buffer) {
                System.out.print(elem + " ");
            }
            
            System.out.println();

            System.out.println(time.seconds());
            
            Instant now = Instant.now();
            System.out.println(now.getEpochSecond());
            System.out.println(now.getEpochSecond() - time.seconds());
            // for (long elem : time) {
            //     System.out.print(elem + ", ");
                

            // }

            System.out.println();

        }


    }


}
