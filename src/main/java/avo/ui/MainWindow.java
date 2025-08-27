package avo.ui;

import avo.main.Avo;
import javafx.scene.layout.AnchorPane;

/**
 * Root node of application
 */
public class MainWindow extends AnchorPane {
    private Avo avo;
    public void setAvo(Avo avo) {
        this.avo = avo;
    }
}
