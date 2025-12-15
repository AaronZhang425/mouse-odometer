package devicemanagement;


import java.io.IOException;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

// Old and needs to be replaced
public final class Mice {

    private final File INPUT_DEVICE_INFO = new File("/proc/bus/input/devices");
    private File mouseHandlerFile;

    public Mice(int eventNum) {
        this(new File("/dev/input/event" + eventNum));

    }

    public Mice(String filePath) {
        this(new File(filePath));
    }

    public Mice(File filePath) {
        // if (osCheck()) {ge);
        //     System.out.println("This app cannot run on windows.");
        //     return;

        // }


        // try {
        //     osCheck();
            
        // } catch (OsException e) {
        //     // throw new OsException();
        //     System.err.println(e.getMessage());
        //     System.err.println(e.getCause());
        // }

        mouseHandlerFile = filePath;

        if (!mouseHandlerFile.exists()) {
            System.out.println("Inputted handler file cannot be found");
            System.out.println("Automatically finding handler");
            parseMouseDriver();

        } else {
            checkFilePath();

        }

    }

    public Mice() {
        // if (osCheck()) {
        //     System.out.println("This app cannot run on this operating system.");
        //     return;
        // }

        // try {
        //     osCheck();
            
        // } catch (OsException e) {
        //     throw new OsException();
        //     // System.err.println(e.getMessage());
        //     // System.err.println(e.getCause());
        // }

        osCheck();

        parseMouseDriver();

    }

    public File getMouseHandlerFile() {
        return mouseHandlerFile;
    }

    public void parseMouseDriver() {
        List<String> lines = readDeviceList();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).toLowerCase();
            if (line.matches(".*mouse.*") && line.startsWith("n")) {
                line = lines.get(i + 4).toLowerCase(); // gets handler line

                findEventNum(line);
            }
        }

        System.out.println("Cannot find file");

    }

    public void findEventNum(String line) {
        String regEx = "event[0-9]+";
        Pattern eventRegEx = Pattern.compile(regEx);
        Matcher matcher = eventRegEx.matcher(line);

        if (matcher.find()) {
            mouseHandlerFile = new File("/dev/bus/input/" + matcher.group(0));

            checkFilePath();

        }
    }

    public void checkFilePath() {
        if (!mouseHandlerFile.canRead()) {
            System.out.println("Cannot read handler file");
            System.out.println("Check file read permissions");
        }
    }

    public List<String> readDeviceList() {
        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader(INPUT_DEVICE_INFO)
            );

            reader.close();

            return reader.lines().toList();

        } catch (IOException error) {
            System.out.println(INPUT_DEVICE_INFO + " cannnot be read");
            return new ArrayList<>();

        }
    }

    // public boolean osCheck() {
    //     String os = System.getProperty("os.name").toLowerCase();
    //     return os.startsWith("windows");
    // }

    public void osCheck() throws OsException {
        String os = System.getProperty("os.name").toLowerCase();

        if (!os.equals("linux")) {
            throw new OsException();
        }
        // if (true) {
        //     throw new OsException();
        // }

    }


}
