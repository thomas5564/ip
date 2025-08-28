package avo.responses;

/**
 * Response for errors
 */
public class ErrorResponse extends Response {
    /**
     * Constructor for this class
     * @param text text for the response
     */
    public ErrorResponse(String text) {
        super(text);
    }
}
