package disc;

public class OutOfDiscMemoryException extends Exception{
    public OutOfDiscMemoryException(String message) {
        super(message);
    }
}
