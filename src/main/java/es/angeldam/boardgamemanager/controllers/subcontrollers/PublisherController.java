package es.angeldam.boardgamemanager.controllers.subcontrollers;

import es.angeldam.boardgamemanager.BoardGameManagerApplication;
import es.angeldam.boardgamemanager.dao.PublisherDAO;
import es.angeldam.boardgamemanager.dataAccess.ConnectionBD;
import es.angeldam.boardgamemanager.model.Publisher;
import es.angeldam.boardgamemanager.utils.Utils;
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
    @FXML public TableView<Publisher> publisherTable;
    @FXML public Button editPublisherButton;
    @FXML public Button addPublisherButton;
    @FXML public Button removePublisher;
    @FXML public TextField searchPublisher;
    @FXML public TableColumn<Publisher, String> pubNameCol;
    @FXML public TableColumn<Publisher, Integer> pubFoundationYearCol;
    @FXML public TableColumn<Publisher, String> pubHeadquartersCol;
    @FXML public TableColumn<Publisher, Integer> pubNumberBGCol;

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

    public void openFormPublisher(Publisher publisher) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(BoardGameManagerApplication.class.getResource("formPublisher-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            FormPublisherController controller = fxmlLoader.getController();
            controller.start(publisher);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(publisher == null ? "New publisher" : "Update publisher");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
            loadPublishers();
        } catch (Exception e) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR", "Error loading form", "Publisher form couldn't be loaded: " + e.getMessage());
        }
    }

    @FXML
    public void addPublisher() {
        openFormPublisher(null);
    }

    @FXML
    public void editPublisher() {
        Publisher publisher = publisherTable.getSelectionModel().getSelectedItem();
        if (publisher == null) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR SELECTION", "There isn't a publisher selected", "You must select a publisher");
            return;
        }
        openFormPublisher(publisher);
        loadPublishers();
        publisherTable.getSelectionModel().select(publisher);
    }

    @FXML
    public void removePublisher() {
        Publisher publisher = publisherTable.getSelectionModel().getSelectedItem();
        if (publisher == null) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR SELECTION", "There isn't a publisher selected", "You must select a publisher");
            return;
        }
        Optional<ButtonType> answer = Utils.alert(Alert.AlertType.CONFIRMATION, "Confirm Delete", "Confirm Delete on publisher", "Are you sure you want to remove: " + publisher.getName() + "?");
        if (answer.isPresent() && answer.get() == ButtonType.OK) {
            try {
                PublisherDAO.deletePublisherById(publisherTable.getSelectionModel().getSelectedItem().getCode());
                publisherTable.getItems().remove(publisher);
                publisherTable.getSelectionModel().clearSelection();
            } catch (SQLException e) {
                Utils.alert(Alert.AlertType.ERROR, "DATABASE ERROR", "Error with database", "The publisher couldn't be removed: " + e.getMessage());
            }
        }
    }

    @FXML
    public void searchPublisher() {

    }
}