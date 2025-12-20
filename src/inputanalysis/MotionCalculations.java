package inputanalysis;

import devicemanagement.EventData;
import devicemanagement.InputReader;
import devicemanagement.Mouse;
import eventclassification.EventTypes;
import eventclassification.eventcodes.Rel;

public class MotionCalculations implements Runnable {
    private final InputReader reader;
    private final Mouse mouse;

    private final EventData[] xValues = new EventData[3];
    private final EventData[] yValues = new EventData[3];

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
        while (true) {
            getData();
            
        }
        
    }

    private double mouseCountsToMeters(int counts, int dpi) {
        return (1.0 * counts / dpi) * 0.0254;
    }

    private double mouseCountsToMeters(int counts) {
        return mouseCountsToMeters(counts, mouse.dpi());
        // return (1.0 * counts / mouse.dpi()) * 0.0254;
    }


    private void displacement(EventData[] data) {

    }

    private void getVelocity(EventData[] data) {
        int intialCount = data[0].value();
        int finalCount = data[1].value();

        // in m/s
        // currently only gets length, not velocity
        double initialMeters = mouseCountsToMeters(intialCount);
        double finalMeters = mouseCountsToMeters(finalCount);

    }

    // data is a parameter that represents an initial and final component
    // of a vector
    private void getAcceleration(EventData[] data) {
        double timeDifference = getTimeDifference(data);



        // long initialSeconds = data[0].time()[0];
        // long intialMicroseconds = data[0].time()[1];

        // long finalSeconds = data[1].time()[0];
        // long finalMicroseconds = data[1].time()[1];

        // // in seconds
        // double timeDelta = (
        //     (finalSeconds - initialSeconds) +
        //     (finalMicroseconds - intialMicroseconds) / 1.0E60
        // );


    }

    private double getTimeDifference(EventData[] data) {
        long initialSeconds = data[0].time()[0];
        long intialMicroseconds = data[0].time()[1];

        long finalSeconds = data[1].time()[0];
        long finalMicroseconds = data[1].time()[1];

        // in seconds
        double timeDifference = (
            (finalSeconds - initialSeconds) +
            (finalMicroseconds - intialMicroseconds) / 1.0E60
        );

        return timeDifference;

    }

    private void getData() {
        int foundXValues = 0;
        int foundYValues = 0;

        while (foundXValues < 3 || foundYValues < 3) {
            EventData data = reader.getEventData();

            if (!data.eventType().equals(EventTypes.REL)) {
                continue;
            }

            if (data.eventCode().equals(Rel.REL_X)) {
                foundXValues++;

                xValues[0] = xValues[1];
                xValues[1] = xValues[2];
                xValues[2] = data;
                continue;
                
            } else if (data.eventCode().equals(Rel.REL_Y)) {
                foundYValues++;

                yValues[0] = yValues[1];
                yValues[1] = yValues[2];
                yValues[2] = data;

            }



        }

    }

}
