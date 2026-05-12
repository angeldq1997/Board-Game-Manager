package es.angeldam.boardgamemanager.controllers;

import es.angeldam.boardgamemanager.BoardGameManagerApplication;
import es.angeldam.boardgamemanager.dao.UserDAO;
import es.angeldam.boardgamemanager.dataAccess.ConnectionBD;
import es.angeldam.boardgamemanager.model.User;
import es.angeldam.boardgamemanager.model.UserSession;
import es.angeldam.boardgamemanager.utils.UserType;
import es.angeldam.boardgamemanager.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class UserSessionController {
    @FXML
    public Button logInButton;
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField userField;
    @FXML
    public Button signUpButton;
    public Button clearButton;

    @FXML
    public void initialize() {
        loadDB();
    }

    @FXML
    public void logIn(ActionEvent actionEvent) {
        User user = null;
        if (this.userField.getText().isEmpty() || this.passwordField.getText().isEmpty()) {
            Utils.alertError("ERROR EMPTY USER OR PASSWORD", "An error has occurred", "the user or password fields aren't fill up, please complete both.");
        } else {
            try {
                user = UserDAO.findByName(userField.getText());
                if (user != null && !user.getPassword().equals(Utils.sha256(passwordField.getText().trim()))) {
                    Utils.alertError("ERROR PASSWORD", "The password doesn't match", "the password written on field doesn't correlate to the user password.");
                } else {
                    FXMLLoader fxmlLoader = new FXMLLoader(BoardGameManagerApplication.class.getResource("principal-view.fxml"));
                    Stage changedStage = (Stage) logInButton.getScene().getWindow();
                    Scene scene = null;
                    scene = new Scene(fxmlLoader.load(), 250, 250);
                    changedStage.setTitle("Board Game Manager");
                    changedStage.setScene(scene);
                }
            } catch (Exception e) {
                Utils.alertError("ERROR USER", "User not found: There aren't users with the user name written", e.getMessage());
            }
        }
    }

    @FXML
    public void signUp(ActionEvent actionEvent) {
        User user = null;
        try {
            user = new User(userField.getText(), Utils.sha256(passwordField.getText()), UserType.USER);
            System.out.println(user.getPassword());
        } catch (NoSuchAlgorithmException e) {
            Utils.alertError("ERROR ENCRYPTING", "There was an error while encrypting password", "This password isn´t valid, please try again");
        }
        try {
            UserDAO.addUser(user);
        } catch (SQLException e) {
            Utils.alertError("ERROR STORING USER", "There was an error while storing user on database", e.getMessage());
        }
    }

    public void loadDB() {
        if (ConnectionBD.getConnection() == null) {
            Utils.alertError("ERROR CONNECTING DATABASE", "An error has occured while connnecting database", "Contact the administrator to solve the error");
        }
    }

    public void clear(ActionEvent actionEvent) {
        passwordField.clear();
        userField.clear();
    }
}