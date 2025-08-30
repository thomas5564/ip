package avo.ui;
import java.util.ArrayList;

import avo.main.Main;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


/**
 * Menu bar class
 */
public class MenuDiv extends VBox {
    private MainWindow mainWindow;
    private Button button;
    private String name;
    private boolean isListing = false;
    private VBox sublistBox = new VBox();

    /**
     * divs that show a sublist
     * @param name name of label
     * @param sublist sublist that is shown
     */
    public MenuDiv(String name, ArrayList<String> sublist, MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.button = new Button(name);
        button.setPickOnBounds(true);
        this.getStyleClass().add("menu-root");
        sublistBox.getStyleClass().add("sublist");
        button.setOnMouseClicked(e -> toggleSublist(sublist));
        for (String subitem : sublist) {
            Button button = new Button(subitem);
            button.setOnAction(e-> {
                mainWindow.handleMenuCommand(button.getText());
            });
            sublistBox.getChildren().add(button);
        }
        this.getChildren().add(button);
        System.out.println(sublistBox.getStyleClass());
    }

    /**
     * Displays the sublist in the menu div itself
     * @param subList
     */
    public void toggleSublist(ArrayList<String> subList) {
        if (!isListing) {
            this.getChildren().add(sublistBox);
            sublistBox.setVisible(true);
            sublistBox.setManaged(true);
            isListing = true;
        } else {
            this.getChildren().remove(sublistBox);
            isListing = false;
        }
    }
}
