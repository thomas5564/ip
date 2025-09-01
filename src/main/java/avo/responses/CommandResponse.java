package avo.responses;

import avo.commands.Command;

/**
 * Responses to commands
 */
public class CommandResponse extends Response {
    private Command commandType;

    /**
     * Command response
     * @param text text for response
     * @param commandType type of command
     */
    public CommandResponse(String text, Command commandType) {
        super(text);
        this.commandType = commandType;
    }
}
