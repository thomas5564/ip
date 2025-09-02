package avo.responses;

import avo.commands.Command;

/**
 * Response to bye command
 */
public class ByeResponse extends CommandResponse {
    /**
     * Constructor for this class
     *
     * @param text text for the response
     */
    public ByeResponse(String text) {
        super(text, Command.BYE);
    }
}
