package es.angeldam.boardgamemanager.controllers;

import com.mysql.cj.util.Util;
import es.angeldam.boardgamemanager.BoardGameManagerApplication;
import es.angeldam.boardgamemanager.dao.BoardGameDAO;
import es.angeldam.boardgamemanager.dataAccess.ConnectionBD;
import es.angeldam.boardgamemanager.model.BoardGame;
import es.angeldam.boardgamemanager.model.User;
import es.angeldam.boardgamemanager.utils.Difficulty;
import es.angeldam.boardgamemanager.utils.UserType;
import es.angeldam.boardgamemanager.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PrincipalController {
    @FXML
    public TabPane tabPane;
    @FXML
    public Tab authorTab;
    @FXML
    public Tab illustratorTab;
    @FXML
    public Tab publisherTab;
    @FXML
    public Tab boardGameTab;
    @FXML
    public Tab gameTab;
    @FXML
    public Tab playerTab;
    @FXML
    public Button editBoardGameButton;
    @FXML
    public Button removeBoardGameButton;
    @FXML
    public Button addBoardGameButton;
    @FXML
    public TextField searchBoardGameField;
    @FXML
    public TableColumn<BoardGame, Integer> bGCodeCol;
    @FXML
    public TableColumn<BoardGame, String> bGNameCol;
    @FXML
    public TableColumn<BoardGame, Integer> bGMinPlayersCol;
    @FXML
    public TableColumn<BoardGame, Integer> bGMaxPlayersCol;
    @FXML
    public TableColumn<BoardGame, Integer> bGAvgDurationCol;
    @FXML
    public TableColumn<BoardGame, Integer> bGPubYearCol;
    @FXML
    public TableColumn<BoardGame, Difficulty> bGDiffCol;
    @FXML
    public TableColumn<BoardGame, Integer> bGRankCol;
    @FXML
    public TableColumn<BoardGame, String> bGMechCol;
    @FXML
    public TableColumn<BoardGame, Integer> bGAgeCol;
    @FXML
    public TableView<BoardGame> bGTable;

    @FXML
    public void initialize() {
        if ( User.getInstance().getUserType() == UserType.USER ){
            tabPane.getTabs().removeAll(authorTab, illustratorTab, publisherTab);
        }
        configureBoardGameTable(loadBoardGames());
    }

    private List<BoardGame> loadBoardGames() {
        ArrayList<BoardGame> boardGames = null;
        if (ConnectionBD.getConnection() == null) {
            Utils.alert(Alert.AlertType.ERROR,"ERROR CONNECTING DATABASE", "There was an error loading database", "The database couldn't connect and retrieve board game data.");
        } else {
            try {
                boardGames = BoardGameDAO.findAll();
            } catch (SQLException e) {
                Utils.alert(Alert.AlertType.ERROR,"ERROR LOADING BOARDGAMES", "There was an error loading board games", e.getMessage());
            }
        }
        return boardGames;
    }

    public void configureBoardGameTable(List<BoardGame> boardGames) {
        bGCodeCol.setCellValueFactory(new PropertyValueFactory<BoardGame, Integer>("code"));
        bGNameCol.setCellValueFactory(new PropertyValueFactory<BoardGame, String>("name"));
        bGMinPlayersCol.setCellValueFactory(new PropertyValueFactory<BoardGame, Integer>("minPlayers"));
        bGMaxPlayersCol.setCellValueFactory(new PropertyValueFactory<BoardGame, Integer>("maxPlayers"));
        bGAvgDurationCol.setCellValueFactory(new PropertyValueFactory<BoardGame, Integer>("averageDuration"));
        bGAgeCol.setCellValueFactory(new PropertyValueFactory<BoardGame, Integer>("recommendedAge"));
        bGPubYearCol.setCellValueFactory(new PropertyValueFactory<BoardGame, Integer>("publicationYear"));
        bGDiffCol.setCellValueFactory(new PropertyValueFactory<BoardGame, Difficulty>("difficulty"));
        bGRankCol.setCellValueFactory(new PropertyValueFactory<BoardGame, Integer>("ranking"));
        bGMechCol.setCellValueFactory(new PropertyValueFactory<BoardGame, String>("mechanics"));
        ObservableList<BoardGame> boardGameObservableList = FXCollections.observableArrayList(boardGames);
        bGTable.setItems(boardGameObservableList);

        bGTable.setPlaceholder(new Label("There isn't board games to show"));
        editBoardGameButton.disableProperty().bind(bGTable.getSelectionModel().selectedItemProperty().isNull());
        removeBoardGameButton.disableProperty().bind(bGTable.getSelectionModel().selectedItemProperty().isNull());
    }

    public void editBoardGame(ActionEvent actionEvent) {
        BoardGame boardGame = bGTable.getSelectionModel().getSelectedItem();
        if (boardGame == null){
            Utils.alert(Alert.AlertType.ERROR,"ERROR SELECTION", "There isn't a board game selected", "You must select a board game");
            return;
        }
        openForm(boardGame);
        loadBoardGames();
        bGTable.getSelectionModel().select(boardGame);
    }

    private void openForm(BoardGame selectedBoardGame) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(BoardGameManagerApplication.class.getResource("formBoardGame-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            FormBoardGameController controller = fxmlLoader.getController();
            controller.initialize(selectedBoardGame);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(selectedBoardGame == null ? "New board game" : "Update board game");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (Exception e) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR", "Error loading form", "Board game form couldn't be loaded: "+ e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public void removeBoardGame(ActionEvent actionEvent) {
        BoardGame boardGame = bGTable.getSelectionModel().getSelectedItem();
        if (boardGame == null){
            Utils.alert(Alert.AlertType.ERROR,"ERROR SELECTION", "There isn't a board game selected", "You must select a board game");
            return;
        }
        Optional<ButtonType> answer = Utils.alert(Alert.AlertType.CONFIRMATION, "Confirm Delete", "Confirm Delete on board game", "Are you sure you want to remove" + boardGame.getName() + "?");
        if(answer.isPresent() && answer.get() == ButtonType.OK){
            try {
                BoardGameDAO.deleteGameById(boardGame.getCode());
                bGTable.getItems().remove(boardGame);
                bGTable.getSelectionModel().clearSelection();
            } catch (SQLException e) {
                Utils.alert(Alert.AlertType.ERROR,"DATABASE ERROR", "Error with database", "The board game couldn't be removed: "+e.getMessage());
            }
        }
    }

    public void searchBoardGame(ActionEvent actionEvent) {
    }

    public void addBoardGame(ActionEvent actionEvent) {
        openForm(null);
        loadBoardGames();
    }
}