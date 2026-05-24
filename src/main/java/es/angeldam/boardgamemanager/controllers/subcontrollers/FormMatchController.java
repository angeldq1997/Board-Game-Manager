package es.angeldam.boardgamemanager.controllers.subcontrollers;

import es.angeldam.boardgamemanager.dao.MatchDAO;
import es.angeldam.boardgamemanager.model.BoardGame;
import es.angeldam.boardgamemanager.model.Match;
import es.angeldam.boardgamemanager.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.util.Arrays;

/**
 * Controller that manages the form of match to allow the user to fill a form
 * with the intention of updates or add a match to the database
 */
public class FormMatchController {
    @FXML
    public Label formTitleLabel;
    @FXML
    public TextField txtPlace;
    @FXML
    public ComboBox<BoardGame> cmbBoardGame;
    @FXML
    public Button btnSave;
    @FXML
    public DatePicker datePicker;

    private Match matchToEdit;

    /**
     * Method that executes by default when the controller is called
     * @param match The match that want to be edited, if NULL the intention is to create a new one
     */
    @FXML
    public void initialize(Match match) {
        this.matchToEdit = match;
        addListeners();
        prepareText(match);
    }

    /**
     * Method that change the text of the form to empty when the match is null and with its data when isn't
     * @param match match to edit or null when wants to create a new one
     */
    private void prepareText(Match match) {
        if (match != null){
            txtPlace.setText(match.getPlace());
            cmbBoardGame.getSelectionModel().select(match.getBoardGame());
            formTitleLabel.setText("Update match");
        }else{
            txtPlace.setText("");
            cmbBoardGame.getSelectionModel().clearSelection();
            formTitleLabel.setText("Add match");
        }
    }

    /**
     * Method that verify the fields of the match
     * @return True when the data is valid (every field isn't empty) or False when they aren't filled up
     */
    public boolean validData(){
        return !(txtPlace.getText().isBlank() &&
                cmbBoardGame.getSelectionModel().getSelectedItem() == null &&
                datePicker.getDayCellFactory() == null);
    }

    /**
     * Method that updates the save button to enable it when the condition is met
     */
    private void updateSaveButton() {
        btnSave.setDisable(!validData());
    }

    @FXML
    public void closeWindow() {
        Stage stage = (Stage) formTitleLabel.getScene().getWindow();
        stage.close();
    }

    /**
     * Method that adds listener to multiples fields
     */
    private void addListeners(){
        txtPlace.textProperty().addListener((observable, oldValue, newValue) -> updateSaveButton());
    }

    /**
     * Method that stores a match with the data took from the form
     * when there isn't a match selected it makes a new one
     */
    @FXML
    public void storeMatch() {
        try{
            String name = txtPlace.getText();
            Timestamp date = (Timestamp) datePicker.getDayCellFactory();
            BoardGame boardGame = cmbBoardGame.getValue();

            Match newMatch = new Match(name, date, boardGame);
            if (this.matchToEdit == null){
                if (MatchDAO.addMatch(newMatch)){
                    Utils.alert(Alert.AlertType.INFORMATION, "MATCH ADDED SUCCESSFULLY", "Match added to the database", "The match with board game: " + newMatch.getBoardGame().getName() + " at " + newMatch.getPlace() + " was added without errors to the database");
                    closeWindow();
                }else{
                    Utils.alert(Alert.AlertType.ERROR, "ERROR UPDATING MATCH", "The match couldn't be added to database", "The match with board game: " + newMatch.getBoardGame().getName() + " at  " + newMatch.getPlace() + " couldn`t be added");
                }
            }else {
                if ( MatchDAO.updateMatch(matchToEdit, newMatch) ){
                    Utils.alert(Alert.AlertType.INFORMATION, "MATCH UPDATED", "The match was updated to the database", "The match with board game: " + newMatch.getBoardGame().getName() + " at  " + newMatch.getPlace() +" was updated to the database");
                    closeWindow();
                }else{
                    Utils.alert(Alert.AlertType.ERROR, "ERROR UPDATING MATCH", "The match couldn't be uploaded to database", "The match with board game: "+ newMatch.getBoardGame().getName() +" couldn`t be updated");
                }
            }
        }catch (Exception e){
            Utils.alert(Alert.AlertType.ERROR,"ERROR", "There was an error while storing match on database", "Details: " + e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
