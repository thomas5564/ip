package storage;

import tasks.Task;
import main.Avo;
import parser.Parser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Storage {
    private String filePath;
    private File storageFile;
    private Scanner fileScanner;
    public Storage(String filePath){
        try{
            this.filePath = filePath;
            storageFile  = new File(filePath);
            fileScanner = new Scanner(storageFile);
            readFile();
        }catch(FileNotFoundException e){
            System.out.println("Data file is not found. If you want your tasks to be saved,\n " +
                    "Do the following:\n" +
                    "1. Make a folder named \"data\" in your current directory\n" +
                    "2. Add a text file named Avo.txt into it");
        }
    }
    public void appendToFile(String filePath, String textToAdd){
        try{
            FileWriter fw = new FileWriter(filePath, true); // create a FileWriter in append mode
            fw.write(textToAdd + "\n");
            fw.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    public void rewriteFileFromList(int numberOfTasks, Task[] tasks){
        try{
            int counter = 0;
            FileWriter fileClearer = new FileWriter(filePath, false);
            fileClearer.append("");
            while(counter<numberOfTasks){
                appendToFile(filePath,tasks[counter].getStorageString());
                counter++;
            }
            fileClearer.close();
        }catch(FileNotFoundException e){
            System.out.println("File is not found. If you want your tasks to be saved,\n " +
                    "add the file and start the program again");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void readFile(){
        while (fileScanner.hasNext()) {
            String nextEntry =  fileScanner.nextLine();
            Task nextTask = Parser.parseTaskFromStorage(nextEntry);
            Avo.taskList.addTask(nextTask,true);
        }
        fileScanner.close();
    }
}
