package avo.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import avo.main.Avo;
import avo.main.Main;
import avo.responses.ErrorResponse;
import avo.responses.Response;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;



/**
 * Root node of application
 */
public class MainWindow extends AnchorPane {
    private Avo avo;
    private String currentInput;
    private boolean isMenuOut = false;
    private boolean autoScrollEnabled = false;
    private Image avoImage = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/images/avo.jpg")));
    private Image userImage = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/images/user.jpg")));
    private AvoSpeaker speaker;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private Button sendButton;
    @FXML
    private Button menuButton;
    @FXML
    private TextField userInput;
    @FXML
    private VBox commandBox;
    @FXML
    private HBox datePickerContainer;
    public void setAvo(Avo avo) {
        this.avo = avo;
        this.speaker = avo.getSpeaker();
    }

    /**
     * Initialises Avo to take in a new to-do task
     */
    @FXML
    public void initialize() {
        InputCollator todoInputCollator = () -> {
            String instruction = userInput.getText();
            String input = String.format(
                    "todo %s",
                    instruction);
            return input;
        };
        userInput.setOnAction(e -> {
            handleUserInput(todoInputCollator);
        });
        userInput.setPromptText("Write to-do instruction...");
    }
    /**
     * Adds greeting dialog box from avo
     */
    public void greet() {
        String greetString = speaker.greet();
        DialogBox greetDialog = DialogBox.getDukeDialog(greetString, avoImage);
        dialogContainer.getChildren().add(greetDialog);
        scrollPane.setVvalue(0.0);
    }
    private InputCollator buildDatedTaskCollator(String formatString, String... labels) {
        List<DatePicker> pickers = new ArrayList<>();
        for (String labelText : labels) {
            DatePicker dp = new DatePicker();
            dp.setPrefWidth(150);
            dp.setPromptText(labelText);
            datePickerContainer.getChildren().add(dp);
            pickers.add(dp);
        }
        InputCollator currentInputCollator = () -> {
            String[] inputs = new String[labels.length + 1];
            int counter = 1;
            String instruction = userInput.getText();
            inputs[0] = instruction;
            for (Node node: datePickerContainer.getChildren()) {
                DatePicker dp = (DatePicker) node;
                LocalDate date = dp.getValue();
                inputs[counter] = date.toString();
                counter++;
            }
            String input = String.format(
                    formatString,
                    (Object[]) inputs
            );
            return input;
        };
        return currentInputCollator;
    }


    /**
     * Handle user inputs from the textfield and reflect it in the dialogbox
     */
    public void handleUserInput(InputCollator inputCollator) {
        if (!autoScrollEnabled) {
            scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
            autoScrollEnabled = true;
        }
        String input = inputCollator.getInput();
        Response response = speaker.getResponse(input);
        DialogBox avoDialog = DialogBox.getDukeDialog(response, avoImage);
        if (response instanceof ErrorResponse) {
            avoDialog.lookup(".label").getStyleClass().add("error-label");
        }
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                avoDialog
        );
        for (Node node: datePickerContainer.getChildren()) {
            if (node instanceof DatePicker) {
                DatePicker datePicker = (DatePicker) node;
                datePicker.setValue(null);
            }
        }
        userInput.setText("");
    }
    /**
     * toggle menu
     */
    public void toggleMenu() {
        System.out.println("toggling menu");
        if (isMenuOut) {
            commandBox.getChildren().clear();
            this.isMenuOut = false;
        } else {
            Menu menu = new Menu(this);
            commandBox.getChildren().add(menu);
            this.isMenuOut = true;
        }
    }
    public InputCollator getIndexCollator(String command) {
        InputCollator indexInputCollator = () -> {
            String index = userInput.getText();
            return String.format(
                    "%s %s",
                    command,
                    index);
        };
        return indexInputCollator;
    }
    public void showList() {
        handleUserInput(() -> "list");
    }
    /**
     * handles responses to the menu button presses
     * @param commandString corresponding label on the button
     */
    public void handleMenuCommand(String commandString) {
        datePickerContainer.getChildren().clear();
        switch (commandString) {
        case "Show List":
            handleUserInput(() -> "list");
            toggleMenu();
            break;
        case "Event":
            InputCollator eventInputCollator = buildDatedTaskCollator(
                    "event %s /from %s /to %s",
                    "start",
                    "end");
            userInput.setOnAction(e -> {
                handleUserInput(eventInputCollator);
            });
            toggleMenu();
            break;

        case "Deadline":
            InputCollator deadlineInputCollator = buildDatedTaskCollator(
                    "deadline %s /by %s",
                "deadline");
            userInput.setOnAction(e -> {
                handleUserInput(deadlineInputCollator);
            });
            userInput.setPromptText("Write deadline instruction...");
            toggleMenu();
            break;
        case "To-do":
            InputCollator todoInputCollator = () -> {
                String instruction = userInput.getText();
                String input = String.format(
                        "todo %s",
                        instruction);
                return input;
            };
            userInput.setOnAction(e -> {
                handleUserInput(todoInputCollator);
            });
            userInput.setPromptText("Write to-do instruction...");
            toggleMenu();
            break;
        case "Mark Task":
            userInput.setOnAction(e -> {
                handleUserInput(getIndexCollator("mark"));
            });
            userInput.setPromptText("Type task index to mark...");
            toggleMenu();
            break;
        case "Unmark Task":
            userInput.setOnAction(e -> {
                handleUserInput(getIndexCollator("unmark"));
            });
            userInput.setPromptText("Type task index to unmark...");
            toggleMenu();
            break;
        case "Delete Task":
            userInput.setOnAction(e -> {
                handleUserInput(getIndexCollator("delete"));
            });
            userInput.setPromptText("Type task index to delete...");
            toggleMenu();
            break;
        default:
            userInput.clear();
            break;
        }
    }
}
