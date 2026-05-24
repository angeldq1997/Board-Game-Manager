package es.angeldam.boardgamemanager.controllers.subcontrollers;

import es.angeldam.boardgamemanager.BoardGameManagerApplication;
import es.angeldam.boardgamemanager.dao.DesignerDAO;
import es.angeldam.boardgamemanager.dataAccess.ConnectionBD;
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
import java.util.List;
import java.util.Optional;

/**
 * Controller that manages the designer view and displays the form when needed, it allows the CRUD of the class Designer
 */
public class DesignerController {

    @FXML
    public Button addDesignerButton = new Button();
    @FXML
    public Button editDesignerButton = new Button();
    @FXML
    public Button removeDesignerButton = new Button();
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
    public TextField txtSearchDesigner;

    /**
     * Method that executes by default when the class is called
     */
    @FXML
    public void initialize() {
        configureDesignerTable(loadDesigners());
    }

    /**
     * Method that configures the table Designer and all its columns
     * @param designers List of designers that will be displayed at the table
     */
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

    /**
     * Method that load the designers to Java from the database
     * @return the list of designers that are stored in the database
     */
    public List<Designer> loadDesigners() {
        List<Designer> designers = null;
        if (ConnectionBD.getConnection() == null) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR CONNECTING DATABASE", "There was an error loading database", "The database couldn't connect and retrieve designer data.");
        } else {
            try {
                designers = DesignerDAO.findAll();
            } catch (SQLException e) {
                Utils.alert(Alert.AlertType.ERROR, "ERROR LOADING DESIGNERS", "There was an error loading designers", e.getMessage());
            }
        }
        return designers;
    }

    /**
     * Method that load a window for the form when the user wants to create a new designer or update one
     * @param designer the designer that want to be updated or NULL if we want to make a new one
     */
    public void openFormDesigner(Designer designer) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(BoardGameManagerApplication.class.getResource("formDesigner-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            FormDesignerController controller = fxmlLoader.getController();
            controller.initialize(designer);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(designer == null ? "New designer" : "Update designer");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
            configureDesignerTable(loadDesigners());
        } catch (Exception e) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR", "Error loading form", "Designer form couldn't be loaded: " + e.getMessage());
        }
    }

    /**
     * Method that adds a designer to the database
     */
    @FXML
    public void addDesigner() {
        openFormDesigner(null);
        configureDesignerTable(loadDesigners());
    }

    /**
     * Method that updates the designer data to the database, selected from the table view
     */
    @FXML
    public void editDesigner() {
        Designer designer = designerTable.getSelectionModel().getSelectedItem();
        if (designer == null) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR SELECTION", "There isn't a designer selected", "You must select a designer");
            return;
        }
        openFormDesigner(designer);
        configureDesignerTable(loadDesigners());
        designerTable.getSelectionModel().select(designer);
    }

    /**
     * Method that erase the designer data from the database; the user selects a concrete designer and then press the delete button
     */
    @FXML
    public void removeDesigner() {
        Designer designer = designerTable.getSelectionModel().getSelectedItem();
        if (designer == null) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR SELECTION", "There isn't a designer selected", "You must select a designer");
            return;
        }
        Optional<ButtonType> answer = Utils.alert(Alert.AlertType.CONFIRMATION, "Confirm Delete", "Confirm Delete on designer", "Are you sure you want to remove: " + designer.getName() + "?");
        if (answer.isPresent() && answer.get() == ButtonType.OK) {
            try {
                int designerCode = designerTable.getSelectionModel().getSelectedItem().getCode();
                if ( DesignerDAO.deleteDesignerById(designerCode) ){
                    designerTable.getItems().remove(designer);
                    designerTable.getSelectionModel().clearSelection();
                }
            } catch (SQLException e) {
                Utils.alert(Alert.AlertType.ERROR, "DATABASE ERROR", "Error with database", "The designer couldn't be removed: " + e.getMessage());
            }
        }
    }

    /**
     * Method for searching a designer with a specific name
     */
    @FXML
    public void searchDesigner() {
        try {
            List<Designer> designers = DesignerDAO.findByPartialName(txtSearchDesigner.getText());
            if ( !designers.isEmpty() ){
                ObservableList<Designer> designerObservableList = FXCollections.observableArrayList(designers);
                designerTable.setItems(designerObservableList);
            }else{
                Utils.alert(Alert.AlertType.ERROR, "ERROR COULDN'T FIND DESIGNER", "Couldn't find designer specified with name: " + txtSearchDesigner.getText(), "Please try again with another text");
            }
        } catch (Exception e) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR COULDN'T FIND DESIGNER", "Designer with name: " + txtSearchDesigner.getText() + " produced an exception", e.getMessage());
        }
    }
}