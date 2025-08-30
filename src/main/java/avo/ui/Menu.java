package avo.ui;

import avo.main.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * UI for allowing user to choose command
 */
public class Menu extends VBox {
    private HashMap<String, ArrayList<String>> buttonNames = new HashMap<>();
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
        ArrayList<String> taskTypes = new ArrayList<>(List.of("Event", "Deadline", "Todo"));
        ArrayList<String> manageTask = new ArrayList<>(List.of("Mark Task", "Unmark Task"));
        buttonNames.put("Add Task", taskTypes);
        buttonNames.put("Manage Task", manageTask);
        buttonNames.put("Remove Task", new ArrayList<>());
        buttonNames.put("Show List", new ArrayList<>());
        for (String name: buttonNames.keySet()) {
            if (buttonNames.get(name).isEmpty()) {
                Button button = new Button(name);
                this.getChildren().add(button);
            } else {
                MenuDiv menuDiv = new MenuDiv(name, buttonNames.get(name), mainWindow);
                this.getChildren().add(menuDiv);
            }
        }
    }
}
