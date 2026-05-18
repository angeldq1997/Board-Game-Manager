package es.angeldam.boardgamemanager.controllers;

import es.angeldam.boardgamemanager.dao.DesignerDAO;
import es.angeldam.boardgamemanager.dao.BoardGameDAO;
import es.angeldam.boardgamemanager.dao.IllustratorDAO;
import es.angeldam.boardgamemanager.dao.PublisherDAO;
import es.angeldam.boardgamemanager.model.*;
import es.angeldam.boardgamemanager.utils.Difficulty;
import es.angeldam.boardgamemanager.utils.Utils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class FormBoardGameController {
    @FXML
    public Label formTitleLabel;
    @FXML
    public Button btnSave;
    @FXML
    public TextField txtName;
    @FXML
    public TextField txtMinPlayers;
    @FXML
    public TextField txtMaxPlayers;
    @FXML
    public TextField txtAvgDuration;
    @FXML
    public TextField txtAge;
    @FXML
    public TextField txtPubYear;
    @FXML
    public ComboBox<Difficulty> cmbDifficulty;
    @FXML
    public TextField txtRanking;
    @FXML
    public TextField txtMechanics;
    @FXML
    public ComboBox<Designer> cmbDesigner1;
    @FXML
    public ComboBox<Designer> cmbDesigner2;
    @FXML
    public ComboBox<Designer> cmbDesigner3;
    @FXML
    public ComboBox<Illustrator> cmbIllustrator1;
    @FXML
    public ComboBox<Illustrator> cmbIllustrator2;
    @FXML
    public ComboBox<Illustrator> cmbIllustrator3;
    @FXML
    public ComboBox<Publisher> cmbPublisher1;
    @FXML
    public ComboBox<Publisher> cmbPublisher2;
    @FXML
    public ComboBox<Publisher> cmbPublisher3;

    private BoardGame boardGameToEdit;

    @FXML
    public void initialize(BoardGame boardGame) {
        this.boardGameToEdit = boardGame;

        loadDesigners();
        loadIllustrators();
        loadPublishers();
        cmbDifficulty.getItems().addAll(Difficulty.values());
        configComboBoxDesigner();
        configComboBoxIllustrator();
        configComboBoxPublisher();
        configListeners();
        cmbDesigner2.setVisible(false);
        cmbDesigner3.setVisible(false);
        cmbIllustrator2.setVisible(false);
        cmbIllustrator3.setVisible(false);
        cmbPublisher2.setVisible(false);
        cmbPublisher3.setVisible(false);
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
            //TODO: search in middle tables the values of the designer/publisher/illustrator
            cmbDesigner1.setValue(boardGame.getDesigners().getFirst());
            cmbIllustrator1.setValue(boardGame.getIllustrators().getFirst());
            cmbPublisher1.setValue(boardGame.getPublishers().getFirst());

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
        updateSaveButton();
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
        cmbDesigner1.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Designer designer, boolean empty) {
                super.updateItem(designer, empty);
                setText(empty || designer == null ? null : designer.getName());
            }
        });

        cmbDesigner1.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Designer designer, boolean empty) {
                super.updateItem(designer, empty);
                setText(empty || designer == null ? null : designer.getName());
            }
        });

        cmbDesigner2.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Designer designer, boolean empty) {
                super.updateItem(designer, empty);
                setText(empty || designer == null ? null : designer.getName());
            }
        });

        cmbDesigner2.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Designer designer, boolean empty) {
                super.updateItem(designer, empty);
                setText(empty || designer == null ? null : designer.getName());
            }
        });

        cmbDesigner2.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Designer designer, boolean empty) {
                super.updateItem(designer, empty);
                setText(empty || designer == null ? null : designer.getName());
            }
        });

        cmbDesigner2.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Designer designer, boolean empty) {
                super.updateItem(designer, empty);
                setText(empty || designer == null ? null : designer.getName());
            }
        });
    }

    private void configComboBoxIllustrator() {
        cmbIllustrator1.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Illustrator illustrator, boolean empty) {
                super.updateItem(illustrator, empty);
                setText(empty || illustrator == null ? null : illustrator.getName());
            }
        });

        cmbIllustrator1.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Illustrator illustrator, boolean empty) {
                super.updateItem(illustrator, empty);
                setText(empty || illustrator == null ? null : illustrator.getName());
            }
        });

        cmbIllustrator2.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Illustrator illustrator, boolean empty) {
                super.updateItem(illustrator, empty);
                setText(empty || illustrator == null ? null : illustrator.getName());
            }
        });

        cmbIllustrator2.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Illustrator illustrator, boolean empty) {
                super.updateItem(illustrator, empty);
                setText(empty || illustrator == null ? null : illustrator.getName());
            }
        });

        cmbIllustrator3.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Illustrator illustrator, boolean empty) {
                super.updateItem(illustrator, empty);
                setText(empty || illustrator == null ? null : illustrator.getName());
            }
        });

        cmbIllustrator3.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Illustrator illustrator, boolean empty) {
                super.updateItem(illustrator, empty);
                setText(empty || illustrator == null ? null : illustrator.getName());
            }
        });
    }

    private void configComboBoxPublisher() {
        cmbPublisher1.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Publisher publisher, boolean empty) {
                super.updateItem(publisher, empty);
                setText(empty || publisher == null ? null : publisher.getName());
            }
        });

        cmbPublisher1.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Publisher publisher, boolean empty) {
                super.updateItem(publisher, empty);
                setText(empty || publisher == null ? null : publisher.getName());
            }
        });

        cmbPublisher2.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Publisher publisher, boolean empty) {
                super.updateItem(publisher, empty);
                setText(empty || publisher == null ? null : publisher.getName());
            }
        });

        cmbPublisher2.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Publisher publisher, boolean empty) {
                super.updateItem(publisher, empty);
                setText(empty || publisher == null ? null : publisher.getName());
            }
        });

        cmbPublisher3.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Publisher publisher, boolean empty) {
                super.updateItem(publisher, empty);
                setText(empty || publisher == null ? null : publisher.getName());
            }
        });

        cmbPublisher3.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Publisher publisher, boolean empty) {
                super.updateItem(publisher, empty);
                setText(empty || publisher == null ? null : publisher.getName());
            }
        });
    }

    private void loadDesigners() {
        try {
            List<Designer> designers = DesignerDAO.findAll();
            designers.sort((a1, a2) -> a1.getName().compareTo(a2.getName()));
            cmbDesigner1.setItems(FXCollections.observableArrayList(designers));
        } catch (Exception e) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR DESIGNER", "Error loading designers", "Couldn't get designers from the database: " + e.getMessage());
        }
    }

    private void loadIllustrators() {
        try {
            List<Illustrator> illustrators = IllustratorDAO.findAll();
            illustrators.sort((i1, i2) -> i1.getName().compareTo(i2.getName()));
            cmbIllustrator1.setItems(FXCollections.observableArrayList(illustrators));
        } catch (Exception e) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR ILLUSTRATOR", "Error loading illustrators", "Couldn't get illustrators from the database: " + e.getMessage());
        }
    }

    private void loadPublishers() {
        try {
            List<Publisher> publishers = PublisherDAO.findAll();
            publishers.sort((p1, p2) -> p1.getName().compareTo(p2.getName()));
            cmbPublisher1.setItems(FXCollections.observableArrayList(publishers));
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
            if(comboBoxToCheck.getValue() != null){
                comboBoxToShow.setVisible(true);
                comboBoxToShow.setDisable(false);
                comboBoxToShow.getItems().remove(comboBoxToCheck.getValue());
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

    private void updateSaveButton() {
        btnSave.setDisable(!validData());
    }

    public void storeBoardGame(ActionEvent actionEvent) {
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
                    Utils.alert(Alert.AlertType.INFORMATION, "BOARD GAME UPDATED", "The board game was updated to the database", "The board game with name: "+newBoardGame.getName()+" was updated to the database");
                    Stage stage = (Stage) formTitleLabel.getScene().getWindow();
                    stage.close();
                }
            }
        } catch (Exception e) {
            Utils.alert(Alert.AlertType.ERROR,"Error", "There was an error while storing board game on database", "Details: " + e.getMessage());
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

    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) formTitleLabel.getScene().getWindow();
        stage.close();
    }
}