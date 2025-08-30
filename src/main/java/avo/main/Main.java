package avo.main;

import avo.ui.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main class which sets up the stage, scene and root node (mainWindow).
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        MainWindow mainWindow = new MainWindow();
        Avo avo = new Avo();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
        loader.setRoot(mainWindow);
        AnchorPane rootNode = loader.load();
        Scene scene = new Scene(rootNode);
        stage.setScene(scene);
        loader.<MainWindow>getController().setAvo(avo);
        loader.<MainWindow>getController().greet();
        stage.show();
    }
}
