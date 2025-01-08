package controller;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Player;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class PlayerNameEntryController {

    private final int count = MainController.count;

    @FXML
    public TextField playerNameEntry;
    @FXML
    public Button nextButton;
    @FXML
    public Button doneButton;
    @FXML
    public AnchorPane playerNameEntryScreen;
    @FXML
    private TableColumn<Player, String> playerColumn;
    @FXML
    public TableView<Player> playerTable;
    private final ObservableList<Player> players = FXCollections.observableArrayList();

    private int counter = 0;

    @FXML
    public void initialize() {
        // Set the column's cell value factory
        playerColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        // Bind the TableView to the ObservableList
        playerTable.setItems(players);
    }
    @FXML
    public void onActionAddPlayer(ActionEvent actionEvent) {
        String playerName = playerNameEntry.getText();
        if (counter == count) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Maximum players entered. Continue or restart program.");
            Optional<ButtonType> confirmation = alert.showAndWait();
            return;
        }
        // Check if the name is empty
        if (playerName == null || playerName.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Player name cannot be empty!");
            alert.showAndWait();
            return;
        }
        counter++;

        Player newPlayer = new Player(playerName);
        players.add(newPlayer);
        playerTable.refresh();
        playerNameEntry.clear();
    }

    @FXML
    public void onActionContinue(ActionEvent actionEvent) throws IOException {

        if (counter != count) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter more players");
            alert.showAndWait();
            return;
        }

        TeamListController.setPlayerNames(players);
        Parent teamScreen = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/team-list.fxml")));
        Scene scene = new Scene(teamScreen);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
