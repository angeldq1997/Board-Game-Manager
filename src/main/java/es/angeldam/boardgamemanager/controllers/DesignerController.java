package es.angeldam.boardgamemanager.controllers;

import es.angeldam.boardgamemanager.dao.DesignerDAO;
import es.angeldam.boardgamemanager.dataAccess.ConnectionBD;
import es.angeldam.boardgamemanager.model.Designer;
import es.angeldam.boardgamemanager.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.util.List;

public class DesignerController {

    public TableView designerTable;
    public Button addDesignerButton;
    public Button editDesignerButton;
    public Button removeDesignerButton;
    public TextField searchDesigner;
    public TableColumn dNameCol;
    public TableColumn dAliasCol;
    public TableColumn dBirthDateCol;
    public TableColumn dNationalityCol;
    public TableColumn dBGCol;

    private List<Designer> loadDesigners(){
        List<Designer> designers = null;
        if (ConnectionBD.getConnection() == null) {
            Utils.alert(Alert.AlertType.ERROR,"ERROR CONNECTING DATABASE", "There was an error loading database", "The database couldn't connect and retrieve designer data.");
        } else {
            try {
                designers = DesignerDAO.findAll();
            } catch (SQLException e) {
                Utils.alert(Alert.AlertType.ERROR,"ERROR LOADING BOARDGAMES", "There was an error loading board games", e.getMessage());
            }
        }
        return designers;
    }

    private void configureDesignerTable(List<Designer> designers){
        dNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        dBirthDateCol.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        dAliasCol.setCellValueFactory(new PropertyValueFactory<>("alias"));
        dNationalityCol.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        ObservableList<Designer> designerObservableList = FXCollections.observableArrayList(designers);

    }

    public void editDesigner(ActionEvent actionEvent) {

    }

    public void addDesigner(ActionEvent actionEvent) {

    }

    public void removeDesigner(ActionEvent actionEvent) {
    }

    public void searchDesigner(ActionEvent actionEvent) {

    }
}