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

    public MotionTracker(InputReader reader) {
        this.reader = reader;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        
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
                // xValues[foundXValues] = data;
                continue;

            } else if (data.eventCode().equals(Rel.REL_Y)) {
                foundYValues++;
                // yValues[foundYValues] = data;
            }



        }

    }

}
