
import java.io.IOException;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
// import java.util.HashMap;


public class KernalInputDevices {
    private final File INPUT_DEVICE_INFO = new File("/proc/bus/input/devices");
    private ArrayList<InputDevice> devices;
    
    public KernalInputDevices() {
        System.out.println("Hi");
    }

    public ArrayList<InputDevice> update() {
        List<String> lines = readDeviceList();

        for (int i = 0; i < lines.size(); i++) {
            



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

    public String getPossibleEvents(String line) {
        String regex = "(?<=Ev=)[0-9]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        
        if (matcher.find()) {
            return matcher.group(1);
        }


        return "";
    }

    public List<String> readDeviceList() {
        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader(INPUT_DEVICE_INFO)
            );

            List<String> lines = reader.lines().toList();
            reader.close();

            return lines;

        } catch (IOException error) {
            System.out.println(INPUT_DEVICE_INFO + " cannnot be read");
            return new ArrayList<>();

        }

    }

}