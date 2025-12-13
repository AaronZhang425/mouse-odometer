import devicemanagement.EventData;
import devicemanagement.InputReader;

public class Main {

    public static void main(String[] args) {       
        InputReader input = new InputReader(5);

        // int num = Key.TEMP.getValue();

        while (true) {
            EventData inputData = input.getEventData();
            System.out.println(inputData);
            System.out.println();

            // byte[] buffer = input.eventFileReader();

            // for (byte elem : buffer) {
            //     System.out.print(elem + " ");
            // }
            
            // // System.out.println(num);


        }


    }


}
