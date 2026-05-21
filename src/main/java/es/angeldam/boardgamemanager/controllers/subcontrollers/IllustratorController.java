package es.angeldam.boardgamemanager.controllers.subcontrollers;

import es.angeldam.boardgamemanager.BoardGameManagerApplication;
import es.angeldam.boardgamemanager.dao.IllustratorDAO;
import es.angeldam.boardgamemanager.dataAccess.ConnectionBD;
import es.angeldam.boardgamemanager.model.Illustrator;
import es.angeldam.boardgamemanager.utils.Utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class IllustratorController {

    @FXML public TableView<Illustrator> illustratorTable;
    @FXML public TextField searchIllustratorField;
    @FXML public Button addIllustratorButton;
    @FXML public Button editIllustratorButton;
    @FXML public Button removeIllustratorButton;
    @FXML public TableColumn<Illustrator, String> illustratorNameCol;
    @FXML public TableColumn<Illustrator, Integer> illustratorBirthDateCol;
    @FXML public TableColumn<Illustrator, String> illustratorNationalityCol;
    @FXML public TableColumn<Illustrator, String> illustratorBoardGameCol;

    @FXML
    public void initialize() {
        configureIllustratorTable(loadIllustrators());
    }

    public List<Illustrator> loadIllustrators() {
        List<Illustrator> illustrators = null;
        if (ConnectionBD.getConnection() == null) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR CONNECTING DATABASE", "There was an error loading database", "The database couldn't connect and retrieve designer data.");
        } else {
            try {
                illustrators = IllustratorDAO.findAll();
            } catch (SQLException e) {
                Utils.alert(Alert.AlertType.ERROR, "ERROR LOADING ILLUSTRATORS", "There was an error loading illustrators", e.getMessage());
            }
        }
        return illustrators;
    }

    public void configureIllustratorTable(List<Illustrator> illustrators) {
        illustratorTable.setPlaceholder(new Label("There isn't designers to show"));

        illustratorNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        illustratorBirthDateCol.setCellValueFactory(new PropertyValueFactory<>("birthYear"));
        illustratorNationalityCol.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        illustratorBoardGameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().listBoardGames()));

        ObservableList<Illustrator> illustratorObservableList = FXCollections.observableArrayList(illustrators);
        illustratorTable.setItems(illustratorObservableList);

        editIllustratorButton.disableProperty().bind(illustratorTable.getSelectionModel().selectedItemProperty().isNull());
        removeIllustratorButton.disableProperty().bind(illustratorTable.getSelectionModel().selectedItemProperty().isNull());
    }

    public void openFormIllustrator(Illustrator illustrator) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(BoardGameManagerApplication.class.getResource("formIllustrator-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            FormIllustratorController controller = fxmlLoader.getController();
            controller.start(illustrator);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(illustrator == null ? "New illustrator" : "Update illustrator");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
            loadIllustrators();
        } catch (Exception e) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR", "Error loading form", "Illustrator form couldn't be loaded: " + e.getMessage());
        }
    }

    @FXML
    public void addIllustrator() {
        openFormIllustrator(null);
        loadIllustrators();
    }

    @FXML
    public void editIllustrator( ) {
        Illustrator illustrator = illustratorTable.getSelectionModel().getSelectedItem();
        if (illustrator == null) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR SELECTION", "There isn't a illustrator selected", "You must select a illustrator");
            return;
        }
        openFormIllustrator(illustrator);
        loadIllustrators();
        illustratorTable.getSelectionModel().select(illustrator);
    }

    @FXML
    public void removeIllustrator( ) {
        Illustrator illustrator = illustratorTable.getSelectionModel().getSelectedItem();
        if (illustrator == null) {
            Utils.alert(Alert.AlertType.ERROR,"ERROR SELECTION", "There isn't a illustrator selected", "You must select a illustrator");
            return;
        }
        Optional<ButtonType> answer = Utils.alert(Alert.AlertType.CONFIRMATION, "Confirm Delete", "Confirm Delete on illustrator", "Are you sure you want to remove: " + illustrator.getName() + "?");
        if(answer.isPresent() && answer.get() == ButtonType.OK) {
            try {
                IllustratorDAO.deleteIllustratorById(illustratorTable.getSelectionModel().getSelectedItem().getCode());
                illustratorTable.getItems().remove(illustrator);
                illustratorTable.getSelectionModel().clearSelection();
            } catch (SQLException e) {
                Utils.alert(Alert.AlertType.ERROR, "DATABASE ERROR", "Error with database", "The illustrator couldn't be removed: " + e.getMessage());
            }
        }
    }
    @FXML
    public void searchIllustrator( ) {

    }
}