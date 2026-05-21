package es.angeldam.boardgamemanager.controllers.subcontrollers;

import es.angeldam.boardgamemanager.BoardGameManagerApplication;
import es.angeldam.boardgamemanager.dao.DesignerDAO;
import es.angeldam.boardgamemanager.dataAccess.ConnectionBD;
import es.angeldam.boardgamemanager.model.BoardGame;
import es.angeldam.boardgamemanager.model.Designer;
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
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DesignerController {

    @FXML
    public Button addDesignerButton = new Button();
    @FXML
    public Button editDesignerButton = new Button();
    @FXML
    public Button removeDesignerButton = new Button();
    @FXML
    public TextField searchDesigner;

    @FXML
    public TableView<Designer> designerTable = new TableView<>();

    @FXML
    public TableColumn<Designer, String> dNameCol = new TableColumn<>();
    @FXML
    public TableColumn<Designer, String> dAliasCol = new TableColumn<>();
    @FXML
    public TableColumn<Designer, Timestamp> dBirthYearCol = new TableColumn<>();
    @FXML
    public TableColumn<Designer, String> dNationalityCol = new TableColumn<>();
    @FXML
    public TableColumn<Designer, String> dBGCol = new TableColumn<>();

    @FXML
    public void initialize() {
        configureDesignerTable(loadDesigners());
    }

    public List<Designer> loadDesigners() {
        List<Designer> designers = null;
        if (ConnectionBD.getConnection() == null) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR CONNECTING DATABASE", "There was an error loading database", "The database couldn't connect and retrieve designer data.");
        } else {
            try {
                designers = DesignerDAO.findAll();
            } catch (SQLException e) {
                Utils.alert(Alert.AlertType.ERROR, "ERROR LOADING BOARDGAMES", "There was an error loading board games", e.getMessage());
            }
        }
        return designers;
    }

    public void configureDesignerTable(List<Designer> designers) {
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

    public void openFormDesigner(Designer designer) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(BoardGameManagerApplication.class.getResource("formDesigner-view.fxml"));
            //FXMLLoader fxmlLoader = new FXMLLoader(BoardGameManagerApplication.class.getResource("formEntity-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            FormDesignerController controller = fxmlLoader.getController();
            //FormEntityController controller = fxmlLoader.getController();
            controller.initialize(designer);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(designer == null ? "New designer" : "Update designer");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
            loadDesigners();
        } catch (Exception e) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR", "Error loading form", "Designer form couldn't be loaded: " + e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    @FXML
    public void addDesigner() {
        openFormDesigner(null);
        loadDesigners();
    }

    @FXML
    public void editDesigner() {
        Designer designer = designerTable.getSelectionModel().getSelectedItem();
        if (designer == null) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR SELECTION", "There isn't a designer selected", "You must select a designer");
            return;
        }
        openFormDesigner(designer);
        loadDesigners();
        designerTable.getSelectionModel().select(designer);
        /*
        try {
            DesignerDAO.updateDesigner(designer, designer);
        } catch (SQLException e) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR UPDATING DESIGNER", "An error has occurred while updating designer", e.getMessage());
        }
         */
    }

    @FXML
    public void removeDesigner() {
        Designer designer = designerTable.getSelectionModel().getSelectedItem();
        if (designer == null) {
            Utils.alert(Alert.AlertType.ERROR,"ERROR SELECTION", "There isn't a designer selected", "You must select a designer");
            return;
        }
        Optional<ButtonType> answer = Utils.alert(Alert.AlertType.CONFIRMATION, "Confirm Delete", "Confirm Delete on designer", "Are you sure you want to remove: " + designer.getName() + "?");
        if(answer.isPresent() && answer.get() == ButtonType.OK) {
            try {
                DesignerDAO.deleteDesignerById(designerTable.getSelectionModel().getSelectedItem().getCode());
                designerTable.getItems().remove(designer);
                designerTable.getSelectionModel().clearSelection();
            } catch (SQLException e) {
                Utils.alert(Alert.AlertType.ERROR, "DATABASE ERROR", "Error with database", "The designer couldn't be removed: " + e.getMessage());
            }
        }
    }

    @FXML
    public void searchDesigner() {

    }
}