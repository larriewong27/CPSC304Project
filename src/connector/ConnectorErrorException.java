package connector;

public class ConnectorErrorException extends Exception{
    protected ConnectorErrorException(String cause) {
        super(cause);
    }
}
