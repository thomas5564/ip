package avo.responses;

/**
 * Class for all response types
 */
public abstract class Response {
    private String text;

    /**
     * Constructor for this class
     * @param text text for the response
     */
    public Response(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
