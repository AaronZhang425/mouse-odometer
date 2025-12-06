

import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import eventclassification.EventCategory;
import eventclassification.EventTypes;

import java.util.regex.Matcher;

public class KernalInputDevices {

    private final File INPUT_DEVICE_INFO = new File("/proc/bus/input/devices");
    private ArrayList<InputDevice> devices;

    public KernalInputDevices() {
        System.out.println("Test message");
    }

    public ArrayList<InputDevice> update() {
        List<String> lines = readDeviceList();

        ArrayList<InputDevice> devices = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).toLowerCase();

            int[] id;

            if (line.startsWith("i:")) {
                id = getDeviceId(line);
            }

        }

        // String regex = "\"([^\"]*)\"";
        // Pattern pattern = Pattern.compile(regex);
        // for (int i = 0; i < lines.size(); i++) {
        //     Matcher matcher = pattern.matcher(lines.get(i));
        //     if(matcher.find()) {
        //         System.out.group(1);
        //     }
        // }
        return new ArrayList<>();

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

        EventCategory[] possibleEvents = new EventTypes[indices.size()];

        for (int i = 0; i < indices.size(); i++) {
            possibleEvents[i] = EventTypes.fromValue(indices.get(i));
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
