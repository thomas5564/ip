package avo.ui;

import java.util.Objects;

import avo.main.Avo;
import avo.main.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;



/**
 * Root node of application
 */
public class MainWindow extends AnchorPane {
    private Avo avo;
    private Image avoImage = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/images/avo.jpg")));
    private Image userImage = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/images/user.jpg")));

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private Button sendButton;
    @FXML
    private TextField userInput;
    public void setAvo(Avo avo) {
        this.avo = avo;
    }

    /**
     * Handle user inputs from the textfield and reflect it in the dialogbox
     */
    public void handleUserInput() {
        String input = userInput.getText();
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(input, avoImage)
        );
    }
}
