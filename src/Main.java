import devicemanagement.*;
import eventclassification.*;
import eventclassification.eventcodes.*;
import inputanalysis.MouseMotionTracker;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {       
        // InputReader input = new InputReader(5);

        // int num = Key.TEMP.getValue();
        System.out.println("Program running");

        ArrayList<InputDevice> devices = KernalInputDevices.getDevices();

        // EventTypes[] filter = {EventTypes.REL, EventTypes.MSC};
        HashMap<EventTypes, EventCode[]> fullCapabilitiesFilter = new HashMap<>();
        EventCode[] filter = {Rel.REL_X, Rel.REL_Y};
        EventCode[] eventCodeFilterMsc = null;
        
        fullCapabilitiesFilter.put(EventTypes.REL, filter);
        fullCapabilitiesFilter.put(EventTypes.MSC, eventCodeFilterMsc);

        ArrayList<InputDevice> filteredDeviceList = KernalInputDevices.getDevices(fullCapabilitiesFilter);
        // ArrayList<InputDevice> filteredDeviceList = KernalInputDevices.getDevices(filter);

        if (filteredDeviceList.size() == 0) {
            System.exit(1);
        }
        
        MouseMotionTracker mouseTracker = new MouseMotionTracker(new Mouse(filteredDeviceList.get(0), 1000));

        Thread mouseThread = new Thread(mouseTracker);
        mouseThread.start();

        // InputDevice deviceToUse = filteredDeviceList.get(0);
        // Mouse mouse = new Mouse(deviceToUse, 1000);
        // System.out.println(deviceToUse.name());
        // System.out.println("Please wait");

        // try {
        //     Thread.sleep(2000);

        // } catch (Exception e) {
        //     System.out.println(e);

        // }
        
        
        // Thread tracker = new Thread(new MouseMotionTracker(mouse));

        // tracker.start();

        // for (int i = 0; i < filteredDeviceList.size(); i++) {
        //     InputDevice device = filteredDeviceList.get(i);

        //     System.out.println(device.name());
        //     Set<EventTypes> keySet = device.capabilities().keySet();

        //     for (EventTypes eventType : keySet) {
        //         System.out.println(eventType);
        //     }

        // }



    }


}
