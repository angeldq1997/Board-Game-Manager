package es.angeldam.boardgamemanager.controllers.subcontrollers;

import es.angeldam.boardgamemanager.dao.IllustratorDAO;
import es.angeldam.boardgamemanager.model.BoardGame;
import es.angeldam.boardgamemanager.model.Illustrator;
import es.angeldam.boardgamemanager.utils.Utils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Arrays;

/**
 * Controller that manages the form of illustrator to allow the user to fill a form
 * with the intention of updates or add a illustrator to the database
 */
public class FormIllustratorController {

    @FXML
    public Button btnSave;
    @FXML
    public ComboBox<BoardGame> cmbBoardGame;
    @FXML
    public TextField txtName;
    @FXML
    public TextField txtBirthYear;
    @FXML
    public TextField txtNationality;
    @FXML
    public Label formTitleLabel;

    private Illustrator illustratorToEdit;

    /**
     * Method that executes by default when the controller is called
     * @param illustrator The illustrator that want to be edited, if NULL the intention is to create a new one
     */
    @FXML
    public void initialize(Illustrator illustrator) {
        this.illustratorToEdit = illustrator;
        addListeners();
        prepareText(illustrator);
    }

    /**
     * Method that verify the fields of the illustrator
     * @return True when the data is valid (every field isn't empty) or False when they aren't filled up
     */
    public boolean validData(){
        return !(txtName.getText().isBlank() &&
                txtNationality.getText().isBlank() &&
                txtBirthYear.getText().isBlank());
    }

    /**
     * Method that change the text of the form to empty when the illustrator is null and with its data when isn't
     * @param illustrator illustrator to edit or null when wants to create a new one
     */
    private void prepareText(Illustrator illustrator) {
        if (illustrator != null){
            txtName.setText(illustrator.getName());
            txtBirthYear.setText(String.valueOf( illustrator.getBirthYear() ));
            txtNationality.setText(illustrator.getNationality());

            formTitleLabel.setText("Update illustrator");
        }else{
            txtName.setText("");
            txtBirthYear.setText("");
            txtNationality.setText("");
            formTitleLabel.setText("Add illustrator");
        }
    }

    /**
     * Method that adds listener to multiples fields
     */
    private void addListeners(){
        txtName.textProperty().addListener((observable, oldValue, newValue) -> updateSaveButton());
        txtBirthYear.textProperty().addListener((observable, oldValue, newValue) -> updateSaveButton());
        addListenerLimitedSize(txtBirthYear, 4);
        addListener(txtBirthYear, "[1-2][0,1,9][\\d]{2}", "[a-zA-Z]");
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
     * Method that stores a illustrator with the data took from the form
     * when there isn't a illustrator selected it makes a new one
     */
    @FXML
    public void storeIllustrator() {
        try{
            String name = txtName.getText();
            int birthYear = Integer.parseInt(txtBirthYear.getText());
            String nationality = txtNationality.getText();
            Illustrator newIllustrator = new Illustrator(name, birthYear, nationality);
            if (this.illustratorToEdit == null){
                if (IllustratorDAO.addIllustrator(newIllustrator)){
                    Utils.alert(Alert.AlertType.INFORMATION, "ILLUSTRATOR ADDED SUCCESSFULLY", "Illustrator added to the database", "The illustrator" + newIllustrator.getName() + " was added without errors to the database");
                    closeWindow();
                }
            }else {
                if ( IllustratorDAO.updateIllustrator(illustratorToEdit, newIllustrator) ){
                    Utils.alert(Alert.AlertType.INFORMATION, "ILLUSTRATOR UPDATED", "The illustrator was updated to the database", "The illustrator with name: "+newIllustrator.getName()+" was updated to the database");
                    closeWindow();
                }
            }
        }catch (Exception e){
            Utils.alert(Alert.AlertType.ERROR,"ERROR", "There was an error while storing illustrator on database", "Details: " + e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }


    private void updateSaveButton() {
        btnSave.setDisable(!validData());
    }

    /**
     * Method that add a board game to the illustrator
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