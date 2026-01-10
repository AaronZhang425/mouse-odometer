package devicemanagement;

import eventclassification.EventTypes;
import eventclassification.eventcodes.EventCode;
import eventclassification.eventcodes.Rep;
import eventclassification.eventcodes.Syn;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



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
    public static ArrayList<InputDevice> getDevices(HashMap<EventTypes, EventCode[]> fullCapabilitiesFilter) {
        // Arraylist containing a filtered list of devices by both
        // event type and event code
        ArrayList<InputDevice> filtered = new ArrayList<>();
        
        ArrayList<EventTypes> eventTypeFilter = new ArrayList<>(fullCapabilitiesFilter.keySet());
        
        // Input devices filtered only by event type, not including event codes
        ArrayList<InputDevice> eventTypeFiltered = getDevices(eventTypeFilter);

        for (InputDevice inputDevice : eventTypeFiltered) {
            boolean matches = true;

            for (int i = 0; i < eventTypeFilter.size(); i++) {
                // Get the event code capabilities for a single event type key
                EventTypes eventTypeKey = eventTypeFilter.get(i);

                // If the event code array is null, interpret as wild card
                // Essentially filter by only event type if event code is null
                if (fullCapabilitiesFilter.get(eventTypeKey) == null) {
                    continue;
                }


                // Convert the event codes of the device to a hash set
                HashSet<EventCode> capableEventCodes = new HashSet<>(
                    Arrays.asList(inputDevice.capabilities().get(eventTypeKey))
                );

                // Convert the event codes to filter to a hash set
                HashSet<EventCode> eventCodeFilter = new HashSet<>(
                    Arrays.asList(fullCapabilitiesFilter.get(eventTypeKey))
                );

                // Set<EventCode> eventCodeFilter = fullCapabilitiesFilter.get(eventTypeKey);

                if (!capableEventCodes.containsAll(eventCodeFilter)) {
                    matches = false;
                }

            }


            if (matches) {
                filtered.add(inputDevice);
            }
        
        }

        return filtered;
    
    }

    // get devices with that have the event types listed in the parameters
    public static ArrayList<InputDevice> getDevices(Set<EventTypes> eventTypesFilter) {
        // Arraylist to be filled with each device that is capable of at least
        // all capabilities as specified by the parameter
        ArrayList<InputDevice> filtered = new ArrayList<>();
        
        for (InputDevice inputDevice : devices) {
            // For every device, get the set of keys of the hashmap of
            // capabilities
            Set<EventTypes> possibleEventTypes = (
                inputDevice.capabilities().keySet()
            );
            
            // If the key set contains all elements of the specified set
            // of capable event types, add the device to the array list.
            if (possibleEventTypes.containsAll(eventTypesFilter)) {
                filtered.add(inputDevice);

            }

        }

        // Return the filtered array list after each device has been evaluated
        return filtered;
    }

    public static ArrayList<InputDevice> getDevices(List<EventTypes> eventTypesFilter) {
        return getDevices(new HashSet<>(eventTypesFilter));

    }

    public static ArrayList<InputDevice> getDevices(EventTypes[] eventTypesFilter) {
        return getDevices(new HashSet<>(Arrays.asList(eventTypesFilter)));

    }


    public static ArrayList<InputDevice> getDevices() {
        // Return a copy of arraylist to prevent external manipulation
        return new ArrayList<>(devices);

    }

    // update list of devices
    public static void update() {
        // Clear arraylist to remove duplicate items being added
        devices.clear();

        int[] id = new int[4];
        String name = null;
        File physicalPath = null; // unimplemented
        File systemFileSystem = null; // unimplemented
        File eventFile = null;
        HashMap<EventTypes, EventCode[]> capabilities = null;
    
        String[] eventDirs = getEventDirectories(INPUT_DEVICE_DIR);

        for (String eventDir : eventDirs) {
            
            id = getDeviceId(eventDir);

            eventFile = getHanderFile(eventDir);

            name = getDeviceName(eventDir);

            capabilities = getCapabilities(eventDir);

            devices.add(new InputDevice(
                id,
                name,
                physicalPath,
                systemFileSystem,
                eventFile,
                capabilities
            ));

            


        }
        
    }

    private static String[] getEventDirectories(File dirToFilter) {
        // Get the files within the directory and only get subdirectoreis
        // of /sys/class/input that start with event followed by a number
        String[] files = dirToFilter.list((File dir, String name) -> name.toLowerCase().matches("event[0-9]+"));

        return files;
    }

    /**
     * Reads the name file of an event directory and returns its contents given
     * the name of the event directory.
     * 
     * @param eventDirName Name of the event directory (e.g. event5)
     * @return the name of device contained in the name file of the event
     */
    private static String getDeviceName(String eventDirName) {
        File nameFile = new File(
            INPUT_DEVICE_DIR +
            "/" +
            eventDirName +
            "/device/name"
        );

        return readFileLine(nameFile);
    }

    // Todo: implement
    private static File getPhysicalPath(String eventDirString) {
        return null;
    }

    // Todo: implement
    private static File getSystemFileSystem(String eventDirString) {
        return null;
    }

    // id methods
    private static int[] getDeviceId(String eventDirName) {
        // Creates a file representing the file path of the id directory inside
        // event directory
        File idDir = new File(
            INPUT_DEVICE_DIR +
            "/" +
            eventDirName +
            "/device/id"
        );
        
        int[] id = new int[4];

        id[0] = getBus(idDir);
        id[1] = getVendor(idDir);
        id[2] = getProduct(idDir);
        id[3] = getVersion(idDir);

        return id;

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
        File versionFile = new File(idDir + "/version");
        int versionNum = Integer.parseInt(readFileLine(versionFile), 16);
        return versionNum;
    }

    private static File getHanderFile(String eventDirName) {
        return new File("/dev/input/" + eventDirName);

    }


    // capability methods
    private static HashMap<EventTypes, EventCode[]> getCapabilities(String eventDirname) {
        EventTypes[] possiableEventTypes = getPossibleEventTypes(eventDirname);

        // create a hashmap to map event types to the corresponding array of
        // event types
        HashMap<EventTypes, EventCode[]> fullCapabilities = new HashMap<>();

        // For each event type, get the array of possiable event codes and
        // add the pair into the hash map 
        for (EventTypes eventType : possiableEventTypes) {
            fullCapabilities.put(eventType, getPossibleEventCodes(eventDirname, eventType));

        }
        
        // Return the hashmap that maps each possiable event type to their
        // respective array of possiable event codes
        return fullCapabilities;
    }

    private static EventCode[] getPossibleEventCodes(String eventDirName, EventTypes eventType) {
        // Any device capable of SYN or REP are automatically capable of 
        // every event code listed under the respective event type. Therefore,
        // a file that lists the possiable event codes for event types SYN and
        // REP is not created by the linux system  
        if (eventType.equals(EventTypes.SYN)) {
            return Syn.values();

        } else if (eventType.equals(EventTypes.REP)) {
            return Rep.values();
        
        }

        /*
        In the directory /sys/class/input/eventX/device/capabilities where
        eventX represents any directory starting with event and ending with
        any number, there exists files specifying event codes the event
        is capable of communicating. The files specifying the possiable
        evnet codes are named after the respective event type in lower case.
        For example, the bit flags indicating potential event codes for relative
        input will be in the rel file, named after the event type rel
        */
        String eventTypeName = eventType.name().toLowerCase();

        
        File eventCodeFile = new File(
            INPUT_DEVICE_DIR +
            "/" +
            eventDirName +
            "/device/capabilities/" +
            eventTypeName
        );
        
        // Put each hex number in an array
        String[] hexNums = readFileLine(eventCodeFile).split(" ");
        
        ArrayList<Integer> bitIndicies = new ArrayList<>();
        
        // System.out.println(eventDirName);

        // for each hex number
        for (int i = 0; i < hexNums.length; i++) {
            // Get the bit indicies without of each hex number
            ArrayList<Integer> wordBitIndicies = getHexBitIndicies(hexNums[i]);
            
            // Factor the index of the hex number to get true bit index value
            for (int j = 0; j < wordBitIndicies.size(); j++) {
                int bitIndexValue = wordBitIndicies.get(j) + i * Long.SIZE;
                wordBitIndicies.set(j, bitIndexValue);
            }
            
            bitIndicies.addAll(wordBitIndicies);
            
            
        }
        
        EventCode[] eventCodeCapabilities = new EventCode[bitIndicies.size()];
        
        for (int i = 0; i < eventCodeCapabilities.length; i++) {
            EventCode capability = eventType.eventCodeByValue(bitIndicies.get(i));
            eventCodeCapabilities[i] = capability;

        }

        // System.out.println(eventTypeName);
        for (EventCode test: eventCodeCapabilities) {
            System.out.print(test + " ");
        }

        System.out.println();

        return eventCodeCapabilities;
    }

    private static EventTypes[] getPossibleEventTypes(String eventDirName) {
        File eventTypeCapabilitiesFile = new File(
            INPUT_DEVICE_DIR + 
            "/" +
            eventDirName + 
            "/device/capabilities/ev"
        );

        // get get the hex number representing the possible event types
        // file containing possible event types will always use a single word
        // extra processing is therefore not needed unlike event code
        String hex = readFileLine(eventTypeCapabilitiesFile);

        ArrayList<Integer> bitIndicies = getHexBitIndicies(hex);

        EventTypes[] eventTypeCapabilities = new EventTypes[bitIndicies.size()];

        for (int i = 0; i < eventTypeCapabilities.length; i++) {
            EventTypes capability = EventTypes.byValue(bitIndicies.get(i));
            eventTypeCapabilities[i] = capability;

        }

        return eventTypeCapabilities;

    }

    // utility methods
    /**
     * 
     * All content in the files in the 
     * /sys/class/input/eventX/device/capabilities directory are hexidecimal
     * numbers. The position of each 1's bit signify flags that correspond to a 
     * event code or event type. Position 0 is defined as the right most bit
     * (i.e. the 1's place of a binary number)
     * 
     * Mappings of the the 1's bit positions can be found here:
     * https://github.com/torvalds/linux/blob/master/include/uapi/linux/input-event-codes.h
     * 
     * @param hex Hexidecimal number to search
     * @return An Array List of all the indicies of a 1 bit
     */
    private static ArrayList<Integer> getHexBitIndicies(String hex) {
        // TODO: Redo method to allow for hex strings beyond the size of a long. Method can still break on the largest possiable inputs


        // The given hex number often exceeds the range of an int
        Long bitMap = Long.parseUnsignedLong(hex, 16);
        
        // An array list to contain the indicies of each bit with index 0 being 
        // the  1's place and index 1 being the 2's place of the binary number
        ArrayList<Integer> indicies = new ArrayList<>();

        // This loop goes through a long and adds the positions in which a 
        // 1 is found in the binary number to the array list. After each check,
        // the index count increases (i in this case) and the number is shifted 
        // rightwards.

        // for each bit of a long:
        for (int i = 0; i < Long.SIZE; i++) {
            // if the rightmost bit is 1 (i.e. the 1's bit is 1):
            if ((bitMap & 1) == 1) {
                // Add the index of that bit into the array list
                // i represents the index of the number
                indicies.add(i);
            }

            // Do a logical shift rightwards by one bit
            bitMap >>>= 1;

        }

        // return the resulting indicies found
        return indicies;
    }
    
    // reads a single line of a given file
    private static String readFileLine(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return reader.readLine();
            
            
        } catch (Exception e) {
            System.out.println(e);
            return null;
            
        }


    }

}
