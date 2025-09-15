package avo.responses;

import avo.commands.Command;
import avo.tasks.Task;

/**
 * Response class for task deletion
 */
public class DeletionResponse extends CommandResponse {
    private Task taskSelected;
    /**
     * Command response for deletion
     *
     * @param text        text for response
     * @param commandType type of command
     */
    public DeletionResponse(String text, Command commandType, Task taskSelected) {
        super(text, commandType);
        this.taskSelected = taskSelected;
    }
    public Task getTask() {
        return taskSelected;
    }
}
