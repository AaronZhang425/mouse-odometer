import java.time.Instant;

public class Main {

    public static void main(String[] args) {
        // Mouse mouse = new Mouse(2);
        // System.out.println(mouse.getMouseHandlerFile());
        
        
        Odometer odometer = new Odometer(1000, 5);
        while (true) {
            // long[] time = odometer.getEventTime();
            EventData inputData = odometer.getEventData();
            System.out.println();

            byte[] buffer = odometer.eventFileReader();


            for (byte elem : buffer) {
                System.out.print(elem + " ");
            }
            
            System.out.println();

            System.out.println(inputData.time().seconds());
            
            Instant now = Instant.now();
            System.out.println(now.getEpochSecond());
            System.out.println(now.getEpochSecond() - inputData.time().seconds());

            long microSeconds = inputData.time().microSeconds();
 
            System.out.println(microSeconds);
            System.out.println(now.getNano() / 1000);
            System.out.println(microSeconds - (now.getNano() / 1000));


            System.out.println();

        }


    }


}
