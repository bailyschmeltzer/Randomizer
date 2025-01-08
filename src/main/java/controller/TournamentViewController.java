package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Matchup;
import model.Team;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

public class TournamentViewController {
    @FXML
    public Label currentMatchupLabel;
    @FXML
    public ComboBox<String> selectWinnerComboBox;
    @FXML
    public Label currentMatchupView;
    public static ObservableList<Team> teamList = FXCollections.observableArrayList();
    @FXML
    public Button enterWinner;
    private ArrayList<Matchup> tournament = new ArrayList<>();

    @FXML
    public void onActionSelectWinner(ActionEvent actionEvent) throws IOException {
        String selection = selectWinnerComboBox.getValue();

        if (selection == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a winner.");
            alert.showAndWait();
            return;
        }

        // Get the current matchup (the first one in the tournament)
        Matchup matchup = tournament.get(0);
        String team1Name = matchup.getTeam1();
        String team2Name = matchup.getTeam2();

        // Remove the processed matchup from the tournament list
        tournament.remove(0);

        Team team1 = matchup.team1;
        Team team2 = matchup.team2;
        // Check which team is the winner based on the selection

        if (Objects.equals(team1Name, selection)) {
            teamList.add(team1);
        } else if (Objects.equals(team2Name, selection)) {
            teamList.add(team2);
        }

        teamList.forEach(team -> System.out.println("Remaining team: " + team.getPlayerNames()));
        // After each selection, check if we need to proceed to the next matchup or end the tournament
        nextMatchup();
    }



    private void nextMatchup() throws IOException {
        // If tournament is empty and there is more than one team in teamList, create next round of matchups
        if (tournament.isEmpty() && teamList.size() > 1) {
            createNextRound();
        }

        // If only one team is left and tournament is empty, show the winner
        if (teamList.size() == 1 && tournament.isEmpty()) {
            showWinner();  // Only show winner if the tournament is finished
            return;
        }

        // Otherwise, proceed to the next match if tournament is not empty
        if (!tournament.isEmpty()) {
            getNextMatch();  // Proceed to next match
        }
    }



    private void createNextRound() {
        ArrayList<Matchup> nextRound = new ArrayList<>();

        // Ensure we only create matchups when we have at least two teams
        for (int i = 0; i < teamList.size(); i += 2) {
            if (i + 1 < teamList.size()) {
                nextRound.add(new Matchup(teamList.get(i), teamList.get(i + 1)));
            } else {
                // Odd number of teams: the last one automatically advances
                nextRound.add(new Matchup(teamList.get(i), null));  // Give the last team a "bye"
            }
        }

        teamList.clear();
        // Update tournament with the new round of matchups
        tournament = nextRound;

        // Proceed to the next match
        getNextMatch();
    }

    private void showWinner() throws IOException {

        // Get the final winner from the teamList
        Team winner = teamList.get(0);  // Only one team should be left
        String winnerText = winner.teamNamesProperty() != null ? winner.teamNamesProperty().get() : winner.playerNamesProperty().get();

        // Load the winner screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/winner.fxml"));
        Parent root = loader.load();
        WinnerController winnerController = loader.getController();
        winnerController.setWinnerText(winnerText);

        // Show the winner screen in a new stage
        Stage stage = new Stage();
        stage.setTitle("Winner");
        stage.setScene(new Scene(root));

        // Close the previous stage (the tournament stage)
        Stage currentStage = (Stage) currentMatchupLabel.getScene().getWindow();
        currentStage.close();

        stage.show();
    }

    public void setCurrentMatchupView(String string) {
        currentMatchupView.setText(string);
    }

    public void startTournament() {
        Random rand = new Random();

        // Check if we have enough teams to start the tournament
        if (teamList.size() < 2) {
            System.out.println("Not enough teams to start the tournament.");
            return;
        }


        // Shuffle the team list to randomize the matchups
        Collections.shuffle(teamList, rand);

        ArrayList<Team> tempTeamList = new ArrayList<>(teamList);  // Use a temporary list to avoid modifying the original teamList
        teamList.clear();
        Matchup matchup;

        // Pair teams in the shuffled list to create matchups
        while (tempTeamList.size() > 1) {
            Team team1 = tempTeamList.get(0);  // Get first team
            tempTeamList.remove(0);            // Remove first team from the list

            Team team2 = tempTeamList.get(0);  // Get second team
            tempTeamList.remove(0);            // Remove second team from the list

            matchup = new Matchup(team1, team2);
            tournament.add(matchup);
        }

        // Handle the "bye" scenario if there's an odd number of teams
        if (tempTeamList.size() == 1) {
            Team teamWithBye = tempTeamList.get(0);
            // Add a "bye" matchup for the last remaining team
            tournament.add(new Matchup(teamWithBye, null));
        }
        // Start the first match
        getNextMatch();
    }



    private void getNextMatch() {
        if (tournament.isEmpty()) {
            return;  // No more matchups
        }

        // Get the current matchup (the first one in the tournament)
        Matchup matchup = tournament.get(0);

        // Ensure both teams are not null before updating the view
        String team1 = matchup.getTeam1();
        String team2 = matchup.getTeam2();

        if (team1 != null && team2 != null) {
            setCurrentMatchupView(team1 + " vs " + team2);
            ObservableList<String> list = FXCollections.observableArrayList(team1, team2);
            selectWinnerComboBox.setItems(list);  // Populate ComboBox with both teams
        } else if (team1 != null) {
            // Handle "bye" scenario if there's a single team left in the round
            setCurrentMatchupView(team1 + " automatically advances.");
            ObservableList<String> list = FXCollections.observableArrayList(team1);
            selectWinnerComboBox.setItems(list);  // Only one team, so just add it to the list
            selectWinnerComboBox.setDisable(true); // Disable ComboBox since no selection is needed
        } else {
            // Handle scenario where both teams are null or the matchup is incomplete
            setCurrentMatchupView("Invalid matchup.");
            selectWinnerComboBox.setItems(FXCollections.observableArrayList());
            selectWinnerComboBox.setDisable(true);  // Disable ComboBox since no valid match exists
        }
    }

}
