package es.angeldam.boardgamemanager.controllers.subcontrollers;

import es.angeldam.boardgamemanager.BoardGameManagerApplication;
import es.angeldam.boardgamemanager.dao.DesignerDAO;
import es.angeldam.boardgamemanager.dao.PublisherDAO;
import es.angeldam.boardgamemanager.dataAccess.ConnectionBD;
import es.angeldam.boardgamemanager.model.Designer;
import es.angeldam.boardgamemanager.model.Publisher;
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
import java.util.List;
import java.util.Optional;

public class PublisherController {
    @FXML public TableView publisherTable;
    @FXML public Button editPublisherButton;
    @FXML public Button addPublisherButton;
    @FXML public Button removePublisher;
    @FXML public TextField searchPublisher;
    @FXML public TableColumn pubNameCol;
    @FXML public TableColumn pubFoundationYearCol;
    @FXML public TableColumn pubHeadquartersCol;
    @FXML public TableColumn pubNumberBGCol;

    @FXML
    public void initialize() {
        configurePublisherTable(loadPublishers());
    }

    public List<Publisher> loadPublishers() {
        List<Publisher> publishers = null;
        if (ConnectionBD.getConnection() == null) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR CONNECTING DATABASE", "There was an error loading database", "The database couldn't connect and retrieve publisher data.");
        } else {
            try {
                publishers = PublisherDAO.findAll();
            } catch (SQLException e) {
                Utils.alert(Alert.AlertType.ERROR, "ERROR LOADING PUBLISHER", "There was an error loading publishers", e.getMessage());
            }
        }
        return publishers;
    }

    public void configurePublisherTable(List<Publisher> publishers) {
        publisherTable.setPlaceholder(new Label("There isn't publishers to show"));

        pubNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        pubFoundationYearCol.setCellValueFactory(new PropertyValueFactory<>("foundationYear"));
        pubHeadquartersCol.setCellValueFactory(new PropertyValueFactory<>("headquarters"));
        //pubNumberBGCol.setCellValueFactory(new PropertyValueFactory<>("TODO: ADD NUMBER"));

        ObservableList<Publisher> publisherObservableList = FXCollections.observableArrayList(publishers);
        publisherTable.setItems(publisherObservableList);

        editPublisherButton.disableProperty().bind(publisherTable.getSelectionModel().selectedItemProperty().isNull());
        removePublisher.disableProperty().bind(publisherTable.getSelectionModel().selectedItemProperty().isNull());
    }

    public void openFormDesigner(Designer designer) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(BoardGameManagerApplication.class.getResource("formDesigner-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            FormDesignerController controller = fxmlLoader.getController();
            controller.start(designer);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(designer == null ? "New designer" : "Update designer");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
            loadPublishers();
        } catch (Exception e) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR", "Error loading form", "Designer form couldn't be loaded: " + e.getMessage());
        }
    }

    @FXML
    public void addDesigner() {
        openFormDesigner(null);
        loadPublishers();
    }

    @FXML
    public void editDesigner() {
        Designer designer = designerTable.getSelectionModel().getSelectedItem();
        if (designer == null) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR SELECTION", "There isn't a designer selected", "You must select a designer");
            return;
        }
        openFormDesigner(designer);
        loadPublishers();
        designerTable.getSelectionModel().select(designer);
    }

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