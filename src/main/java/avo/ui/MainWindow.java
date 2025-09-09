package avo.ui;

import java.time.LocalDate;
import java.util.ArrayList;

import avo.main.Avo;
import avo.responses.ByeResponse;
import avo.responses.ErrorResponse;
import avo.responses.Response;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
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
    private AvoSpeaker speaker;
    private boolean isGuiMode = false;
    private boolean isStatsOpen = false;
    private ChartContainer chartContainer;
    @FXML
    private AnchorPane root;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private Button sendButton;
    @FXML
    private Button menuButton;
    @FXML
    private Button showListButton;
    @FXML
    private TextField userInput;
    @FXML
    private VBox commandBox;
    @FXML
    private HBox datePickerContainer;
    public void setAvo(Avo avo) {
        this.avo = avo;
        this.speaker = avo.getSpeaker();
        this.chartContainer = new ChartContainer(avo.getTaskList());
    }

    /**
     * Toggles the GUI mode and send the appropriate message
     */
    public void toggleGuiMode() {
        String notification;
        if (!isGuiMode) {
            notification = """
                    GUI mode is on!
                    Press ALT key again to switch back to Command mode
                    """;
            showListButton.setVisible(true);
            menuButton.setVisible(true);
            updateInputs(getTodoCollator());
            userInput.setPromptText("Write to-do instruction...");
        } else {
            notification = """
                    Command mode is on!
                    Press ALT again key to switch back to GUI mode
                    """;
            showListButton.setVisible(false);
            menuButton.setVisible(false);
            userInput.setPromptText("Write command...");
            InputCollator inputCollator = () -> userInput.getText();
            updateInputs(inputCollator);
        }
        DialogBox greetDialog = DialogBox.getAvoDialog(notification);
        dialogContainer.getChildren().add(greetDialog);
        isGuiMode = !isGuiMode;
    }

    /**
     * Toggles the stats window
     */
    public void toggleStatWindow() {
        if (!isStatsOpen) {
            scrollPane.setContent(chartContainer);
        } else {
            scrollPane.setContent(dialogContainer);
        }
        isStatsOpen = !isStatsOpen;
    }
    /**
     * Initialises Avo to take in a new to-do task
     */
    @FXML
    public void initialize() {
        menuButton.setVisible(false);
        showListButton.setVisible(false);
        InputCollator inputCollator = () -> userInput.getText();
        updateInputs(inputCollator);
        root.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ALT) {
                toggleGuiMode();
                if (!autoScrollEnabled) {
                    scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
                    autoScrollEnabled = true;
                }
            }
            if (e.getCode() == KeyCode.F2) {
                toggleStatWindow();
            }
        });
        userInput.setPromptText("Write command...");
    }
    /**
     * Updates the input collation process
     * @param inputCollator new input collator
     */
    public void updateInputs(InputCollator inputCollator) {
        userInput.setOnAction(e -> handleUserInput(inputCollator));
        sendButton.setOnAction(e -> handleUserInput(inputCollator));
    }
    /**
     * Adds greeting dialog box from avo
     */
    public void greet() {
        String greetString = speaker.greet();
        DialogBox greetDialog = DialogBox.getAvoDialog(greetString);
        dialogContainer.getChildren().add(greetDialog);
        scrollPane.setVvalue(0.0);
    }

    /**
     * Gets the to-do input collator
     */
    public InputCollator getTodoCollator() {
        InputCollator todoInputCollator = () -> {
            String instruction = userInput.getText();
            String input = String.format(
                    "todo %s",
                    instruction);
            return input;
        };
        return todoInputCollator;
    }
    private InputCollator buildDatedTaskCollator(String formatString, String... labels) {
        assert labels.length >= 1 : "Invalid number of labels!";
        ArrayList<DatePicker> pickers = new ArrayList<>();
        for (String labelText : labels) {
            addDatePicker(pickers, labelText);
        }
        InputCollator currentInputCollator = () -> {
            String[] inputs = new String[labels.length + 1];
            getInputsFromDatepickers(inputs);
            String input = String.format(
                    formatString,
                    (Object[]) inputs
            );
            return input;
        };
        return currentInputCollator;
    }
    public void getInputsFromDatepickers(String[] dateInputs) {
        int counter = 1;
        String instruction = userInput.getText();
        dateInputs[0] = instruction;
        for (Node node: datePickerContainer.getChildren()) {
            DatePicker dp = (DatePicker) node;
            LocalDate date = dp.getValue();
            dateInputs[counter] = date == null ? "-" : date.toString();
            counter++;
        }
    }
    /**
     * Adds configured date picker to the UI and into an arraylist
     * @param pickers aforementioned arraylist
     * @param labelText prompt text for date picker
     */
    public void addDatePicker(ArrayList<DatePicker> pickers, String labelText) {
        DatePicker dp = new DatePicker();
        dp.setPrefWidth(150);
        dp.setPromptText(labelText);
        datePickerContainer.getChildren().add(dp);
        pickers.add(dp);
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
        addDialogBoxes(input);
        resetDatePickers();
        userInput.setText("");
        chartContainer.updateCharts();
    }

    /**
     * Resets date pickers to become empty
     */
    public void resetDatePickers() {
        for (Node node: datePickerContainer.getChildren()) {
            if (node instanceof DatePicker) {
                DatePicker datePicker = (DatePicker) node;
                datePicker.setValue(null);
            }
        }
    }

    /**
     * For a given input from the user, this method will:
     * 1. get the appropriate response from avo
     * 2. reflect the input and response in the DialogContainer
     * @param input user's input
     */
    public void addDialogBoxes(String input) {
        Response response = speaker.getResponse(input);
        DialogBox avoDialog = getResponseDialogBox(response);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input),
                avoDialog
        );
    }

    /**
     * Gets the dialog box for a given response
     * @param response from avo
     * @return the Dialog Box
     */
    private static DialogBox getResponseDialogBox(Response response) {
        if (response instanceof ByeResponse) {
            System.exit(0);
            return DialogBox.getAvoDialog(response);
        }
        return response instanceof ErrorResponse
                ? DialogBox.getErrorDialog((ErrorResponse) response)
                : DialogBox.getAvoDialog(response);
    }

    /**
     * toggle menu
     */
    public void toggleMenu() {
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
        return () -> {
            String index = userInput.getText();
            return String.format(
                    "%s %s",
                    command,
                    index);
        };
    }
    public void showList() {
        handleUserInput(() -> "list");
    }
    /**
     * handles responses to the menu button presses
     * @param commandString corresponding label on the button
     */
    public void handleMenuCommand(String commandString) {
        if (!commandString.equals("Show List")) {
            datePickerContainer.getChildren().clear();
        }
        switch (commandString) {
        case "Show Weekly List":
            handleUserInput(() -> "listw");
            toggleMenu();
            break;
        case "View Stats":
            handleUserInput(() -> "stat");
            toggleMenu();
            break;
        case "Show List":
            handleUserInput(() -> "list");
            toggleMenu();
            break;
        case "Event":
            InputCollator eventInputCollator = buildDatedTaskCollator(
                    "event %s /from %s /to %s",
                    "start",
                    "end");
            updateInputs(eventInputCollator);
            userInput.setPromptText("Write event instruction...");
            toggleMenu();
            break;

        case "Deadline":
            InputCollator deadlineInputCollator = buildDatedTaskCollator(
                    "deadline %s /by %s",
                "deadline");
            updateInputs(deadlineInputCollator);
            userInput.setPromptText("Write deadline instruction...");
            toggleMenu();
            break;
        case "To-do":
            updateInputs(getTodoCollator());
            userInput.setPromptText("Write to-do instruction...");
            toggleMenu();
            break;
        case "Mark Task":
            updateInputs(getIndexCollator("mark"));
            userInput.setPromptText("Type task index to mark...");
            toggleMenu();
            break;
        case "Unmark Task":
            updateInputs(getIndexCollator("unmark"));
            userInput.setPromptText("Type task index to unmark...");
            toggleMenu();
            break;
        case "Search":
            userInput.setPromptText("Type what you're searching for...");
            InputCollator searchInputCollator = () -> {
                String instruction = userInput.getText();
                String input = String.format(
                        "find %s",
                        instruction);
                return input;
            };
            updateInputs(searchInputCollator);
            toggleMenu();
            break;
        case "Delete Task":
            updateInputs(getIndexCollator("delete"));
            userInput.setPromptText("Type task index to delete...");
            toggleMenu();
            break;
        case "Exit":
            System.exit(0);
            break;
        default:
            ErrorResponse errorResponse = new ErrorResponse("Invalid input!");
            getResponseDialogBox(errorResponse);
            break;
        }
    }
}
