package es.angeldam.boardgamemanager.controllers.subcontrollers;

import es.angeldam.boardgamemanager.BoardGameManagerApplication;
import es.angeldam.boardgamemanager.dao.MatchDAO;
import es.angeldam.boardgamemanager.dataAccess.ConnectionBD;
import es.angeldam.boardgamemanager.model.Match;
import es.angeldam.boardgamemanager.model.Participation;
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
import java.util.List;
import java.util.Optional;

public class ParticipateController {
    @FXML
    public Button addMatchButton;
    @FXML
    public Button removeMatchButton;
    @FXML
    public Button editMatchButton;
    @FXML
    public TextField searchMatchField;
    @FXML
    public TableView<Match> matchTable;
    @FXML
    public TableColumn<Match, Integer> matchCodeCol;
    @FXML
    public TableColumn<Match, String> matchBoardGameCol;
    @FXML
    public TableColumn<Match, String> matchPlaceCol;
    @FXML
    public TableColumn<Match, String> matchDateCol;
    @FXML
    public TableColumn<Participation, String> playerCol;
    @FXML
    public TableColumn<Participation, Integer> scoreCol;


    @FXML
    public void initialize() {
        configureMatchTable(loadMatches());
    }

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

    public void configureMatchTable(List<Match> matches){
        matchTable.setPlaceholder(new Label("There isn't matches to show"));

        //matchBoardGameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBoardGame().getName()));
        matchPlaceCol.setCellValueFactory(new PropertyValueFactory<>("place"));
        matchDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        ObservableList<Match> matchObservableList = FXCollections.observableList(matches);
        matchTable.setItems(matchObservableList);

        editMatchButton.disableProperty().bind(matchTable.getSelectionModel().selectedItemProperty().isNull());
        removeMatchButton.disableProperty().bind(matchTable.getSelectionModel().selectedItemProperty().isNull());
    }

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

    public void addMatch( ) {
        openFormMatch(null);
        loadMatches();
    }

    public void editMatch( ) {
        Match match = matchTable.getSelectionModel().getSelectedItem();
        if (match == null) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR SELECTION", "There isn't a match selected", "You must select a match");
            return;
        }
        openFormMatch(match);
        matchTable.getSelectionModel().select(match);
    }

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
