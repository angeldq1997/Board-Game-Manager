package es.angeldam.boardgamemanager.controllers.subcontrollers;

import es.angeldam.boardgamemanager.dao.PublisherDAO;
import es.angeldam.boardgamemanager.model.BoardGame;
import es.angeldam.boardgamemanager.model.Publisher;
import es.angeldam.boardgamemanager.utils.Utils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Arrays;

public class FormPublisherController {

    @FXML
    public Button btnSave;
    @FXML
    public ComboBox<BoardGame> cmbBoardGame;
    @FXML
    public TextField txtName;
    @FXML
    public TextField txtFoundationYear;
    @FXML
    public TextField txtHeadquarters;
    @FXML
    public Label formTitleLabel;

    private Publisher publisherToEdit;

    @FXML
    public void start(Publisher publisher) {
        this.publisherToEdit = publisher;
        addListeners();
        prepareText(publisher);
    }

    public boolean validData() {
        return !(txtName.getText().isBlank() &&
                txtHeadquarters.getText().isBlank() &&
                txtFoundationYear.getText().isBlank());
    }

    private void updateSaveButton() {
        btnSave.setDisable(!validData());
    }

    private void prepareText(Publisher publisher) {
        if (publisher != null) {
            txtName.setText(publisher.getName());
            txtFoundationYear.setText(String.valueOf(publisher.getFoundationYear()));
            txtHeadquarters.setText(publisher.getHeadquarters());

            formTitleLabel.setText("Update publisher");
        } else {
            txtName.setText("");
            txtFoundationYear.setText("");
            txtHeadquarters.setText("");
            formTitleLabel.setText("Add publisher");
        }
    }

    @FXML
    public void closeWindow() {
        Stage stage = (Stage) formTitleLabel.getScene().getWindow();
        stage.close();
    }

    private void addListeners() {
        txtName.textProperty().addListener((observable, oldValue, newValue) -> updateSaveButton());
        txtFoundationYear.textProperty().addListener((observable, oldValue, newValue) -> updateSaveButton());
        addListenerLimitedSize(txtFoundationYear, 4);
        addListener(txtFoundationYear, "[1-2][0,1,9][\\d]{2}", "[a-zA-Z]");
    }

    private void addListener(TextField txtFieldName, String match, String replace) {
        txtFieldName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches(match)) {
                    txtFieldName.setText(newValue.replaceAll(replace, ""));
                }
            }
        });
    }

    private void addListenerLimitedSize(TextField textField, int max) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > max) {
                String copy = newValue.substring(0, max);
                textField.setText(copy);
            }
            updateSaveButton();
        });
    }

    @FXML
    public void storePublisher() {
        try {
            String name = txtName.getText();
            int foundationYear = Integer.parseInt(txtFoundationYear.getText());
            String headquarters = txtHeadquarters.getText();
            Publisher newPublisher = new Publisher(name, foundationYear, headquarters);
            if (this.publisherToEdit == null) {
                if (PublisherDAO.addPublisher(newPublisher)) {
                    Utils.alert(Alert.AlertType.INFORMATION, "PUBLISHER ADDED SUCCESSFULLY", "Publisher added to the database", "The publisher" + newPublisher.getName() + " was added without errors to the database");
                    closeWindow();
                } else {
                    Utils.alert(Alert.AlertType.ERROR, "ERROR UPDATING PUBLISHER", "The publisher couldn't be added to database", "The publisher " + newPublisher.getName() + " couldn`t be added");
                }
            } else {
                if (PublisherDAO.updatePublisher(publisherToEdit, newPublisher)) {
                    Utils.alert(Alert.AlertType.INFORMATION, "PUBLISHER UPDATED", "The publisher was updated to the database", "The publisher with name: " + newPublisher.getName() + " was updated to the database");
                    closeWindow();
                } else {
                    Utils.alert(Alert.AlertType.ERROR, "ERROR UPDATING PUBLISHER", "The publisher couldn't be uploaded to database", "The publisher " + newPublisher.getName() + " couldn`t be updated");
                }
            }
        } catch (Exception e) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR", "There was an error while storing publisher on database", "Details: " + e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    public void addBoardGameToEntity() {

    }
}