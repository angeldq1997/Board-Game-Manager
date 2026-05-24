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

/**
 * Controller that manages the form of publisher to allow the user to fill a form
 * with the intention of updates or add a publisher to the database
 */
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

    /**
     * Method that executes by default when the controller is called
     * @param publisher The publisher that want to be edited, if NULL the intention is to create a new one
     */
    @FXML
    public void initialize(Publisher publisher) {
        this.publisherToEdit = publisher;
        addListeners();
        prepareText(publisher);
    }

    /**
     * Method that verify the fields of the publisher
     * @return True when the data is valid (every field isn't empty) or False when they aren't filled up
     */
    public boolean validData() {
        return !(txtName.getText().isBlank() &&
                txtHeadquarters.getText().isBlank() &&
                txtFoundationYear.getText().isBlank());
    }

    /**
     * Method that updates the save button to enable it when the condition is met
     */
    private void updateSaveButton() {
        btnSave.setDisable(!validData());
    }

    /**
     * Method that change the text of the form to empty when the publisher is null and with its data when isn't
     * @param publisher publisher to edit or null when wants to create a new one
     */
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

    /**
     * Method that adds listener to multiples fields
     */
    private void addListeners() {
        txtName.textProperty().addListener((observable, oldValue, newValue) -> updateSaveButton());
        txtFoundationYear.textProperty().addListener((observable, oldValue, newValue) -> updateSaveButton());
        addListenerLimitedSize(txtFoundationYear, 4);
        addListener(txtFoundationYear, "[1-2][0,1,9][\\d]{2}", "[a-zA-Z]");
    }

    /**
     * Method that assigns a listener with the purpose of narrow the possibilities to write on text field
     * @param textField Text field to apply the listener
     * @param match the regex to match the text with it
     * @param replace the replacement string when the condition isn't fulfilled
     */
    private void addListener(TextField textField, String match, String replace) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches(match)) {
                    textField.setText(newValue.replaceAll(replace, ""));
                }
            }
        });
    }

    /**
     * Method that assigns a listener with the purpose of limiting the maximum text of it
     * @param textField Text field to apply the listener
     * @param max Maximum number of characters
     */
    private void addListenerLimitedSize(TextField textField, int max) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > max) {
                String copy = newValue.substring(0, max);
                textField.setText(copy);
            }
            updateSaveButton();
        });
    }

    /**
     * Method that stores a publisher with the data took from the form
     * when there isn't a publisher selected it makes a new one
     */
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

    /**
     * Method that add a board game to the publisher
     */
    public void addBoardGameToEntity() {

    }

    /**
     * Method to close the form window
     */
    @FXML
    public void closeWindow() {
        Stage stage = (Stage) formTitleLabel.getScene().getWindow();
        stage.close();
    }
}