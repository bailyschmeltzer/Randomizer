package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Player;
import model.Team;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

/** @noinspection ALL*/
public class TeamListController {
    @FXML
    public TextField teamNameEntry;
    @FXML
    public Button startTournamentButton;
    @FXML
    public Button editTeamNameButton;
    @FXML
    public TableColumn<Team, String> teamName;
    @FXML
    public TableColumn<Team, String> teamPlayers;
    @FXML
    public TableView<Team> teamsTable;
    @FXML
    private static final ObservableList<Team> teams = FXCollections.observableArrayList();

    private ObservableList<String> playerNames = FXCollections.observableArrayList();
    private static Random rand;


    @FXML
    public void initialize() {
        // Set the column's cell value factory
        teamPlayers.setCellValueFactory(cellData -> cellData.getValue().playerNamesProperty());
        teamName.setCellValueFactory(cellData -> cellData.getValue().teamNamesProperty());

        // Bind the TableView to the ObservableList
        teamsTable.setItems(teams);

    }

    public static void setPlayerNames(ObservableList<Player> players) {
        // Extract the player names and add them to the new ObservableList
        ArrayList<Player> playerArrayList = new ArrayList<>(players);
        rand = new Random();

        while (playerArrayList.size() > 1) {
            int player1Index = rand.nextInt(playerArrayList.size());
            Player player1 = playerArrayList.get(player1Index);
            playerArrayList.remove(player1Index);
            Player player2 = null;
            if (!playerArrayList.isEmpty()) {
                int player2Index = rand.nextInt(playerArrayList.size());
                player2 = playerArrayList.get(player2Index);
                playerArrayList.remove(player2Index);
            }
            assert player2 != null;
            Team team = new Team(player1, player2);
            teams.add(team);
        }
    }

    @FXML
    public void onActionUpdate(ActionEvent actionEvent) {
        // Get the selected team from the table
        Team selectedTeam = teamsTable.getSelectionModel().getSelectedItem();

        // Ensure that a team is selected and the text field is not empty
        if (selectedTeam != null && teamNameEntry != null && !teamNameEntry.getText().isEmpty()) {
            // Set the new team name
            selectedTeam.setTeamName(teamNameEntry.getText());

            // Refresh the table to show the updated data
            teamsTable.refresh();

            // Optionally, clear the TextField after update
            teamNameEntry.clear();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No Team is selected.");
            Optional<ButtonType> confirmation = alert.showAndWait();
            return;
        }
    }

    @FXML
    public void onActionStartTournament(ActionEvent actionEvent) throws IOException {
        TournamentViewController.teamList = teams;
        // Load the FXML file and get the controller instance
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/tournament-view.fxml"));
        Parent tournamentScreen = loader.load();  // Load the FXML

        // Get the controller instance from the loader
        TournamentViewController controller = loader.getController();


        controller.startTournament();

        // Set the scene with the loaded FXML
        Scene scene = new Scene(tournamentScreen);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
