package inputanalysis;

import devicemanagement.EventData;
import devicemanagement.InputReader;
import devicemanagement.Mouse;
import eventclassification.EventTypes;
import eventclassification.eventcodes.Rel;

public class MotionCalculations implements Runnable {
    private final InputReader reader;
    private final Mouse mouse;

    private final EventData[] xValues = new EventData[2];
    private final EventData[] yValues = new EventData[2];

    // outer: displacement, velocity, acceleration
    // inner: components of vector (x, y)
    private final double[][] motionData = new double[3][2];

    public MotionCalculations(Mouse mouse, double[] start) {
        this.mouse = mouse;
        this.reader = new InputReader(this.mouse.device().handlerFile());
        
        motionData[0][0] = start[0];
        motionData[0][1] = start[1];
        
    }
    
    
    public MotionCalculations(Mouse mouse) {
        this.mouse = mouse;
        this.reader = new InputReader(this.mouse.device().handlerFile());

        motionData[0][0] = 0;
        motionData[0][1] = 0;

    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        
    }

    public void getVelocity(EventData[] data) {
        int intialCount = data[0].value();
        int finalCount = data[1].value();

        // in m/s
        // currently only gets length, not velocity
        double initialMeters = ((1.0 * intialCount / mouse.dpi()) * 0.0254);
        double finalMeters = ((1.0 * finalCount / mouse.dpi()) * 0.0254);

    }

    // data is a parameter that represents an initial and final component
    // of a vector
    public void getAcceleration(EventData[] data) {
        long initialSeconds = data[0].time()[0];
        long intialMicroseconds = data[0].time()[1];

        long finalSeconds = data[1].time()[0];
        long finalMicroseconds = data[1].time()[1];

        // in seconds
        double timeDelta = (
            (finalSeconds - initialSeconds) +
            (finalMicroseconds - intialMicroseconds) / 1.0E60
        );


    }

    public void getData() {
        int foundXValues = 0;
        int foundYValues = 0;

        while (foundXValues < 2 || foundYValues < 2) {
            EventData data = reader.getEventData();

            if (!data.eventType().equals(EventTypes.REL)) {
                continue;
            }

            if (data.eventCode().equals(Rel.REL_X)) {
                foundXValues++;

                xValues[0] = xValues[1];
                xValues[1] = data;
                continue;
                
            } else if (data.eventCode().equals(Rel.REL_Y)) {
                foundYValues++;
                yValues[0] = yValues[1];
                yValues[1] = data;

            }



        }

    }

}
