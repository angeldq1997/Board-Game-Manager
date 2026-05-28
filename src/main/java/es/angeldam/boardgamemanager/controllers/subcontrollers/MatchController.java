package es.angeldam.boardgamemanager.controllers.subcontrollers;

import es.angeldam.boardgamemanager.BoardGameManagerApplication;
import es.angeldam.boardgamemanager.dao.MatchDAO;
import es.angeldam.boardgamemanager.dataAccess.ConnectionBD;
import es.angeldam.boardgamemanager.model.Match;
import es.angeldam.boardgamemanager.utils.Utils;
import javafx.beans.property.SimpleStringProperty;
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
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * Controller that manages the match view and displays the form when needed, it allows the CRUD of the class Match
 */
public class MatchController {
    @FXML
    public TableView<Match> matchTable;
    @FXML
    public TableColumn<Match, Integer> matchCodeCol;
    @FXML
    public TableColumn<Match, String> matchBoardGameCol;
    @FXML
    public TableColumn<Match, String> matchPlaceCol;
    @FXML
    public TableColumn<Match, Timestamp> matchDateCol;
    @FXML
    public Button addMatchButton;
    @FXML
    public Button editMatchButton;
    @FXML
    public Button removeMatchButton;
    @FXML
    public TextField searchMatchField;

    /**
     * Method that executes by default when the class is called
     */
    @FXML
    public void initialize() {
        configureMatchTable(loadMatches());
    }

    /**
     * Method that configures the table Match and all its columns
     * @param matches List of matches that will be displayed at the table
     */
    public void configureMatchTable(List<Match> matches){
        matchTable.setPlaceholder(new Label("There isn't matches to show"));

        matchCodeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        //matchBoardGameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBoardGame().getName()));

        matchPlaceCol.setCellValueFactory(new PropertyValueFactory<>("place"));
        matchDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        ObservableList<Match> matchObservableList = FXCollections.observableList(matches);
        matchTable.setItems(matchObservableList);

        editMatchButton.disableProperty().bind(matchTable.getSelectionModel().selectedItemProperty().isNull());
        removeMatchButton.disableProperty().bind(matchTable.getSelectionModel().selectedItemProperty().isNull());
    }

    /**
     * Method that load the matches to Java from the database
     * @return the list of matches that are stored in the database
     */
    public List<Match> loadMatches() {
        List<Match> matches = null;
        if (ConnectionBD.getConnection() == null) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR CONNECTING DATABASE", "There was an error loading database", "The database couldn't connect and retrieve any match data.");
        } else {
            try {
                matches = MatchDAO.findAll();
            } catch (SQLException e) {
                Utils.alert(Alert.AlertType.ERROR, "ERROR LOADING MATCHES", "There was an error loading matches", e.getMessage());
            }
        }
        return matches;
    }

    /**
     * Method that load a window for the form when the user wants to create a new match or update one
     * @param match the match that want to be updated or NULL if we want to make a new one
     */
    public void openFormMatch(Match match) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(BoardGameManagerApplication.class.getResource("formMatch-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            FormMatchController controller = fxmlLoader.getController();
            controller.initialize(match);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(match == null ? "New match" : "Update match");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
            loadMatches();
        } catch (Exception e) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR", "Error loading form", "Match form couldn't be loaded: " + e.getMessage());
        }
    }

    /**
     * Method that adds a match to the database
     */
    public void addMatch( ) {
        openFormMatch(null);
        configureMatchTable(loadMatches());
    }

    /**
     * Method that updates the match data to the database, selected from the table view
     */
    public void editMatch( ) {
        Match match = matchTable.getSelectionModel().getSelectedItem();
        if (match == null) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR SELECTION", "There isn't a match selected", "You must select a match");
            return;
        }
        openFormMatch(match);
        configureMatchTable(loadMatches());
        matchTable.getSelectionModel().select(match);
    }

    /**
     * Method that erase the match data from the database; the user selects a concrete match and then press the delete button
     */
    public void removeMatch( ) {
        Match match = matchTable.getSelectionModel().getSelectedItem();
        if (match == null) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR SELECTION", "There isn't a match selected", "You must select a match");
            return;
        }
        Optional<ButtonType> answer = Utils.alert(Alert.AlertType.CONFIRMATION, "Confirm Delete", "Confirm Delete on match", "Are you sure you want to remove the match \nwith board game: " + match.getBoardGame() + " at " + match.getPlace() + "?");
        if (answer.isPresent() && answer.get() == ButtonType.OK) {
            try {
                int matchCode = matchTable.getSelectionModel().getSelectedItem().getCode();
                if ( MatchDAO.deleteMatchById(matchCode) ){
                    matchTable.getItems().remove(match);
                    matchTable.getSelectionModel().clearSelection();
                }
            } catch (SQLException e) {
                Utils.alert(Alert.AlertType.ERROR, "DATABASE ERROR", "Error with database", "The match couldn't be erased: " + e.getMessage());
            }
        }
    }
}