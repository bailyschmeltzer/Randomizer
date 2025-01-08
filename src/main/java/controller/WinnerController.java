package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WinnerController {

    @FXML
    public Label winnerLabel;
    @FXML
    public Label winnerText = new Label();


    @FXML
    public void setWinnerText(String winner) {
        winnerText.setText(winner);
    }
}
