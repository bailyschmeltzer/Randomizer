package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Team {
    private final StringProperty playerNames;
    private StringProperty teamName;

    public Team(Player player1, Player player2) {
        this.teamName = null;
        this.playerNames = new SimpleStringProperty(player1.getName() + " and " + player2.getName());
    }

    public StringProperty playerNamesProperty() {
        return playerNames;
    }

    public StringProperty teamNamesProperty() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = new SimpleStringProperty(teamName);
    }

    public String getPlayerNames() {
        return playerNames.get();
    }

    public String getTeamNames() {
        return teamName != null ? teamName.get() : null;
    }
}