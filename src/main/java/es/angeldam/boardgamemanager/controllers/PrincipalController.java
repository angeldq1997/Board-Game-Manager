package es.angeldam.boardgamemanager.controllers;

import es.angeldam.boardgamemanager.model.User;
import es.angeldam.boardgamemanager.utils.UserType;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

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
    public void initialize() {
        if ( User.getInstance().getUserType() == UserType.USER ){
            tabPane.getTabs().removeAll(authorTab, illustratorTab, publisherTab);
        }
    }
}