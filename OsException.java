import java.io.IOException;

public class OsException extends RuntimeException {
    public OsException() {
        super("This program only supports linux", new IOException());
    }



}