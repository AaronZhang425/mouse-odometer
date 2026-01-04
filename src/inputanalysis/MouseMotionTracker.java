package inputanalysis;

import devicemanagement.EventData;
import devicemanagement.InputReader;
import devicemanagement.Mouse;
import eventclassification.EventTypes;
import eventclassification.eventcodes.Rel;


public class MouseMotionTracker implements Runnable{
    private volatile boolean stop = false;

    private final InputReader reader;
    private final Mouse mouse;

    private final InputFilter xValues;
    private final InputFilter yValues;

    private final Thread xValuesThread;
    private final Thread yValuesThread;

    // outer: displacement, velocity, acceleration
    // inner: components of vector (x, y)
    private final double[][] motionData = new double[3][2];

    public MouseMotionTracker(Mouse mouse, double[] start) {
        this.mouse = mouse;
        this.reader = new InputReader(this.mouse.device().handlerFile());
        
        motionData[0][0] = start[0];
        motionData[0][1] = start[1];

        xValues = new InputFilter(reader, EventTypes.REL, Rel.REL_X);
        yValues = new InputFilter(reader, EventTypes.REL, Rel.REL_Y);

        xValuesThread = new Thread(xValues);
        yValuesThread = new Thread(yValues);

        xValuesThread.start();
        yValuesThread.start();

        
    }
    
    
    public MouseMotionTracker(Mouse mouse) {
        double[] startPosition = {0, 0};
        this(mouse, startPosition);

    }

    public void terminate() {
        stop = false;
    }

    public boolean isTerminated() {
        return stop;
    }

    public double[][] getMotionData() {
        return motionData.clone();
    }


    @Override
    public void run() {
        while (!stop) {
            if (xValues.hasNext()) {
                EventData data = xValues.getData();
                motionData[0][0] += getDisplacement(data);
                
            }
            
            if (yValues.hasNext()) {
                EventData data = yValues.getData();
                motionData[0][1] += getDisplacement(data);

            }

            System.out.println("X displacement: " + motionData[0][0]);
            System.out.println("Y displacement: " + motionData[0][1]);

            
        }

        xValues.terminate();
        yValues.terminate();
        
    }
    
    private double mouseCountsToMeters(int counts, int dpi) {
        // return (double) counts / 1000.0;
        // return (double) counts / dpi;
        return (double) counts / dpi * 0.0254;

        // counts inch   meters
        //        counts inch
    }
    
    private double mouseCountsToMeters(int counts) {
        return mouseCountsToMeters(counts, mouse.dpi());
        // return (1.0 * counts / mouse.dpi()) * 0.0254;
    }
    
    

    private double totalDisplacement(EventData[] event) {
        double displacement = 0;

        for (EventData data : event) {
            displacement += getDisplacement(data);
        }

        return displacement;

    }

    private double getDisplacement(EventData event) {
        return mouseCountsToMeters(event.value());

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


}
