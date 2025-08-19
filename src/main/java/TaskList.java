public class TaskList {
    private static int numberOfTasks = 0;
    private static Task[] tasks = new Task[100];
    public String toString(){
        StringBuilder listString = new StringBuilder();
        for(int i = 0; i< tasks.length; i++){
            if(tasks[i] == null){
                break;
            }
            String line = "\n         " + (i+1) + "."+ tasks[i].toString();
            listString.append(line);
        }
        return listString.toString();
    }

    public void deleteTask(int index) throws InvalidIndexException {
        if (index >= numberOfTasks || index < 0) {
            throw new InvalidIndexException(index + 1, numberOfTasks);
        }
        Task selectedTask = tasks[index];
        for (int i = index; i < numberOfTasks - 1; i++) {
            tasks[i] = tasks[i + 1];
        }
        tasks[numberOfTasks - 1] = null;
        numberOfTasks--;
        Ui.removeTaskResponse(selectedTask, numberOfTasks);
    }
    public void mark(int index) throws InvalidIndexException {
        if(index > numberOfTasks-1 || index<0){
            throw new InvalidIndexException(index,numberOfTasks);
        }else{
            tasks[index].mark();
            Ui.markTaskResponse(tasks[index].toString());
        }
        Avo.rewriteFileFromList(numberOfTasks,tasks);
    }

    public void addTask (Task currentTask,boolean isAddingFromMemory){
        tasks[numberOfTasks] = currentTask;
        numberOfTasks++;
        if(!isAddingFromMemory){
            Avo.appendToFile(Avo.pathName, currentTask.getStorageString());
            Ui.addTaskResponse(currentTask,numberOfTasks);
        }
    }

    public void unmark(int index) throws InvalidIndexException {
        if(index > numberOfTasks-1 || index<0){
            throw new InvalidIndexException(index,numberOfTasks);
        }else{
            tasks[index].unmark();
            Ui.unmarkTaskResponse(tasks[index].toString());
        }
        Avo.rewriteFileFromList(numberOfTasks,tasks);
    }
}
