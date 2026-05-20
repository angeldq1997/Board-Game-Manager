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

import java.sql.SQLException;
import java.util.Arrays;

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

    @FXML
    public void initialize(Designer designer) {
        this.designerToEdit = designer;
        addListeners();
        prepareText(designer);
        storeDesigner();
    }

    public boolean validData(){
        return !(txtName.getText().isBlank() &&
                txtNationality.getText().isBlank() &&
                txtBirthYear.getText().isBlank() &&
                txtAlias.getText().isBlank());
    }

    private void updateSaveButton() {
        btnSave.setDisable(!validData());
    }

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

    @FXML
    public void closeWindow() {
        Stage stage = (Stage) formTitleLabel.getScene().getWindow();
        stage.close();
    }

    private void addListeners(){
        txtName.textProperty().addListener((observable, oldValue, newValue) -> updateSaveButton());
        txtBirthYear.textProperty().addListener((observable, oldValue, newValue) -> updateSaveButton());
        addListenerLimitedSize(txtBirthYear, 4);
        addListener(txtBirthYear, "[1-2][0,1,9][\\d]{2}", "[^\\d]");

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
                }
            }else {
                if ( DesignerDAO.updateDesigner(designerToEdit, newDesigner) ){
                    Utils.alert(Alert.AlertType.INFORMATION, "DESIGNER UPDATED", "The designer was updated to the database", "The designer with name: "+newDesigner.getName()+" was updated to the database");
                    closeWindow();
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