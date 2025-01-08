package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Winner {


    public StringProperty winnerText;

    public void setWinner(String teamName) {
        this.winnerText = new SimpleStringProperty(teamName);
    }
    public StringProperty getWinnerText() { return winnerText; }
}
