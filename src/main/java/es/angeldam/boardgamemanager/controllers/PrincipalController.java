package es.angeldam.boardgamemanager.controllers;

import es.angeldam.boardgamemanager.dao.BoardGameDAO;
import es.angeldam.boardgamemanager.dataAccess.ConnectionBD;
import es.angeldam.boardgamemanager.model.BoardGame;
import es.angeldam.boardgamemanager.model.User;
import es.angeldam.boardgamemanager.utils.Difficulty;
import es.angeldam.boardgamemanager.utils.UserType;
import es.angeldam.boardgamemanager.utils.Utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.ArrayList;

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
    public TableView<BoardGame> bGTable;
    @FXML
    ListView boardGamesName = new ListView<>();

    @FXML
    public void initialize() {
        if ( User.getInstance().getUserType() == UserType.USER ){
            tabPane.getTabs().removeAll(authorTab, illustratorTab, publisherTab);
        }
        loadBoardGames();
        configureBoardGameTable();
    }

    private void loadBoardGames() {
        if (ConnectionBD.getConnection() == null) {
            Utils.alertError("ERROR CONNECTING DATABASE", "There was an error loading database", "The database couldn't connect and retrieve board game data.");
        } else {
            ArrayList<BoardGame> boardGames = null;
            try {
                boardGames = BoardGameDAO.findAll();
            } catch (SQLException e) {
                Utils.alertError("ERROR LOADING BOARDGAMES", "There was an error loading board games", e.getMessage());
            }

        }
    }

    public void configureBoardGameTable() {
        boardGameTreeTable(cellData -> {
            String boardGameCode = cellData.getValue().getCode();

            BoardGame boardGame = BoardGameDAO.findById(boardGameCode);
            return new SimpleStringProperty(boardGame.getCode() + " " + boardGame.getName());
        });
        treeTableName.setCellValueFactory(
                new PropertyValueFactory<>("dni")
        );

        ObservableList<BoardGame> boardGamesList =
                FXCollections.observableArrayList(BoardGameDAO.findAll());

        boardGameTreeTable.setItems(boardGamesList);
    }

    private void configureList() {

    }

    public void editBoardGame(ActionEvent actionEvent) {
    }

    public void removeBoardGame(ActionEvent actionEvent) {
    }

    public void searchBoardGame(ActionEvent actionEvent) {
    }

    public void addBoardGame(ActionEvent actionEvent) {
    }
}