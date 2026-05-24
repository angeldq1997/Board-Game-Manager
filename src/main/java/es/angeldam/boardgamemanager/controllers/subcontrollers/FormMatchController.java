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

    @FXML
    public void start(Match match) {
        this.matchToEdit = match;
        addListeners();
        prepareText(match);
    }

    public boolean validData(){
        return !(txtPlace.getText().isBlank() &&
                cmbBoardGame.getSelectionModel().getSelectedItem() == null &&
                datePicker.getDayCellFactory() == null);
    }

    private void updateSaveButton() {
        btnSave.setDisable(!validData());
    }

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

    @FXML
    public void closeWindow() {
        Stage stage = (Stage) formTitleLabel.getScene().getWindow();
        stage.close();
    }

    private void addListeners(){
        txtPlace.textProperty().addListener((observable, oldValue, newValue) -> updateSaveButton());
    }

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
