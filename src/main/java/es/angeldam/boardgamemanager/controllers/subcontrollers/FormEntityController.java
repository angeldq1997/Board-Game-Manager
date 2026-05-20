package es.angeldam.boardgamemanager.controllers.subcontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FormEntityController {

    @FXML public Button btnSave;
    @FXML public ComboBox cmbBoardGame;
    @FXML public TextField txtHeadquarters;
    @FXML public TextField txtNationality;
    @FXML public Label formTitleLabel;
    @FXML public TextField txtName;
    @FXML public TextField txtAlias;
    @FXML public TextField txtYear;
    @FXML public TextField txtPublicationYear;

    public void closeWindow(ActionEvent actionEvent) {
    }

    public void storeEntity(ActionEvent actionEvent) {
    }

    public void addBoardGameToEntity(ActionEvent actionEvent) {
    }
}