package es.angeldam.boardgamemanager.controllers;


import es.angeldam.boardgamemanager.model.User;
import es.angeldam.boardgamemanager.utils.UserType;

import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Principal controller of the app, it initializes when the principal window of the program is opened
 */
public class PrincipalController {
    @FXML public TabPane tabPane;
    @FXML public Tab illustratorTab;
    @FXML public Tab publisherTab;
    @FXML public Tab boardGameTab;
    @FXML public Tab gameTab;
    @FXML public Tab playerTab;
    @FXML public Tab designerTab;

    /**
     * Method that will initiate by default when the view is called, it hides the tabs to the normal user
     */
    @FXML
    public void initialize() {
        if ( User.getInstance().getUserType() == UserType.USER ){
            tabPane.getTabs().removeAll(designerTab, illustratorTab, publisherTab);
        }
    }
}