package inputanalysis;

import devicemanagement.EventData;
import devicemanagement.InputReader;
import eventclassification.EventTypes;
import eventclassification.eventcodes.Rel;

// to be implemented
public class MotionTracker implements Runnable {
    private final InputReader reader;

    private final EventData[] xValues = new EventData[2];
    private final EventData[] yValues = new EventData[2];

    // outer: displacement, velocity, acceleration
    // inner: components of vector (x, y)
    private final double[][] motionData = new double[3][2];

    public MotionTracker(InputReader reader, double[] origin) {
        this.reader = reader;
        motionData[0][0] = origin[0];
        motionData[0][1] = origin[1];

    }
    
    
    public MotionTracker(InputReader reader) {
        this.reader = reader;
        motionData[0][0] = 0;
        motionData[0][1] = 0;

    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        
    }

    public void getAcceleration(EventData[] data) {
        long initialSeconds = data[0].time()[0];
        long intialMicroseconds = data[0].time()[1];

        long finalSeconds = data[1].time()[0];
        long finalMicroseconds = data[1].time()[1];

        // in seconds
        double timeDelta = (
            (finalSeconds - initialSeconds) +
            (finalMicroseconds - initialSeconds) / 1E6
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
