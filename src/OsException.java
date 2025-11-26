import java.io.IOException;

public class OsException extends RuntimeException {
    public OsException() {
        super("Linux kernal files cannot be read. Check OS.", new IOException());
    }



}