package es.angeldam.boardgamemanager.controllers.subcontrollers;

import es.angeldam.boardgamemanager.BoardGameManagerApplication;
import es.angeldam.boardgamemanager.dao.PlayerDAO;
import es.angeldam.boardgamemanager.dataAccess.ConnectionBD;
import es.angeldam.boardgamemanager.model.Player;
import es.angeldam.boardgamemanager.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PlayerController {
    @FXML
    public TableView<Player> playerTable;
    @FXML
    public Button addPlayerButton;
    @FXML
    public Button editPlayerButton;
    @FXML
    public Button removePlayerButton;
    @FXML
    public TextField searchPlayerField;
    @FXML
    public TableColumn<Player, String> playerNameCol;
    @FXML
    public TableColumn<Player, Integer> playerBirthYearCol;
    @FXML
    public TableColumn<Player, Integer> playerCodeCol;

    @FXML
    public void initialize() {
        configurePlayerTable(loadPlayers());
    }

    public List<Player> loadPlayers() {
        List<Player> players = null;
        if (ConnectionBD.getConnection() == null) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR CONNECTING DATABASE", "There was an error loading database", "The database couldn't connect and retrieve player data.");
        } else {
            try {
                players = PlayerDAO.findAll();
            } catch (SQLException e) {
                Utils.alert(Alert.AlertType.ERROR, "ERROR LOADING PLAYERS", "There was an error loading players", e.getMessage());
            }
        }
        return players;
    }

    public void configurePlayerTable(List<Player> players) {
        playerTable.setPlaceholder(new Label("There isn't players to show"));


        playerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        playerBirthYearCol.setCellValueFactory(new PropertyValueFactory<>("birthYear"));

        ObservableList<Player> designerObservableList = FXCollections.observableArrayList(players);
        playerTable.setItems(designerObservableList);

        editPlayerButton.disableProperty().bind(playerTable.getSelectionModel().selectedItemProperty().isNull());
        removePlayerButton.disableProperty().bind(playerTable.getSelectionModel().selectedItemProperty().isNull());
    }

    public void openFormPlayer(Player player) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(BoardGameManagerApplication.class.getResource("formPlayer-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            FormPlayerController controller = fxmlLoader.getController();
            controller.start(player);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(player == null ? "New player" : "Update player");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
            loadPlayers();
        } catch (Exception e) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR", "Error loading form", "Designer form couldn't be loaded: " + e.getMessage());
        }
    }

    @FXML
    public void addPlayer() {
        openFormPlayer(null);
        loadPlayers();
    }

    @FXML
    public void editPlayer() {
        Player player = playerTable.getSelectionModel().getSelectedItem();
        if (player == null) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR SELECTION", "There isn't a player selected", "You must select a player");
            return;
        }
        openFormPlayer(player);
        loadPlayers();
        playerTable.getSelectionModel().select(player);
    }

    @FXML
    public void removePlayer() {
        Player player = playerTable.getSelectionModel().getSelectedItem();
        if (player == null) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR SELECTION", "There isn't a player selected", "You must select a player");
            return;
        }
        Optional<ButtonType> answer = Utils.alert(Alert.AlertType.CONFIRMATION, "Confirm Delete", "Confirm Delete on player", "Are you sure you want to remove: " + player.getName() + "?");
        if (answer.isPresent() && answer.get() == ButtonType.OK) {
            try {
                int playerCode = playerTable.getSelectionModel().getSelectedItem().getCode();
                if ( PlayerDAO.deletePlayerById(playerCode) ){
                    playerTable.getItems().remove(player);
                    playerTable.getSelectionModel().clearSelection();
                }
            } catch (SQLException e) {
                Utils.alert(Alert.AlertType.ERROR, "DATABASE ERROR", "Error with database", "The player couldn't be removed: " + e.getMessage());
            }
        }
    }

    @FXML
    public void searchPlayer() {

    }
}
