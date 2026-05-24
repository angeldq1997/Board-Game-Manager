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

/**
 * Controller that manages the form of player to allow the user to fill a form
 * with the intention of updates or add a player to the database
 */
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

    /**
     * Method that executes by default when the controller is called
     * @param player The player that want to be edited, if NULL the intention is to create a new one
     */
    @FXML
    public void initialize(Player player) {
        this.playerToEdit = player;
        addListeners();
        prepareText(player);
    }

    private void updateSaveButton() {
        btnSave.setDisable(!validData());
    }

    /**
     * Method that change the text of the form to empty when the player is null and with its data when isn't
     * @param player player to edit or null when wants to create a new one
     */
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
     * Method that stores a player with the data took from the form
     * when there isn't a player selected it makes a new one
     */
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

    /**
     * Method that verify the fields of the player
     * @return True when the data is valid (every field isn't empty) or False when they aren't filled up
     */
    public boolean validData(){
        return !(txtName.getText().isBlank() &&
                txtBirthYear.getText().isBlank());
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
