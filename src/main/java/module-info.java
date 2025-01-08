module tournament.randomizer {
    requires javafx.controls;
    requires javafx.fxml;


    opens name to javafx.fxml;
    exports name;

    opens controller to javafx.fxml;
    exports controller;
}