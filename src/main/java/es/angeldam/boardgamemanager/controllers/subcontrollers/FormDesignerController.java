package es.angeldam.boardgamemanager.controllers.subcontrollers;

import es.angeldam.boardgamemanager.dao.DesignerDAO;
import es.angeldam.boardgamemanager.model.BoardGame;
import es.angeldam.boardgamemanager.model.Designer;
import es.angeldam.boardgamemanager.utils.Utils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Arrays;

/**
 * Controller that manages the form of designer to allow the user to fill a form
 * with the intention of updates or add a designer to the database
 */
public class FormDesignerController {

    @FXML
    public Button btnSave;
    @FXML
    public ComboBox<BoardGame> cmbBoardGame;
    @FXML
    public TextField txtName;
    @FXML
    public TextField txtAlias;
    @FXML
    public TextField txtBirthYear;
    @FXML
    public TextField txtNationality;
    @FXML
    public Label formTitleLabel;

    private Designer designerToEdit;

    /**
     * Method that executes by default when the controller is called
     * @param designer The designer that want to be edited, if NULL the intention is to create a new one
     */
    @FXML
    public void initialize(Designer designer) {
        this.designerToEdit = designer;
        addListeners();
        prepareText(designer);
    }

    /**
     * Method that change the text of the form to empty when the designer is null and with its data when isn't
     * @param designer designer to edit or null when wants to create a new one
     */
    private void prepareText(Designer designer) {
        if (designer != null){
            txtName.setText(designer.getName());
            txtAlias.setText(designer.getAlias());
            txtBirthYear.setText(String.valueOf( designer.getBirthYear() ));
            txtNationality.setText(designer.getNationality());

            formTitleLabel.setText("Update designer");
        }else{
            txtName.setText("");
            txtAlias.setText("");
            txtBirthYear.setText("");
            txtNationality.setText("");
            formTitleLabel.setText("Add designer");
        }
    }

    /**
     * Method that verify the fields of the designer
     * @return True when the data is valid (every field isn't empty) or False when they aren't filled up
     */
    public boolean validData(){
        return !(txtName.getText().isBlank() &&
                txtNationality.getText().isBlank() &&
                txtBirthYear.getText().isBlank() &&
                txtAlias.getText().isBlank());
    }

    /**
     * Method that updates the save button to enable it when the condition is met
     */
    private void updateSaveButton() {
        btnSave.setDisable(!validData());
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
     * Method that stores a designer with the data took from the form
     * when there isn't a designer selected it makes a new one
     */
    @FXML
    public void storeDesigner() {
        try{
            String name = txtName.getText();
            String alias = txtAlias.getText();
            int birthYear = Integer.parseInt(txtBirthYear.getText());
            String nationality = txtNationality.getText();
            Designer newDesigner = new Designer(name, alias, birthYear, nationality);
            if (this.designerToEdit == null){
                if (DesignerDAO.addDesigner(newDesigner)){
                    Utils.alert(Alert.AlertType.INFORMATION, "DESIGNER ADDED SUCCESSFULLY", "Designer added to the database", "The designer" + newDesigner.getName() + " was added without errors to the database");
                    closeWindow();
                }else{
                    Utils.alert(Alert.AlertType.ERROR, "ERROR UPDATING DESIGNER", "The designer couldn't be added to database", "The designer "+ newDesigner.getName() +" couldn`t be added");
                }
            }else {
                if ( DesignerDAO.updateDesigner(designerToEdit, newDesigner) ){
                    Utils.alert(Alert.AlertType.INFORMATION, "DESIGNER UPDATED", "The designer was updated to the database", "The designer with name: "+newDesigner.getName()+" was updated to the database");
                    closeWindow();
                }else{
                    Utils.alert(Alert.AlertType.ERROR, "ERROR UPDATING DESIGNER", "The designer couldn't be uploaded to database", "The designer "+ newDesigner.getName() +" couldn`t be updated");
                }
            }
        }catch (Exception e){
            Utils.alert(Alert.AlertType.ERROR,"ERROR", "There was an error while storing designer on database", "Details: " + e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * Method that add a board game to the designer
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