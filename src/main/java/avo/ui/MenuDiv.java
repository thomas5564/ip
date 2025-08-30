package avo.ui;
import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


/**
 * Menu bar class
 */
public class MenuDiv extends VBox {
    private Label label;
    private String name;
    private boolean isListing = false;
    private VBox sublistBox = new VBox();

    /**
     * divs that show a sublist
     * @param name name of label
     * @param sublist sublist that is shown
     */
    public MenuDiv(String name, ArrayList<String> sublist) {
        this.label = new Label(name);
        this.getStyleClass().add("menu-root");
        sublistBox.getStyleClass().add("sublist");
        label.setOnMouseClicked(e -> toggleSublist(sublist));
        for (String subitem : sublist) {
            Label label = new Label(subitem);
            sublistBox.getChildren().add(label);
        }
        this.getChildren().add(label);
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
