package avo.ui;
import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

import avo.main.Main;
import avo.responses.ErrorResponse;
import avo.responses.Response;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;


/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    private static Image avoImage = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/images/avo.jpg")));
    private static Image userImage = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/images/user.jpg")));
    private static Image angryAvoImage = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/images/angryAvo.png")));
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        dialog.getStyleClass().add("reply-label");
    }

    public static DialogBox getUserDialog(String text) {
        return new DialogBox(text, userImage);
    }

    public static DialogBox getAvoDialog(Response response) {
        var db = new DialogBox(response.getText(), avoImage);
        db.flip();
        return db;
    }
    public static DialogBox getErrorDialog(ErrorResponse errorResponse) {
        var db = new DialogBox(errorResponse.getText(), angryAvoImage);
        db.flip();
        db.lookup(".label").getStyleClass().add("error-label");
        return db;
    }
    public static DialogBox getAvoDialog(String text) {
        var db = new DialogBox(text, avoImage);
        db.flip();
        return db;
    }

    private void changeDialogStyle(String commandType) {
        switch (commandType) {
        case "AddCommand":
            dialog.getStyleClass().add("add-label");
            break;
        case "ChangeMarkCommand":
            dialog.getStyleClass().add("marked-label");
            break;
        case "DeleteCommand":
            dialog.getStyleClass().add("delete-label");
            break;
        default:
        }
    }
}
