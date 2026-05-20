package es.angeldam.boardgamemanager.controllers.subcontrollers;

import es.angeldam.boardgamemanager.dao.*;
import es.angeldam.boardgamemanager.model.*;
import es.angeldam.boardgamemanager.utils.Difficulty;
import es.angeldam.boardgamemanager.utils.Utils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class FormBoardGameController {
    @FXML public Label formTitleLabel;
    @FXML public Button btnSave;
    @FXML public TextField txtName;
    @FXML public TextField txtMinPlayers;
    @FXML public TextField txtMaxPlayers;
    @FXML public TextField txtAvgDuration;
    @FXML public TextField txtAge;
    @FXML public TextField txtPubYear;
    @FXML public ComboBox<Difficulty> cmbDifficulty;
    @FXML public TextField txtRanking;
    @FXML public TextField txtMechanics;
    @FXML public ComboBox<Designer> cmbDesigner1;
    @FXML public ComboBox<Designer> cmbDesigner2;
    @FXML public ComboBox<Designer> cmbDesigner3;
    @FXML public ComboBox<Illustrator> cmbIllustrator1;
    @FXML public ComboBox<Illustrator> cmbIllustrator2;
    @FXML public ComboBox<Illustrator> cmbIllustrator3;
    @FXML public ComboBox<Publisher> cmbPublisher1;
    @FXML public ComboBox<Publisher> cmbPublisher2;
    @FXML public ComboBox<Publisher> cmbPublisher3;

    private BoardGame boardGameToEdit;

    @FXML public void initialize(BoardGame boardGame) {
        this.boardGameToEdit = boardGame;

        loadDesigners();
        loadIllustrators();
        loadPublishers();
        cmbDifficulty.getItems().addAll(Difficulty.values());
        configComboBoxDesigner();
        configComboBoxIllustrator();
        configComboBoxPublisher();
        configListeners();
        setText(boardGame);
        updateSaveButton();
    }

    private void setText(BoardGame boardGame) {
        if (boardGame != null) {
            txtName.setText(boardGame.getName());
            txtMechanics.setText(boardGame.getMechanics());
            txtMinPlayers.setText(String.valueOf(boardGame.getMinPlayers()));
            txtMaxPlayers.setText(String.valueOf(boardGame.getMaxPlayers()));
            txtAvgDuration.setText(String.valueOf(boardGame.getAverageDuration()));
            txtAge.setText(boardGame.getRecommendedAge());
            txtPubYear.setText(String.valueOf(boardGame.getPublicationYear()));
            cmbDifficulty.setValue(boardGame.getDifficulty());
            txtRanking.setText(String.valueOf(boardGame.getRanking()));

            if (boardGame.getDesigners() != null && !boardGame.getDesigners().isEmpty() && boardGame.getDesigners().get(0) != null ){
                cmbDesigner1.setValue(boardGame.getDesigners().getFirst());
            }

            if (boardGame.getIllustrators() != null && !boardGame.getIllustrators().isEmpty() && boardGame.getIllustrators().get(0) != null ){
                cmbIllustrator1.setValue(boardGame.getIllustrators().getFirst());
            }

            if (boardGame.getPublishers() != null && !boardGame.getPublishers().isEmpty() && boardGame.getPublishers().get(0) != null ){
                cmbPublisher1.setValue(boardGame.getPublishers().getFirst());
            }

            formTitleLabel.setText("Update board game");
        } else {
            txtName.setText("");
            txtMechanics.setText("");
            txtMinPlayers.setText("");
            txtMaxPlayers.setText("");
            txtAvgDuration.setText("");
            txtAge.setText("");
            txtPubYear.setText("");
            cmbDifficulty.setValue(null);
            txtRanking.setText("");
            cmbDesigner1.setValue(null);
            cmbIllustrator1.setValue(null);
            cmbPublisher1.setValue(null);
            formTitleLabel.setText("Add board game");
        }
    }

    public boolean validData() {
        return !(txtName.getText().isBlank() &&
                txtMinPlayers.getText().isBlank() &&
                txtMaxPlayers.getText().isBlank() &&
                txtAvgDuration.getText().isBlank() &&
                txtAge.getText().isBlank() &&
                txtPubYear.getText().isBlank() &&
                cmbDifficulty.getSelectionModel().isEmpty() &&
                txtRanking.getText().isBlank()  &&
                txtMechanics.getText().isBlank() &&
                cmbDesigner1.getSelectionModel().isEmpty() &&
                cmbIllustrator1.getSelectionModel().isEmpty() &&
                cmbPublisher1.getSelectionModel().isEmpty());
    }

    private void configComboBoxDesigner() {
        configComboBoxDesigner(cmbDesigner1);
        configComboBoxDesigner(cmbDesigner2);
        configComboBoxDesigner(cmbDesigner3);

        setPlaceHolderEmpty(cmbDesigner1);
        setPlaceHolderEmpty(cmbDesigner2);
        setPlaceHolderEmpty(cmbDesigner3);
    }

    private void configComboBoxDesigner(ComboBox<Designer> cmbDesigner) {
        cmbDesigner.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Designer designer, boolean empty) {
                super.updateItem(designer, empty);
                setText(empty || designer == null ? null : designer.getName());
            }
        });

        cmbDesigner.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Designer designer, boolean empty) {
                super.updateItem(designer, empty);
                setText(empty || designer == null ? null : designer.getName());
            }
        });
    }

    private void configComboBoxIllustrator() {
        configComboBoxIllustrator(cmbIllustrator1);
        configComboBoxIllustrator(cmbIllustrator2);
        configComboBoxIllustrator(cmbIllustrator3);

        setPlaceHolderEmpty(cmbIllustrator1);
        setPlaceHolderEmpty(cmbIllustrator2);
        setPlaceHolderEmpty(cmbIllustrator3);
    }

    private void configComboBoxIllustrator(ComboBox<Illustrator> cmbIllustrator) {
        cmbIllustrator.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Illustrator i, boolean empty) {
                super.updateItem(i, empty);
                setText(empty || i == null ? null : i.getName());
            }
        });

        cmbIllustrator.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Illustrator illustrator, boolean empty) {
                super.updateItem(illustrator, empty);
                setText(empty || illustrator == null ? null : illustrator.getName());
            }
        });
    }

    private void configComboBoxPublisher() {
        configComboBoxPublisher(cmbPublisher1);
        configComboBoxPublisher(cmbPublisher2);
        configComboBoxPublisher(cmbPublisher3);

        setPlaceHolderEmpty(cmbPublisher1);
        setPlaceHolderEmpty(cmbPublisher2);
        setPlaceHolderEmpty(cmbPublisher3);
    }

    private void configComboBoxPublisher(ComboBox<Publisher> cmbPublisher) {
        cmbPublisher.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Publisher p, boolean empty) {
                super.updateItem(p, empty);
                setText(empty || p == null ? null : p.getName());
            }
        });

        cmbPublisher.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Publisher p, boolean empty) {
                super.updateItem(p, empty);
                setText(empty || p == null ? null : p.getName());
            }
        });
    }

    private void loadDesigners() {
        try {
            List<Designer> designers = DesignerDAO.findAll();
            designers.sort((a1, a2) -> a1.getName().compareTo(a2.getName()));
            cmbDesigner1.setItems(FXCollections.observableArrayList(designers));
            cmbDesigner2.setItems(FXCollections.observableArrayList(designers));
            cmbDesigner3.setItems(FXCollections.observableArrayList(designers));
        } catch (Exception e) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR DESIGNER", "Error loading designers", "Couldn't get designers from the database: " + e.getMessage());
        }
    }

    private void loadIllustrators() {
        try {
            List<Illustrator> illustrators = IllustratorDAO.findAll();
            illustrators.sort((i1, i2) -> i1.getName().compareTo(i2.getName()));
            cmbIllustrator1.setItems(FXCollections.observableArrayList(illustrators));
            cmbIllustrator2.setItems(FXCollections.observableArrayList(illustrators));
            cmbIllustrator3.setItems(FXCollections.observableArrayList(illustrators));
        } catch (Exception e) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR ILLUSTRATOR", "Error loading illustrators", "Couldn't get illustrators from the database: " + e.getMessage());
        }
    }

    private void loadPublishers() {
        try {
            List<Publisher> publishers = PublisherDAO.findAll();
            publishers.sort((p1, p2) -> p1.getName().compareTo(p2.getName()));
            cmbPublisher1.setItems(FXCollections.observableArrayList(publishers));
            cmbPublisher2.setItems(FXCollections.observableArrayList(publishers));
            cmbPublisher3.setItems(FXCollections.observableArrayList(publishers));
        } catch (Exception e) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR PUBLISHER", "Error loading publishers", "Couldn't get publishers from the database: " + e.getMessage());
        }
    }

    private void configListeners() {
        txtName.textProperty().addListener((observable, oldValue, newValue) -> updateSaveButton());
        addListenerLimitedSize(txtMinPlayers, 3);
        addListenerLimitedSize(txtMaxPlayers, 3);
        addListenerLimitedSize(txtAvgDuration, 4);
        addListenerLimitedSize(txtAge, 2);
        txtPubYear.textProperty().addListener((observable, oldValue, newValue) -> updateSaveButton());
        cmbDifficulty.valueProperty().addListener((observable, oldValue, newValue) -> updateSaveButton());
        txtRanking.textProperty().addListener((observable, oldValue, newValue) -> updateSaveButton());
        txtMechanics.textProperty().addListener((observable, oldValue, newValue) -> updateSaveButton());
        addListenerObjectVisibility(cmbDesigner1, cmbDesigner2);
        addListenerObjectVisibility(cmbDesigner2, cmbDesigner3);

        addListenerObjectVisibility(cmbIllustrator1, cmbIllustrator2);
        addListenerObjectVisibility(cmbIllustrator2, cmbIllustrator3);

        addListenerObjectVisibility(cmbPublisher1, cmbPublisher2);
        addListenerObjectVisibility(cmbPublisher2, cmbPublisher3);


        addListener(txtMinPlayers, "[\\d]{1,3}", "[^\\d]");
        addListener(txtMaxPlayers, "\\d{1,3}", "[^\\d]");
        addListener(txtAvgDuration, "\\d{1,4}", "\\d+");
        addListener(txtPubYear, "[1-2][0,1,9][\\d]{2}", "[^\\d]");
        addListener(txtRanking, "\\d{1,3}", "\\d+");
    }

    private <T> void addListenerObjectVisibility(ComboBox<T> comboBoxToCheck, ComboBox<T> comboBoxToShow) {
        comboBoxToCheck.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(comboBoxToCheck.getSelectionModel().getSelectedItem() != null){
                comboBoxToShow.setVisible(true);
                comboBoxToShow.setDisable(false);
                //TODO: FIX CHOOSING TWO IN COMBOBOX REMOVE BOTH WHEN SHOWING NEXT
                comboBoxToShow.getItems().remove(comboBoxToCheck.getSelectionModel().getSelectedItem());
            }else{
                comboBoxToShow.setVisible(false);
                comboBoxToShow.setDisable(true);
            }
            updateSaveButton();
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

    private <T> void setPlaceHolderEmpty(ComboBox<T> tComboBox) {
        tComboBox.setPlaceholder(new Label("EMPTY"));
    }

    private void updateSaveButton() {
        btnSave.setDisable(!validData());
    }

    public void storeBoardGame() {
        ArrayList<Designer> designers = new ArrayList<>();
        ArrayList<Illustrator> illustrators = new ArrayList<>();
        ArrayList<Publisher> publishers = new ArrayList<>();
        try {
            String name = txtName.getText();
            int minPlayers = Integer.parseInt(txtMinPlayers.getText());
            int maxPlayers = Integer.parseInt(txtMaxPlayers.getText());
            int averageDuration = Integer.parseInt(txtAvgDuration.getText());
            String recommendedAge = txtAge.getText();
            int publicationYear = Integer.parseInt(txtPubYear.getText());
            Difficulty difficulty = cmbDifficulty.getValue();
            int ranking = Integer.parseInt(txtRanking.getText());
            String mechanics = txtMechanics.getText();
            Designer designer1 = cmbDesigner1.getValue();
            Illustrator illustrator1 = cmbIllustrator1.getValue();
            Publisher publisher1 = cmbPublisher1.getValue();

            Designer designer2 = ((cmbDesigner2.getValue() == null) ? null : cmbDesigner2.getValue());
            Designer designer3 = ((cmbDesigner3.getValue() == null) ? null : cmbDesigner3.getValue());
            Publisher publisher2 = ((cmbPublisher2.getValue() == null) ? null : cmbPublisher2.getValue());
            Publisher publisher3 = ((cmbPublisher3.getValue() == null) ? null : cmbPublisher3.getValue());
            Illustrator illustrator2 = ((cmbIllustrator2.getValue() == null) ? null : cmbIllustrator2.getValue());
            Illustrator illustrator3 = ((cmbIllustrator3.getValue() == null) ? null : cmbIllustrator3.getValue());
            addOnlyNotNull(designers, designer1, designer2, designer3);
            addOnlyNotNull(illustrators, illustrator1, illustrator2, illustrator3);
            addOnlyNotNull(publishers, publisher1, publisher2, publisher3);

            BoardGame newBoardGame = new BoardGame(name, minPlayers, maxPlayers, averageDuration, recommendedAge, publicationYear, difficulty, ranking, mechanics, designers, illustrators, publishers);
            if (this.boardGameToEdit == null){
                if( BoardGameDAO.addBoardGame(newBoardGame) ){
                    Utils.alert(Alert.AlertType.INFORMATION, "BOARD GAME ADDED", "The board game was added to the database", "The board game with name: "+newBoardGame.getName()+" was added to the database");
                    Stage stage = (Stage) formTitleLabel.getScene().getWindow();
                    stage.close();
                }
            }else {
                if (BoardGameDAO.updateBoardGame(boardGameToEdit, newBoardGame)){
                    updateOthers(boardGameToEdit, newBoardGame);
                    Utils.alert(Alert.AlertType.INFORMATION, "BOARD GAME UPDATED", "The board game was updated to the database", "The board game with name: "+newBoardGame.getName()+" was updated to the database");
                    Stage stage = (Stage) formTitleLabel.getScene().getWindow();
                    stage.close();
                }
            }
        } catch (Exception e) {
            Utils.alert(Alert.AlertType.ERROR,"Error", "There was an error while storing board game on database", "Details: " + e.getMessage());
        }
    }

    private void updateOthers(BoardGame boardGameToEdit, BoardGame newBoardGame) {
        if ( newBoardGame.getDesigners() != null ){
            SecondariesDAO.insertMake(newBoardGame.getDesigners() , boardGameToEdit);
        }

        if ( newBoardGame.getIllustrators() != null ){
            SecondariesDAO.insertDepict(newBoardGame.getIllustrators() , boardGameToEdit);
        }

        if ( newBoardGame.getPublishers() != null ){
            SecondariesDAO.insertProduce(newBoardGame.getPublishers() , boardGameToEdit);
        }
    }

    private <T> void addOnlyNotNull(List<T> list, T t1, T t2, T t3){
        if(t1 != null){
            list.add(t1);
        }
        if(t2 != null){
            list.add(t2);
        }
        if(t3 != null){
            list.add(t3);
        }
    }

    public void closeWindow() {
        Stage stage = (Stage) formTitleLabel.getScene().getWindow();
        stage.close();
    }
}