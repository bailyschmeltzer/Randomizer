package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainController {
    public Label playerCountLabel;
    public TextField playerCount;
    public Button playerCountSubmit;
    public static int count;

    @FXML
    protected void onButtonClick(ActionEvent event) throws IOException {
        String stringCount = playerCount.getText();
        if (stringCount == null || stringCount.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Number of players cannot be empty!");
            alert.showAndWait();
            return;
        }
        try {
            count = Integer.parseInt(stringCount);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Number of players must be a valid number");
            alert.showAndWait();
            return;
        }

        if (count < 4) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Number of players cannot be less than 4!");
            alert.showAndWait();
            return;
        }

        if (count % 2 != 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Number of players must be even!");
            alert.showAndWait();
            return;
        }



        Parent playerEntryScreen = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/player-name-entry.fxml")));
        Scene scene = new Scene(playerEntryScreen);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}