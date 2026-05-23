package es.angeldam.boardgamemanager.controllers.subcontrollers;

import es.angeldam.boardgamemanager.BoardGameManagerApplication;
import es.angeldam.boardgamemanager.dao.BoardGameDAO;
import es.angeldam.boardgamemanager.dataAccess.ConnectionBD;
import es.angeldam.boardgamemanager.model.BoardGame;
import es.angeldam.boardgamemanager.utils.Difficulty;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BoardGameController {
    @FXML public ToggleGroup locationToSearch;
    @FXML public RadioButton authorRadio;
    @FXML public RadioButton illustratorRadio;
    @FXML public RadioButton nameRadio;
    @FXML public RadioButton publisherRadio;
    @FXML public Button addBoardGameButton ;
    @FXML public Button editBoardGameButton ;
    @FXML public Button removeBoardGameButton ;
    @FXML public Button searchButton;
    @FXML public TextField searchBoardGameField;

    @FXML public TableView<BoardGame> boardGameTable;

    @FXML public TableColumn<BoardGame, Integer> bGCodeCol;
    @FXML public TableColumn<BoardGame, String> bGNameCol ;
    @FXML public TableColumn<BoardGame, Integer> bGMinPlayersCol ;
    @FXML public TableColumn<BoardGame, Integer> bGMaxPlayersCol ;
    @FXML public TableColumn<BoardGame, Integer> bGAvgDurationCol ;
    @FXML public TableColumn<BoardGame, Integer> bGPubYearCol ;
    @FXML public TableColumn<BoardGame, Difficulty> bGDiffCol ;
    @FXML public TableColumn<BoardGame, Integer> bGRankCol ;
    @FXML public TableColumn<BoardGame, String> bGMechCol ;
    @FXML public TableColumn<BoardGame, Integer> bGAgeCol ;
    @FXML public TableColumn<BoardGame , String> bGDesigners ;
    @FXML public TableColumn<BoardGame , String> bGIllustrators ;
    @FXML public TableColumn<BoardGame , String> bGPublishers ;

    @FXML
    public void initialize() {
        configureBoardGameTable(loadBoardGames());
        configListeners();
    }

    public void configureBoardGameTable(List<BoardGame> boardGames) {
        if( boardGames == null || boardGames.isEmpty() || boardGames.get(0) == null ){
            boardGameTable.setPlaceholder(new Label("There isn't board games to show"));
            Utils.alert(Alert.AlertType.ERROR, "ERROR BOARD GAMES", "The board game database couldn't get any results", "There isn't board games to show");
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
            bGDesigners.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().listDesigners()));
            bGIllustrators.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().listIllustrator()));
            bGPublishers.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().listPublisher()));

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
                boardGames = BoardGameDAO.findAllEager();
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
                int boardGameCode = boardGame.getCode();
                if (BoardGameDAO.deleteGameById(boardGameCode)){
                    boardGameTable.getItems().remove(boardGame);
                    boardGameTable.getSelectionModel().clearSelection();
                }
            } catch (SQLException e) {
                Utils.alert(Alert.AlertType.ERROR,"DATABASE ERROR", "Error with database", "The board game couldn't be removed: "+e.getMessage());
            }
        }
    }

    public void searchBoardGames( ){
        configureBoardGameTable( loadPartialBoardGames(detectText()) );
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
}