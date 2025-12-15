package inputanalysis;

import devicemanagement.EventData;
import devicemanagement.InputReader;

// to be implemented
public class Odometer implements Runnable {
    private final InputReader reader;
    private final EventData[] xValues = new EventData[2];
    private final EventData[] yValues = new EventData[2];

    public Odometer(InputReader reader) {
        this.reader = reader;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        
    }
}
