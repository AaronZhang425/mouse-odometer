package inputanalysis;

import devicemanagement.EventData;
import devicemanagement.InputReader;
import devicemanagement.Mouse;
import eventclassification.EventTypes;
import eventclassification.eventcodes.Rel;

public class MouseMotionTracker implements Runnable {
    private final InputReader reader;
    private final Mouse mouse;

    private final EventData[] xValues = new EventData[3];
    private final EventData[] yValues = new EventData[3];

    // outer: displacement, velocity, acceleration
    // inner: components of vector (x, y)
    private final double[][] motionData = new double[3][2];

    public MouseMotionTracker(Mouse mouse, double[] start) {
        this.mouse = mouse;
        this.reader = new InputReader(this.mouse.device().handlerFile());
        
        motionData[0][0] = start[0];
        motionData[0][1] = start[1];
        
    }
    
    
    public MouseMotionTracker(Mouse mouse) {
        this.mouse = mouse;
        this.reader = new InputReader(this.mouse.device().handlerFile());

        motionData[0][0] = 0;
        motionData[0][1] = 0;

    }

    public double[][] getMotionData() {
        return motionData.clone();
    }


    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (true) {
            getData();

            // update x displacement
            motionData[0][0] += totalDisplacement(xValues);
            // update y displacement
            motionData[0][1] += totalDisplacement(yValues);


            
        }
        
    }
    
    private double mouseCountsToMeters(int counts, int dpi) {
        return (1.0 * counts / dpi) * 0.0254;
    }
    
    private double mouseCountsToMeters(int counts) {
        return mouseCountsToMeters(counts, mouse.dpi());
        // return (1.0 * counts / mouse.dpi()) * 0.0254;
    }
    
    

    private double totalDisplacement(EventData[] data) {
        double displacement = 0;

        for (EventData event : data) {
            displacement += mouseCountsToMeters(event.value());
        }

        return displacement;

    }
    
    // private void displacement() {

    // }

    // private double getVelocity(EventData event0, EventData event1) {
    //     double timeDifference = getTimeDifference(event0.finalData);
    //     double finalMeters = mouseCountsToMeters(event1.value());

    //     return finalMeters / timeDifference;


    //     // in m/s
    //     // currently only gets length, not velocity
    //     // double initialMeters = mouseCountsToMeters(initialCount);

    // }

    private double getVelocity(
        double displacementDifference,
        double timeDifference
    ) {

        return displacementDifference / timeDifference;

    }

    private double getVelocity(
        double intialDisplacement,
        double finalDisplacemenet,
        double timeDifference
    ) {

        return (finalDisplacemenet - intialDisplacement) / timeDifference;

    }

    // data is a parameter that represents an initial and final component
    // of a vector
    private double getAcceleration(
        double intialVelocity,
        double finalVelocity,
        double timeDifference
    ) {
        return (finalVelocity - intialVelocity) / timeDifference;

    }

    private double getTimeDifference(EventData intialData, EventData finalData) {
        long initialSeconds = intialData.time()[0];
        long initialMicroseconds = intialData.time()[1];

        long finalSeconds = finalData.time()[0];
        long finalMicroseconds = finalData.time()[1];

        // long initialSeconds = data[0].time()[0];
        // long intialMicroseconds = data[0].time()[1];

        // long finalSeconds = data[1].time()[0];
        // long finalMicroseconds = data[1].time()[1];

        // in seconds
        double timeDifference = (
            (finalSeconds - initialSeconds) +
            (finalMicroseconds - initialMicroseconds) / 1.0E60
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
