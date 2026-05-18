package es.angeldam.boardgamemanager.controllers;

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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PrincipalController {
    //TAB PANE
    @FXML
    public TabPane tabPane;
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

    //GAME
    @FXML
    public TableView<BoardGame> bGTable;
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

    //ILLUSTRATOR
    public TableView illustratorTable;
    public TextField searchIllustratorField;
    public Button addIllustratorButton;
    public Button editIllustratorButton;
    public Button removeIllustratorButton;
    public TableColumn iNameCol;
    public TableColumn iBirthDateCol;
    public TableColumn iNationalityCol;
    public TableColumn iBGcol;

    //PUBLISHER
    public TableView publisherTable;
    public Button editPublisherButton;
    public Button addPublisherButton;
    public Button removePublisher;
    public TextField searchPublisher;

    //GAME
    public Button editGameButton;
    public Button removeGameButton;
    public Button addGameButton;
    public TextField searchGameField;
    public TableColumn gCodeCol;
    public TableColumn gBoardGameCol;
    public TableColumn gPlaceCol;
    public TableColumn gGameDateCol;
    public TableColumn gPlayersCol;

    //PLAYER
    public TableView playerTable;
    public Button addPlayerButton;
    public Button editPlayerButton;
    public Button removePlayerButton;
    public TextField searchPlayerField;
    public TableColumn plaNameCol;
    public TableColumn plaBirthDateCol;
    public TableColumn plaGamesCol;
    public TableColumn pubNameCol;
    public TableColumn pubFoundationYearCol;
    public TableColumn pubHeadquartersCol;
    public TableColumn pubNumberBGCol;

    @FXML
    public void initialize() {
        if ( User.getInstance().getUserType() == UserType.USER ){
            tabPane.getTabs().removeAll(designerTab, illustratorTab, publisherTab);
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

    private void configureBoardGameTable(List<BoardGame> boardGames) {
        bGCodeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        bGNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        bGMinPlayersCol.setCellValueFactory(new PropertyValueFactory<>("minPlayers"));
        bGMaxPlayersCol.setCellValueFactory(new PropertyValueFactory<>("maxPlayers"));
        bGAvgDurationCol.setCellValueFactory(new PropertyValueFactory<>("averageDuration"));
        bGAgeCol.setCellValueFactory(new PropertyValueFactory<>("recommendedAge"));
        bGPubYearCol.setCellValueFactory(new PropertyValueFactory<>("publicationYear"));
        bGDiffCol.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        bGRankCol.setCellValueFactory(new PropertyValueFactory<>("ranking"));
        bGMechCol.setCellValueFactory(new PropertyValueFactory<>("mechanics"));
        ObservableList<BoardGame> boardGameObservableList = FXCollections.observableArrayList(boardGames);
        bGTable.setItems(boardGameObservableList);

        bGTable.setPlaceholder(new Label("There isn't board games to show"));
        editBoardGameButton.disableProperty().bind(bGTable.getSelectionModel().selectedItemProperty().isNull());
        removeBoardGameButton.disableProperty().bind(bGTable.getSelectionModel().selectedItemProperty().isNull());
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
        }
    }

    public void searchBoardGame(ActionEvent actionEvent) {

    }

    public void addBoardGame(ActionEvent actionEvent) {
        openForm(null);
        openForm(null);
        loadBoardGames();
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

    public void removeBoardGame(ActionEvent actionEvent) {
        BoardGame boardGame = bGTable.getSelectionModel().getSelectedItem();
        if (boardGame == null){
            Utils.alert(Alert.AlertType.ERROR,"ERROR SELECTION", "There isn't a board game selected", "You must select a board game");
            return;
        }
        Optional<ButtonType> answer = Utils.alert(Alert.AlertType.CONFIRMATION, "Confirm Delete", "Confirm Delete on board game", "Are you sure you want to remove: " + boardGame.getName() + "?");
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

    public void addPublisher(ActionEvent actionEvent) {

    }

    public void editPublisher(ActionEvent actionEvent) {

    }

    public void removePublisher(ActionEvent actionEvent) {
    }

    public void searchIllustrator(ActionEvent actionEvent) {

    }

    public void removeIllustrator(ActionEvent actionEvent) {

    }

    public void editIllustrator(ActionEvent actionEvent) {

    }

    public void editGame(ActionEvent actionEvent) {

    }

    public void removeGame(ActionEvent actionEvent) {

    }

    public void addGame(ActionEvent actionEvent) {

    }

    public void editPlayer(ActionEvent actionEvent) {

    }

    public void removePlayer(ActionEvent actionEvent) {

    }

    public void addPlayer(ActionEvent actionEvent) {

    }

    public void searchDesigner(ActionEvent actionEvent) {
    }
}