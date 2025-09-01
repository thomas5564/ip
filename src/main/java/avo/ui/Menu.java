package avo.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * UI for allowing user to choose command
 */
public class Menu extends VBox {
    private HashMap<String, ArrayList<String>> commandMap = new HashMap<>();
    private MainWindow mainWindow;
    /**
     * Constructor for this class
     */
    public Menu(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/Menu.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initialiseMap(commandMap);
        initialiseButtons(mainWindow);
    }

    /**
     * initialises menu buttons
     * @param mainWindow
     */
    private void initialiseButtons(MainWindow mainWindow) {
        for (String name: commandMap.keySet()) {
            if (commandMap.get(name).isEmpty()) {
                Button button = new Button(name);
                button.setOnAction(e-> {
                    mainWindow.handleMenuCommand(button.getText());
                });
                this.getChildren().add(button);
            } else {
                MenuDiv menuDiv = new MenuDiv(name, commandMap.get(name), mainWindow);
                this.getChildren().add(menuDiv);
            }
        }
    }

    /**
     * Initialises the commands in the hashmap
     * @param commandMap hashmap of commands
     */
    public void initialiseMap(HashMap<String, ArrayList<String>> commandMap) {
        ArrayList<String> taskTypes = new ArrayList<>(List.of("Event", "Deadline", "To-do"));
        ArrayList<String> manageTask = new ArrayList<>(List.of("Mark Task", "Unmark Task"));
        commandMap.put("Add Task", taskTypes);
        commandMap.put("Manage Task", manageTask);
        commandMap.put("Delete Task", new ArrayList<>());
        commandMap.put("Show List", new ArrayList<>());
        commandMap.put("Search", new ArrayList<>());
    }
}
