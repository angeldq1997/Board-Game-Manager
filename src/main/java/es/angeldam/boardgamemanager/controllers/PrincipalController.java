package es.angeldam.boardgamemanager.controllers;

import es.angeldam.boardgamemanager.model.User;
import es.angeldam.boardgamemanager.utils.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PrincipalController {
    //TAB PANE
    @FXML public TabPane tabPane;
    @FXML public Tab illustratorTab;
    @FXML public Tab publisherTab;
    @FXML public Tab boardGameTab;
    @FXML public Tab gameTab;
    @FXML public Tab playerTab;
    @FXML public Tab designerTab;

    //PUBLISHER
    public TableView publisherTable;
    public Button editPublisherButton;
    public Button addPublisherButton;
    public Button removePublisher;
    public TextField searchPublisher;
    public TableColumn pubFoundationYearCol;
    public TableColumn pubHeadquartersCol;
    public TableColumn pubNumberBGCol;

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

    public BoardGameController boardGameController = new BoardGameController();
    public DesignerController designerController = new DesignerController();

    @FXML
    public void initialize() {
        if ( User.getInstance().getUserType() == UserType.USER ){
            tabPane.getTabs().removeAll(designerTab, illustratorTab, publisherTab);
        }
        boardGameController.configureBoardGameTable(boardGameController.loadBoardGames());
        designerController.configureDesignerTable(designerController.loadDesigners());
    }

    public void searchBoardGame(ActionEvent actionEvent) {

    }

    public void addPublisher(ActionEvent actionEvent) {

    }

    public void editPublisher(ActionEvent actionEvent) {

    }

    public void removePublisher(ActionEvent actionEvent) {
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