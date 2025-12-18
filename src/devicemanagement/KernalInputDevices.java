package devicemanagement;


import eventclassification.EventCategory;
import eventclassification.EventTypes;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KernalInputDevices {

    private final File INPUT_DEVICE_INFO = new File("/proc/bus/input/devices");
    private ArrayList<InputDevice> devices;

    public KernalInputDevices() {
        System.out.println("Test message");
    }

    public ArrayList<InputDevice> getDevices() {
        return new ArrayList<>(devices);

    }

    public void update() {
        List<String> lines = readDeviceList();
        
        ArrayList<InputDevice> devices = new ArrayList<>();
        
        int i = 0;
        while (i < lines.size()) {
            String line = lines.get(i).toLowerCase();

            int[] id = new int[4];
            String name = "";
            File physicalPath = null;
            File systemFileSystem = null;

            while (!line.equals("")) {

                if (line.startsWith("i:")) {
                    id = getDeviceId(line);

                } else if (line.startsWith("n")) {
                    getDeviceName(line);

                }

                i++;
              
            }

            devices.add(new InputDevice(id, name, physicalPath, systemFileSystem));

            i++;

        }

    }

    public String getDeviceName(String line) {
        String regex = "\"([^\"]*)\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return "";

    }

    // to be implemented
    public int[] getDeviceId(String line) {
        return new int[4];
    }

    // to be implemented
    public File getHandlers(String line) {

        String regEx = "event[0-9]+";
        Pattern eventRegEx = Pattern.compile(regEx);
        Matcher matcher = eventRegEx.matcher(line);

        if (matcher.find()) {
            return new File("/dev/bus/input/" + matcher.group(0));

        }

        throw new CustomUncheckedException();



    }

    public EventCategory[] getPossibleEvents(String line) {
        String regex = "(?<=EV=)[0-9]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

        if (!matcher.find()) {
            return null;
        }

        String ev = matcher.group(0);

        int bitMap = 0;

        int index = ev.length() - 1;
        int bitShiftAmount = 0;

        int num;

        while (index >= 0) {
            num = Character.digit(ev.charAt(index), 16);
            bitMap |= (num << 4 * bitShiftAmount);

            index--;
            bitShiftAmount++;

        }

        ArrayList<Integer> indices = new ArrayList<>();

        for (int i = 0; i < Integer.SIZE; i++) {
            if ((bitMap | 1) == 1) {
                indices.add(i);
            }

            bitMap >>>= 1;

        }

        EventTypes[] possibleEvents = new EventTypes[indices.size()];

        for (int i = 0; i < indices.size(); i++) {
            possibleEvents[i] = EventTypes.byValue(indices.get(i));
        }

        return possibleEvents;
    }

    public List<String> readDeviceList() {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(INPUT_DEVICE_INFO)
        )) {

            return reader.lines().toList();

        } catch (IOException error) {
            System.out.println(INPUT_DEVICE_INFO + " cannnot be read");
            return new ArrayList<>();

        }

    }

}
