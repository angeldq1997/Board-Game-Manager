package es.angeldam.boardgamemanager.controllers;

import es.angeldam.boardgamemanager.controllers.subcontrollers.BoardGameController;
import es.angeldam.boardgamemanager.controllers.subcontrollers.DesignerController;
import es.angeldam.boardgamemanager.controllers.subcontrollers.IllustratorController;
import es.angeldam.boardgamemanager.controllers.subcontrollers.PublisherController;
import es.angeldam.boardgamemanager.model.Publisher;
import es.angeldam.boardgamemanager.model.User;
import es.angeldam.boardgamemanager.utils.UserType;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

    @FXML public BoardGameController boardGameController;
    @FXML public DesignerController designerController;
    @FXML public IllustratorController illustratorController;
    @FXML public PublisherController publisherController;

    @FXML
    public void initialize() {
        if ( User.getInstance().getUserType() == UserType.USER ){
            tabPane.getTabs().removeAll(designerTab, illustratorTab, publisherTab);
        }
    }
}