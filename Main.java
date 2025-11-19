public class Main {

    public static void main(String[] args) {
        // Mouse mouse = new Mouse(2);
        // System.out.println(mouse.getMouseHandlerFile());
        
        Odometer odometer = new Odometer(1000, 2);
        while (true) {
            byte[] buffer = odometer.eventFileReader();

            for (byte elem : buffer) {
                System.out.print(elem + ", ");

            }

            System.out.println();

        }

    }


}
