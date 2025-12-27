package devicemanagement;


// import eventclassification.EventCategory;
import eventclassification.EventTypes;
import eventclassification.eventcodes.EventCode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
// import java.io.IOException;
// import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
// import java.util.List;
// import java.util.regex.Matcher;
// import java.util.regex.Pattern;



public class KernalInputDevices {
    // This file lists all devices and their details
    // private static final File INPUT_DEVICE_INFO = new File("/proc/bus/input/devices");
    private static final File INPUT_DEVICE_DIR = new File("/sys/class/input");

    // List of devices
    private static ArrayList<InputDevice> devices = new ArrayList<>();

    static {
        update();
    }
    
    // get devices with that have the event types listed in the parameters
    // to be implemented
    private static ArrayList<InputDevice> getDevices(HashMap<EventTypes, EventCode> fullCapabilities) {
        return new ArrayList<>(devices);
    }


    // get devices with that have the event types listed in the parameters
    // to be implemented
    public static ArrayList<InputDevice> getDeivces(ArrayList<EventTypes> eventTypes) {
        ArrayList<InputDevice> filtered = new ArrayList<>();
        
        for (InputDevice inputDevice : devices) {
            ArrayList<EventTypes> possibleEvents = new ArrayList<>(Arrays.asList(inputDevice.possibleEvents()));

            if (possibleEvents.containsAll(eventTypes)) {
                filtered.add(inputDevice);
            }

        }

        return filtered;
    }
    
    public static ArrayList<InputDevice> getDevices() {
        return new ArrayList<>(devices);

    }

    // update list of devices
    public static void update() {
        int[] id = new int[4];
        String name = null;
        File physicalPath = null;
        File systemFileSystem = null;
        File eventFile = null;
        EventTypes[] possiableEventTypes = null;
    
        String[] eventDirs = getEventDirectories(INPUT_DEVICE_DIR);

        for (String eventDir : eventDirs) {
            System.out.println(eventDir);
            
            id = getDeviceId(eventDir);
            System.out.println(id);

            eventFile = getHanderFile(eventDir);
            System.out.println(eventFile);

            name = getDeviceName(eventDir);
            System.out.println(name);



        }
        
    }

    private static String[] getEventDirectories(File dirToFilter) {
        String[] files = dirToFilter.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().matches("event[0-9]+");

            }
        });

        return files;
    }

    private static String getDeviceName(String eventDirName) {
        File nameFile = new File(
            INPUT_DEVICE_DIR +
            "/" +
            eventDirName +
            "/device/name"
        );
        
        System.out.println(nameFile);
        return readFileLine(nameFile);
    }

    // id methods
    private static int[] getDeviceId(String eventDirName) {
        File idDir = new File(INPUT_DEVICE_DIR + "/" + eventDirName + "/device/id");
        int[] id = new int[4];

        id[0] = getBus(idDir);
        id[1] = getVendor(idDir);
        id[2] = getProduct(idDir);
        id[3] = getVersion(idDir);

        return new int[4];
    }

    private static int getBus(File idDir) {
        File busFile = new File(idDir + "/bustype");
        int busNum = Integer.parseInt(readFileLine(busFile), 16);
        return busNum;
    }

    private static int getVendor(File idDir) {
        File vendorFile = new File(idDir + "/vendor");
        int vendorNum = Integer.parseInt(readFileLine(vendorFile), 16);
        return vendorNum;
    }
    
    private static int getProduct(File idDir) {
        File productFile = new File(idDir + "/product");
        int productNum = Integer.parseInt(readFileLine(productFile), 16);
        return productNum;
    }
    
    private static int getVersion(File idDir) {
        File versionFile = new File(idDir + "/product");
        int versionNum = Integer.parseInt(readFileLine(versionFile), 16);
        return versionNum;
    }

    private static File getHanderFile(String eventDirName) {
        return new File("/dev/input/" + eventDirName);

    }


    // capability methods
    private static HashMap<EventTypes, EventCode[]> getCapabilities(String eventDirname) {
        return null;
    }

    private static EventCode getPossibleEventCodes(String eventDirName, EventTypes eventType) {
        return null;
    }

    private static EventTypes[] getPossibleEventTypes(String eventDirName) {
        File eventTypeCapabilitiesFile = new File(
            INPUT_DEVICE_DIR + 
            "/" +
            eventDirName + 
            "device/capabilities/ev"
        );

        String hex = readFileLine(eventTypeCapabilitiesFile);

        ArrayList<Integer> bitIndicies = getHexBitIndicies(hex);

        EventTypes[] eventTypeCapabilities = new EventTypes[bitIndicies.size()];

        for (int i = 0; i < bitIndicies.size(); i++) {
            EventTypes capability = EventTypes.byValue(bitIndicies.get(i));
            eventTypeCapabilities[i] = capability;

        }

        return eventTypeCapabilities;

    }

    // utility methods
    private static ArrayList<Integer> getHexBitIndicies(String hex) {
        int bitMap = Integer.parseInt(hex, 16);
        
        ArrayList<Integer> indicies = new ArrayList<>();

        for (int i = 0; i < Integer.SIZE; i++) {
            if ((bitMap & 1) == 1) {
                indicies.add(i);
            }

            bitMap >>>= 1;

        }

        return indicies;
    }
    
    private static String readFileLine(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return reader.readLine();
            
            
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }


    }


}
