package ce326.hw3;

public class UnsupportedFileFormatException extends Exception {
    /** Default constructor */
    public UnsupportedFileFormatException() {
        super();
    }

    /**
     * Constructs the exception with a custom message.
     * @param msg detailed error message
     */
    public UnsupportedFileFormatException(String msg) {
        super(msg);
    }
}
