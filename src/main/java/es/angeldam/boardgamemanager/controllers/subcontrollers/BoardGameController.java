package es.angeldam.boardgamemanager.controllers.subcontrollers;

import es.angeldam.boardgamemanager.BoardGameManagerApplication;
import es.angeldam.boardgamemanager.dao.BoardGameDAO;
import es.angeldam.boardgamemanager.dataAccess.ConnectionBD;
import es.angeldam.boardgamemanager.model.BoardGame;
import es.angeldam.boardgamemanager.model.Designer;
import es.angeldam.boardgamemanager.model.Illustrator;
import es.angeldam.boardgamemanager.model.Publisher;
import es.angeldam.boardgamemanager.utils.Difficulty;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BoardGameController {
    @FXML public ToggleGroup locationToSearch;
    @FXML public RadioButton authorRadio;
    @FXML public RadioButton illustratorRadio;
    @FXML public RadioButton nameRadio;
    @FXML public RadioButton publisherRadio;
    @FXML public Button addBoardGameButton = new Button();
    @FXML public Button editBoardGameButton = new Button();
    @FXML public Button removeBoardGameButton = new Button();
    @FXML public Button searchButton = new Button();
    @FXML public TextField searchBoardGameField;

    @FXML public TableView<BoardGame> boardGameTable = new TableView<>();

    @FXML public TableColumn<BoardGame, Integer> bGCodeCol = new TableColumn<>();
    @FXML public TableColumn<BoardGame, String> bGNameCol = new TableColumn<>();
    @FXML public TableColumn<BoardGame, Integer> bGMinPlayersCol = new TableColumn<>();
    @FXML public TableColumn<BoardGame, Integer> bGMaxPlayersCol = new TableColumn<>();
    @FXML public TableColumn<BoardGame, Integer> bGAvgDurationCol = new TableColumn<>();
    @FXML public TableColumn<BoardGame, Integer> bGPubYearCol = new TableColumn<>();
    @FXML public TableColumn<BoardGame, Difficulty> bGDiffCol = new TableColumn<>();
    @FXML public TableColumn<BoardGame, Integer> bGRankCol = new TableColumn<>();
    @FXML public TableColumn<BoardGame, String> bGMechCol = new TableColumn<>();
    @FXML public TableColumn<BoardGame, Integer> bGAgeCol = new TableColumn<>();
    @FXML public TableColumn<BoardGame , ArrayList<Designer>> bGDesigners = new TableColumn<>();
    @FXML public TableColumn<BoardGame , ArrayList<Illustrator>> bGIllustrators =  new TableColumn<>();
    @FXML public TableColumn<BoardGame , ArrayList<Publisher>> bGPublishers = new TableColumn<>();

    @FXML
    public void initialize(){
        configListeners();
    }

    public void configureBoardGameTable(List<BoardGame> boardGames) {
        if( boardGames == null || boardGames.getFirst() == null ){
            boardGameTable.setPlaceholder(new Label("There isn't board games to show"));
        }else {
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
            bGDesigners.setCellValueFactory(new PropertyValueFactory<>("designers"));
            bGIllustrators.setCellValueFactory(new PropertyValueFactory<>("illustrators"));
            bGPublishers.setCellValueFactory(new PropertyValueFactory<>("publishers"));
            ObservableList<BoardGame> boardGameObservableList = FXCollections.observableArrayList(boardGames);
            boardGameTable.setItems(boardGameObservableList);

            editBoardGameButton.disableProperty().bind(boardGameTable.getSelectionModel().selectedItemProperty().isNull());
            removeBoardGameButton.disableProperty().bind(boardGameTable.getSelectionModel().selectedItemProperty().isNull());
        }
    }

    public List<BoardGame> loadBoardGames() {
        List<BoardGame> boardGames = null;
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

    private void openFormBoardGame(BoardGame selectedBoardGame) {
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

    @FXML private void configListeners(){
        addListener(authorRadio, searchBoardGameField, searchButton);
        addListener(illustratorRadio, searchBoardGameField, searchButton);
        addListener(publisherRadio, searchBoardGameField, searchButton);
        addListener(nameRadio, searchBoardGameField, searchButton);
    }


    private void addListener(RadioButton radioButton, TextField textField, Button button) {
        radioButton.pressedProperty().addListener(observable -> {
            textField.setDisable(false);
            button.setDisable(false);
        });
    }

    @FXML private void addBoardGame( ) {
        openFormBoardGame(null);
        loadBoardGames();
    }

    @FXML private void editBoardGame( ) {
        BoardGame boardGame = boardGameTable.getSelectionModel().getSelectedItem();
        if (boardGame == null){
            Utils.alert(Alert.AlertType.ERROR,"ERROR SELECTION", "There isn't a board game selected", "You must select a board game");
            return;
        }
        openFormBoardGame(boardGame);
        loadBoardGames();
        boardGameTable.getSelectionModel().select(boardGame);
    }

    @FXML private void removeBoardGame( ) {
        BoardGame boardGame = boardGameTable.getSelectionModel().getSelectedItem();
        if (boardGame == null){
            Utils.alert(Alert.AlertType.ERROR,"ERROR SELECTION", "There isn't a board game selected", "You must select a board game");
            return;
        }
        Optional<ButtonType> answer = Utils.alert(Alert.AlertType.CONFIRMATION, "Confirm Delete", "Confirm Delete on board game", "Are you sure you want to remove: " + boardGame.getName() + "?");
        if(answer.isPresent() && answer.get() == ButtonType.OK){
            try {
                BoardGameDAO.deleteGameById(boardGame.getCode());
                boardGameTable.getItems().remove(boardGame);
                boardGameTable.getSelectionModel().clearSelection();
            } catch (SQLException e) {
                Utils.alert(Alert.AlertType.ERROR,"DATABASE ERROR", "Error with database", "The board game couldn't be removed: "+e.getMessage());
            }
        }
    }

    public void searchBoardGames( ){
        //TODO: CHECK LIKE IN SQL
        configureBoardGameTable( loadPartialBoardGames(detectText()) );
    }

    public String detectText( ) {
        String searchText = "";
        if (nameRadio.isSelected()){
            searchText = "name";
        }else if (authorRadio.isSelected()){
            searchText = "author";
        }else if (illustratorRadio.isSelected()){
            searchText = "illustrator";
        }else if (publisherRadio.isSelected()){
            searchText = "publisher";
        }
        return searchText;
    }

    public List<BoardGame> loadPartialBoardGames(String location) {
        ArrayList<BoardGame> boardGames = null;
        if (ConnectionBD.getConnection() == null) {
            Utils.alert(Alert.AlertType.ERROR,"ERROR CONNECTING DATABASE", "There was an error loading database", "The database couldn't connect and retrieve board game data.");
        } else {
            try {
                boardGames = BoardGameDAO.findPartial(location, searchBoardGameField.getText() );
            } catch (SQLException e) {
                Utils.alert(Alert.AlertType.ERROR,"ERROR LOADING BOARDGAMES", "There was an error loading board games", e.getMessage());
            }
        }
        return boardGames;
    }
}