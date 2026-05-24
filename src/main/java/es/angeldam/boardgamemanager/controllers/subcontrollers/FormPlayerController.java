package es.angeldam.boardgamemanager.controllers.subcontrollers;

import es.angeldam.boardgamemanager.dao.PlayerDAO;
import es.angeldam.boardgamemanager.model.Player;
import es.angeldam.boardgamemanager.utils.Utils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Arrays;

public class FormPlayerController {
    @FXML
    public TextField txtName;
    @FXML
    public TextField txtBirthYear;
    @FXML
    public Label formTitleLabel;
    @FXML
    public Button btnSave;

    private Player playerToEdit;

    @FXML
    public void start(Player player) {
        this.playerToEdit = player;
        addListeners();
        prepareText(player);
    }

    public boolean validData(){
        return !(txtName.getText().isBlank() &&
                txtBirthYear.getText().isBlank());
    }

    private void updateSaveButton() {
        btnSave.setDisable(!validData());
    }

    private void prepareText(Player player) {
        if (player != null){
            txtName.setText(player.getName());
            txtBirthYear.setText(String.valueOf( player.getBirthYear() ));

            formTitleLabel.setText("Update player");
        }else{
            txtName.setText("");
            txtBirthYear.setText("");
            formTitleLabel.setText("Add player");
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
        addListener(txtBirthYear, "[1-2][0,1,9][\\d]{2}", "[a-zA-Z]");
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
    public void storePlayer() {
        try{
            String name = txtName.getText();
            int birthYear = Integer.parseInt(txtBirthYear.getText());

            Player newPlayer = new Player(name, birthYear);
            if (this.playerToEdit == null){
                if (PlayerDAO.addPlayer(newPlayer)){
                    Utils.alert(Alert.AlertType.INFORMATION, "PLAYER ADDED SUCCESSFULLY", "Player added to the database", "The player " + newPlayer.getName() + " was added without errors to the database");
                    closeWindow();
                }else{
                    Utils.alert(Alert.AlertType.ERROR, "ERROR UPDATING PLAYER", "The player couldn't be added to database", "The player "+ newPlayer.getName() +" couldn`t be added");
                }
            }else {
                if ( PlayerDAO.updatePlayer(playerToEdit, newPlayer) ){
                    Utils.alert(Alert.AlertType.INFORMATION, "PLAYER UPDATED", "The player was updated to the database", "The player with name: "+newPlayer.getName()+" was updated to the database");
                    closeWindow();
                }else{
                    Utils.alert(Alert.AlertType.ERROR, "ERROR UPDATING PLAYER", "The player couldn't be uploaded to database", "The player "+ newPlayer.getName() +" couldn`t be updated");
                }
            }
        }catch (Exception e){
            Utils.alert(Alert.AlertType.ERROR,"ERROR", "There was an error while storing player on database", "Details: " + e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
