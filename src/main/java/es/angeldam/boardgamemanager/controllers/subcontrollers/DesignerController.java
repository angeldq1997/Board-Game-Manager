package es.angeldam.boardgamemanager.controllers.subcontrollers;

import es.angeldam.boardgamemanager.dao.DesignerDAO;
import es.angeldam.boardgamemanager.dataAccess.ConnectionBD;
import es.angeldam.boardgamemanager.model.Designer;
import es.angeldam.boardgamemanager.utils.Utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class DesignerController {

    @FXML public Button addDesignerButton = new Button();
    @FXML public Button editDesignerButton = new Button();
    @FXML public Button removeDesignerButton = new Button();
    @FXML public TextField searchDesigner;

    @FXML public TableView<Designer> designerTable = new TableView<>();

    @FXML public TableColumn<Designer, String> dNameCol = new TableColumn<>();
    @FXML public TableColumn<Designer, String> dAliasCol = new TableColumn<>();
    @FXML public TableColumn<Designer, Timestamp> dBirthYearCol = new TableColumn<>();
    @FXML public TableColumn<Designer, String> dNationalityCol = new TableColumn<>();
    @FXML public TableColumn<Designer, String> dBGCol = new TableColumn<>();

    @FXML
    public void initialize(){
        configureDesignerTable(loadDesigners());
    }

    public List<Designer> loadDesigners(){
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

    public void configureDesignerTable(List<Designer> designers){
        designerTable.setPlaceholder(new Label("There isn't designers to show"));
        
        dNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        dAliasCol.setCellValueFactory(new PropertyValueFactory<>("alias"));
        dBirthYearCol.setCellValueFactory(new PropertyValueFactory<>("birthYear"));
        dNationalityCol.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        dBGCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().listBoardGames()));

        ObservableList<Designer> designerObservableList = FXCollections.observableArrayList(designers);
        designerTable.setItems(designerObservableList);

        editDesignerButton.disableProperty().bind(designerTable.getSelectionModel().selectedItemProperty().isNull());
        removeDesignerButton.disableProperty().bind(designerTable.getSelectionModel().selectedItemProperty().isNull());
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