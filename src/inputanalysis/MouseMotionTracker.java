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

    // private final EventData[] xValues = new EventData[3];
    // private final EventData[] yValues = new EventData[3];

    // private final EventData[] xValues = {new EventData(), new EventData(), new EventData()};
    // private final EventData[] yValues = {new EventData(), new EventData(), new EventData()};

    // private final ArrayDeque<EventData> xValues = new ArrayDeque<>();
    // private final ArrayDeque<EventData> yValues = new ArrayDeque<>();

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
        // TODO: Redo logic for sensing

        while (!stop) {
            // motionData[0][0] = xValues.getData();

            if (xValues.hasNext()) {
                motionData[0][0] += getDisplacement(xValues.getData());
                // EventData xData = xValues.getData();
                // System.out.println(xData);
            }
            
            if (yValues.hasNext()) {
                motionData[0][1] += getDisplacement(xValues.getData());

            }

            System.out.println("X displacement: " + motionData[0][0]);
            System.out.println("Y displacement: " + motionData[0][1]);
            // if (xData != null) {
            //     motionData[0][0] += getDisplacement(xData);

            // }



            // EventData yData = yValues.getData();
        
            // if (yData != null) {
            //     motionData[0][1] += getDisplacement(yData);

            // }



            // System.out.println("X displacement: " + motionData[0][0]);
            // System.out.println("Y displacement: " + motionData[0][1]);
            // getData();
        
            // if (xValues.peekFirst() != null) {
            //     motionData[0][0] += getDisplacement(xValues.pollFirst());
            // }

            // if (yValues.peekFirst() != null) {
            //     motionData[0][1] += getDisplacement(yValues.pollFirst());
            // }

            // System.out.println("X displacement " + motionData[0][0]);
            // System.out.println("Y displacement " + motionData[0][1]);
            //     // update x displacement
            //     motionData[0][0] += totalDisplacement(xValues.getData);
            //     System.out.println("X displacement: " + motionData[0][0]);
            //     // update y displacement
            //     motionData[0][1] += totalDisplacement(yValues);
            //     System.out.println("Y displacement: " + motionData[0][1]);
            
            //     System.out.println("X value 0: " + xValues[0].value());
            //     System.out.println("X value 1: " + xValues[1].value());
            //     System.out.println("X value 2: " + xValues[2].value());
            
            //     System.out.println("Y value 0: " + yValues[0].value());
            //     System.out.println("Y value 1: " + yValues[1].value());
            //     System.out.println("Y value 2: " + yValues[2].value());
            
        }

        // xValues.terminate();
        // yValues.terminate();
        
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

    // private void getData() {
    //     EventData data = reader.getEventData();
        
    //     if (!data.eventType().equals(EventTypes.REL)) {
    //         return;
    //     }

    //     if (data.eventCode().equals(Rel.REL_X)) {
    //         // xValues[0] = xValues[1];
    //         // xValues[1] = xValues[2];
    //         // xValues[2] = data;
    //         xValues.addLast(data);
            
    //     } else if (data.eventCode().equals(Rel.REL_Y)) {
    //         // yValues[0] = yValues[1];
    //         // yValues[1] = yValues[2];
    //         // yValues[2] = data;
    //         yValues.addLast(data);

    //     }

    // }

    // private void getData() {
    //     int foundXValues = 0;
    //     int foundYValues = 0;

    //     while (foundXValues < 3 || foundYValues < 3) {
    //         EventData data = reader.getEventData();
    //         printEventData(data);

    //         if (!data.eventType().equals(EventTypes.REL)) {
    //             continue;
    //         }

    //         if (data.eventCode().equals(Rel.REL_X)) {
    //             foundXValues++;

    //             xValues[0] = xValues[1];
    //             xValues[1] = xValues[2];
    //             xValues[2] = data;

    //             // System.out.println("X value 0: " + xValues[0]);
    //             // System.out.println("X value 1: " + xValues[1]);
    //             // System.out.println("X value 0: " + xValues[2]);


    //             continue;
                
    //         } else if (data.eventCode().equals(Rel.REL_Y)) {
    //             foundYValues++;

    //             yValues[0] = yValues[1];
    //             yValues[1] = yValues[2];
    //             yValues[2] = data;

    //         }



    //     }

    // }

    // private void printEventData(EventData data) {
    //     System.out.println("Input event info: ");
    //     System.out.println("Seconds: " + data.time()[0]);
    //     System.out.println("Microseconds: " + data.time()[1]);
    //     System.out.println("Event type: " + data.eventType());
    //     System.out.println("Event code: " + data.eventCode());
    //     System.out.println("Value: " + data.value());
    //     System.out.println();

    // }

}
