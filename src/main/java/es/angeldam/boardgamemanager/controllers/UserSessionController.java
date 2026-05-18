package es.angeldam.boardgamemanager.controllers;

import es.angeldam.boardgamemanager.BoardGameManagerApplication;
import es.angeldam.boardgamemanager.dao.UserDAO;
import es.angeldam.boardgamemanager.dataAccess.ConnectionBD;
import es.angeldam.boardgamemanager.model.User;
import es.angeldam.boardgamemanager.utils.UserType;
import es.angeldam.boardgamemanager.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class UserSessionController {
    @FXML public Button logInButton;
    @FXML public PasswordField passwordField;
    @FXML public TextField userField;
    @FXML public Button signUpButton;
    @FXML public Button clearButton;

    @FXML
    public void initialize(){
        loadDB();
    }

    @FXML
    public void logIn(ActionEvent actionEvent) throws IOException {
        User user = null;
        User aux = null;
        if (this.userField.getText().isEmpty() || this.passwordField.getText().isEmpty()) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR EMPTY USER OR PASSWORD", "An error has occurred", "the user or password fields aren't fill up, please complete both.");
        } else {
            try {
                aux = UserDAO.findByName(userField.getText().trim());
                if (aux == null) {
                    Utils.alert(Alert.AlertType.ERROR, "ERROR COULDN'T FIND USER", "An error has occurred related to the user", "The user " + userField.getText().trim() + " \ncouldn't be found on database");
                } else {
                    user = User.redefineInstance(aux.getUserName(), aux.getPassword(), aux.getUserType());
                }

                if (user != null && !user.getPassword().equals(Utils.sha256(passwordField.getText().trim()))) {
                    Utils.alert(Alert.AlertType.ERROR, "ERROR PASSWORD", "The password doesn't match", "the password written on field doesn't correlate to the user password.");
                } else {
                    Stage changedStage = (Stage) logInButton.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(BoardGameManagerApplication.class.getResource("principal-view.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
                    changedStage.setScene(scene);
                    changedStage.setTitle("Board Game Manager");
                }
            } catch (SQLException e) {
                Utils.alert(Alert.AlertType.ERROR, "ERROR USER", "User not found: There aren't users with the user name written", e.getMessage());
            } catch (NoSuchAlgorithmException e1) {
                Utils.alert(Alert.AlertType.ERROR, "ERROR ALGORITHM", "Can't get password through SHA256", e1.getMessage());
            }
        }
    }

    @FXML
    public void signUp(ActionEvent actionEvent) {
        User user = null;
        try {
            user = new User(userField.getText(), Utils.sha256(passwordField.getText()), UserType.USER);
        } catch (NoSuchAlgorithmException e) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR ENCRYPTING", "There was an error while encrypting password", e.getMessage());
        }
        try {
            UserDAO.addUser(user);
            Utils.alert(Alert.AlertType.CONFIRMATION, "ADDED USER TO DATABASE", "The user and password was added to the database", "The user: " + user.getUserName() + " was added successfully");
        } catch (SQLException e) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR STORING USER", "There was an error while storing user on database", e.getMessage());
        }
    }

    public void loadDB() {
        if (ConnectionBD.getConnection() == null) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR CONNECTING DATABASE", "An error has occurred while connecting database", "Contact the administrator to solve the error");
        }
    }

    public void clear(ActionEvent actionEvent) {
        passwordField.clear();
        userField.clear();
    }
}