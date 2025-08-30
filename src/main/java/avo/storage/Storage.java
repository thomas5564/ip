package avo.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import avo.main.Avo;
import avo.parser.Parser;
import avo.tasks.Task;
import avo.tasks.TaskList;


/**
 * Represents the storage file and contains methods with regard to it
 */
public class Storage {
    private String filePath;
    private File storageFile;
    private Scanner fileScanner;

    /**
     * constructor for this class
     * @param filePath file path of storage file (./data/avo.txt)
     */
    public Storage(String filePath) {
        try {
            this.filePath = filePath;
            storageFile = new File(filePath);
            fileScanner = new Scanner(storageFile);
        } catch (FileNotFoundException e) {
            System.out.println("Data file is not found. If you want your tasks to be saved,\n "
                    + "Do the following:\n"
                    + "1. Make a folder named \"data\" in your current directory\n"
                    + "2. Add a text file named Avo.txt into it");
        }
    }

    /**
     * Adds a storage string to the end of the storage file
     * @param textToAdd text to be added to the bottom of storage file
     */
    public void appendToFile(String textToAdd) {
        try {
            FileWriter fw = new FileWriter(filePath, true); // create a FileWriter in append mode
            fw.write(textToAdd + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Rewrites the storage file, writing the input tasks as their respective storage strings
     * @param numberOfTasks current number of tasks in the task list
     * @param tasks task list itself
     */
    public void rewriteFileFromList(int numberOfTasks, Task[] tasks) {
        try {
            int counter = 0;
            FileWriter fileClearer = new FileWriter(filePath, false);
            fileClearer.append("");
            while (counter < numberOfTasks) {
                appendToFile(tasks[counter].getStorageString());
                counter++;
            }
            fileClearer.close();
        } catch (FileNotFoundException e) {
            System.out.println("File is not found. If you want your tasks to be saved,\n "
                   + "add the file and start the program again");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads the storage file and adds the stored tasks to the Task List
     */
    public void readFile(TaskList taskList) {
        while (fileScanner.hasNext()) {
            String nextEntry = fileScanner.nextLine();
            Task nextTask = Parser.parseTaskFromStorage(nextEntry);
            taskList.addTask(nextTask, false);
        }
        fileScanner.close();
    }
}
