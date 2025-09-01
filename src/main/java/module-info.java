module Avo.main {
    // JavaFX dependencies
    requires javafx.controls;
    requires javafx.fxml;

    // Open your main package for FXML reflection
    opens avo.main to javafx.fxml;

    // Export main package so other modules (if any) can use it
    exports avo.main;
}
