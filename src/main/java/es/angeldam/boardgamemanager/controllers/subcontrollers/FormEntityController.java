package es.angeldam.boardgamemanager.controllers.subcontrollers;

import es.angeldam.boardgamemanager.dao.DesignerDAO;
import es.angeldam.boardgamemanager.dao.IllustratorDAO;
import es.angeldam.boardgamemanager.dao.PublisherDAO;
import es.angeldam.boardgamemanager.interfaces.Entity;
import es.angeldam.boardgamemanager.model.BoardGame;
import es.angeldam.boardgamemanager.model.Designer;
import es.angeldam.boardgamemanager.model.Illustrator;
import es.angeldam.boardgamemanager.model.Publisher;
import es.angeldam.boardgamemanager.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;

public class FormEntityController {

    @FXML
    public Button btnSave;
    @FXML
    public ComboBox<BoardGame> cmbBoardGame;
    @FXML
    public TextField txtHeadquarters;
    @FXML
    public TextField txtNationality;
    @FXML
    public Label formTitleLabel;
    @FXML
    public TextField txtName;
    @FXML
    public TextField txtAlias;
    @FXML
    public TextField txtBirthYear;
    @FXML
    public TextField txtFoundationYear;
    @FXML
    public Label labelAlias;
    @FXML
    public Label labelBirthYear;
    @FXML
    public Label labelFoundationYear;
    @FXML
    public Label labelNationality;
    @FXML
    public Label labelBoardGame;
    @FXML
    public Label labelHeadquarters;

    private Designer designer;
    private Illustrator illustrator;
    private Publisher publisher;

    @FXML
    public void initialize(Object object) {
        setEntity(object);
        openForm();
        storeEntity();
        hideAll();
        resetEntity();
    }

    private void setEntity(Object object) {
        if (object.getClass() == Designer.class) {
            this.designer = (Designer) object;
        } else if (object.getClass() == Illustrator.class) {
            this.illustrator = (Illustrator) object;
        } else if (object.getClass() == Publisher.class) {
            this.publisher = (Publisher) object;
        }
    }

    private void resetEntity() {
        this.designer = null;
        this.illustrator = null;
        this.publisher = null;
    }

    @FXML
    private void hideAll() {
        txtHeadquarters.setVisible(false);
    }

    @FXML
    public void update() {
        switch (formTitleLabel.getText()) {
            case "DESIGNER" -> {
                Designer d = new Designer(txtName.getText(), txtAlias.getText(), Integer.parseInt(txtBirthYear.getText()), txtNationality.getText());
                try {
                    DesignerDAO.updateDesigner(this.designer, d);
                } catch (SQLException e) {
                    Utils.alert(Alert.AlertType.ERROR, "DESIGNER DATABASE ERROR", "An error has occurred while editing designer: ", e.getMessage());
                }
            }

            case "ILLUSTRATOR" -> {
                Illustrator i = new Illustrator(txtName.getText(), Integer.parseInt(txtBirthYear.getText()), txtNationality.getText());
                try {
                    IllustratorDAO.updateIllustrator(this.illustrator, i);
                } catch (SQLException e) {
                    Utils.alert(Alert.AlertType.ERROR, "ILLUSTRATOR DATABASE ERROR", "An error has occurred while editing illustrator: ", e.getMessage());
                }
            }

            case "PUBLISHER" -> {
                Publisher p = new Publisher(txtName.getText(), Integer.parseInt(txtFoundationYear.getText()), txtHeadquarters.getText());
                try {
                    PublisherDAO.updatePublisher(this.publisher, p);
                } catch (SQLException e) {
                    Utils.alert(Alert.AlertType.ERROR, "PUBLISHER DATABASE ERROR", "An error has occurred while editing publisher: ", e.getMessage());
                }
            }
            default ->
                    Utils.alert(Alert.AlertType.ERROR, "ERROR WITH FORM", "An error has occurred getting the form: ", "The form didn`t get a valid item");
        }
    }

    @FXML
    public void add() {
        switch (formTitleLabel.getText()) {
            case "DESIGNER" -> {
                Designer d = new Designer(txtName.getText(), txtAlias.getText(), Integer.parseInt(txtBirthYear.getText()), txtNationality.getText());
                try {
                    DesignerDAO.addDesigner(d);
                } catch (SQLException e) {
                    Utils.alert(Alert.AlertType.ERROR, "DESIGNER DATABASE ERROR", "An error has occurred while editing designer: ", e.getMessage());
                }
            }

            case "ILLUSTRATOR" -> {
                Illustrator i = new Illustrator(txtName.getText(), Integer.parseInt(txtBirthYear.getText()), txtNationality.getText());
                try {
                    IllustratorDAO.addIllustrator(i);
                } catch (SQLException e) {
                    Utils.alert(Alert.AlertType.ERROR, "ILLUSTRATOR DATABASE ERROR", "An error has occurred while editing illustrator: ", e.getMessage());
                }
            }

            case "PUBLISHER" -> {
                Publisher p = new Publisher(txtName.getText(), Integer.parseInt(txtFoundationYear.getText()), txtHeadquarters.getText());
                try {
                    PublisherDAO.addPublisher(p);
                } catch (SQLException e) {
                    Utils.alert(Alert.AlertType.ERROR, "PUBLISHER DATABASE ERROR", "An error has occurred while adding publisher: ", e.getMessage());
                }
            }

            default ->
                    Utils.alert(Alert.AlertType.ERROR, "ERROR WITH FORM", "An error has occurred getting the form: ", "The form didn`t get a valid item");
        }
    }

    @FXML
    public void openForm() {
        if (this.designer != null) {
            labelAlias.setVisible(true);
            txtAlias.setVisible(true);
            labelBirthYear.setVisible(true);
            txtBirthYear.setVisible(true);
            labelNationality.setVisible(true);
            txtNationality.setVisible(true);

            formTitleLabel.setText("DESIGNER");

        } else if (this.illustrator != null) {
            labelBirthYear.setVisible(true);
            txtBirthYear.setVisible(true);
            labelNationality.setVisible(true);
            txtNationality.setVisible(true);

            formTitleLabel.setText("ILLUSTRATOR");

        } else if (this.publisher != null) {
            labelFoundationYear.setVisible(true);
            txtFoundationYear.setVisible(true);
            labelHeadquarters.setVisible(true);
            txtHeadquarters.setVisible(true);

            formTitleLabel.setText("PUBLISHER");

        } else {
            Utils.alert(Alert.AlertType.ERROR, "ERROR WITH FORM", "", "");
            Stage stage = (Stage) formTitleLabel.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    public void closeWindow() {
        Stage stage = (Stage) formTitleLabel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void storeEntity() {
        if (this.illustrator == null && this.designer == null && this.publisher == null) {
            add();
        } else {
            update();
        }
    }

    public void addBoardGameToEntity() {

    }
}