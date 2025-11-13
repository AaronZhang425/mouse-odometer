
import java.io.IOException;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


// import java.util.stream.Stream;

public class Mouse {
    private final File inputDeviceInfo = new File("/proc/bus/input/devices");
    private File mouseHandlerFile;

    public Mouse(int eventNum) {
        this(new File("/dev/input/event" + eventNum));
        
    }

    public Mouse(String filePath) {
        this(new File(filePath));
    }

    public Mouse(File filePath) {
        if (isWindows()){
            System.out.println("This app cannot run on windows.");
            return;
        
        }

        System.out.println(filePath);
        mouseHandlerFile = filePath;
        System.out.println(mouseHandlerFile);
        System.out.println(mouseHandlerFile.canRead());
        System.out.println(mouseHandlerFile.exists());

        if (!mouseHandlerFile.canRead()) {
            System.out.println("Manually inputted handler file cannot be read");
            System.out.println("Automatically finding handler");
            parseMouseDriver();
        }

    }

    public Mouse() {
        if (isWindows()){
            System.out.println("This app cannot run on windows.");
            return;
        }

        parseMouseDriver();

    }

    public File getMouseHandlerFile() {
        return mouseHandlerFile;
    }



    public void parseMouseDriver() {
        List<String> lines = readDeviceList();

        int i = 0;

        while (i < lines.size()) {
            String line = lines.get(i).toLowerCase();

            if (line.matches(".*mouse.*") && line.startsWith("n")) {
                String handlerLine = lines.get(i + 4).toLowerCase();
                System.out.println(handlerLine);

                String regEx = "event[0-9]*";
                Pattern eventRegEx = Pattern.compile(regEx);

                Matcher matcher = eventRegEx.matcher(handlerLine);
                
                if (matcher.find()) {
                    System.out.println(matcher.group(0));
                    
                    mouseHandlerFile = new File(
                        "/dev/bus/input/" + matcher.group(0)
                    );

                    return;


                }

            }

            i++;
        }

        System.out.println("Cannot find file");

    }

    public List<String> readDeviceList() {
        try {
            BufferedReader reader = (
                new BufferedReader(new FileReader(inputDeviceInfo))
            );

            return reader.lines().toList();

        
        } catch (IOException error) {
            System.out.println(inputDeviceInfo + " cannnot be read");
            List<String> emptyList = new ArrayList<String>();

            return emptyList;
        } 
    }

    public boolean isWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.startsWith("windows");
    }

}
