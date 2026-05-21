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

public class FormPublisherController {

    @FXML
    public Button btnSave;
    @FXML
    public ComboBox<BoardGame> cmbBoardGame;
    @FXML
    public TextField txtName;
    @FXML
    public TextField txtAlias;
    @FXML
    public TextField txtFoundationYear;
    @FXML
    public TextField txtNationality;
    @FXML
    public Label formTitleLabel;

    private Designer designerToEdit;

    @FXML
    public void start(Designer designer) {
        this.designerToEdit = designer;
        addListeners();
        prepareText(designer);
    }

    public boolean validData(){
        return !(txtName.getText().isBlank() &&
                txtNationality.getText().isBlank() &&
                txtFoundationYear.getText().isBlank() &&
                txtAlias.getText().isBlank());
    }

    private void updateSaveButton() {
        btnSave.setDisable(!validData());
    }

    private void prepareText(Designer designer) {
        if (designer != null){
            txtName.setText(designer.getName());
            txtAlias.setText(designer.getAlias());
            txtFoundationYear.setText(String.valueOf( designer.getBirthYear() ));
            txtNationality.setText(designer.getNationality());

            formTitleLabel.setText("Update designer");
        }else{
            txtName.setText("");
            txtAlias.setText("");
            txtFoundationYear.setText("");
            txtNationality.setText("");
            formTitleLabel.setText("Add designer");
        }
    }

    @FXML
    public void closeWindow() {
        Stage stage = (Stage) formTitleLabel.getScene().getWindow();
        stage.close();
    }

    private void addListeners(){
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
        try{
            String name = txtName.getText();
            String alias = txtAlias.getText();
            int birthYear = Integer.parseInt(txtFoundationYear.getText());
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

    public void addBoardGameToEntity() {

    }
}