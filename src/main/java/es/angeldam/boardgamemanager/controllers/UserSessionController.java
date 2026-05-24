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

/**
 * Controller of user session that manages the login, log out, sign in
 */
public class UserSessionController {
    @FXML public Button logInButton;
    @FXML public PasswordField passwordField;
    @FXML public TextField userField;
    @FXML public Button signUpButton;
    @FXML public Button clearButton;
    @FXML public Button logOutButton;

    private Stage stage;
    /**
     * Method that initialize the controller loading the database
     */
    @FXML
    public void initialize(){
        loadDB();
        addListeners();
    }

    /**
     * Method that manages the login of the user checking the username, password and initializing the principal view of the app
     * @throws IOException if an error occurs during loading the view
     */
    @FXML
    public void logIn() throws IOException {
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
                    this.stage = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader(BoardGameManagerApplication.class.getResource("principal-view.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
                    this.stage.setScene(scene);
                    this.stage.setTitle("Board Game Manager");
                    signUpButton.setVisible(false);
                    signUpButton.setDisable(true);
                    logInButton.setVisible(false);
                    logInButton.setDisable(true);
                    logOutButton.setVisible(true);
                    this.stage.showAndWait();
                    clear();
                }
            } catch (SQLException e) {
                Utils.alert(Alert.AlertType.ERROR, "ERROR USER", "User not found: There aren't users with the user name written", e.getMessage());
            } catch (NoSuchAlgorithmException e1) {
                Utils.alert(Alert.AlertType.ERROR, "ERROR ALGORITHM", "Can't get password through SHA256", e1.getMessage());
            }
        }
    }

    /**
     * Method that manages the signup of the user with his/her password and username (taken from the text fields) storing the new user into the database
     */
    @FXML
    public void signUp( ) {
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

    /**
     * Method that uses the connection database singleton to load the connection properties and URL
     */
    public void loadDB() {
        if (ConnectionBD.getConnection() == null) {
            Utils.alert(Alert.AlertType.ERROR, "ERROR CONNECTING DATABASE", "An error has occurred while connecting database", "Contact the administrator to solve the error");
        }
    }

    /**
     * Method that add listeners to the text fields username and password to constrain to the desired restrictions such as size
     */
    private void addListeners(){
        addListenerLimitedSize(userField, 30);
        addListenerLimitedSize(passwordField, 30);
        userField.textProperty().addListener((observable, oldValue, newValue) -> updateButtons());
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> updateButtons());
    }

    /**
     * Method that add a listener to a text field with a max number of characters
     * @param textField the concrete text field whose listener will be put
     * @param max max number of characters that will be allowed to the text field
     */
    private void addListenerLimitedSize(TextField textField, int max) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > max) {
                String copy = newValue.substring(0, max);
                textField.setText(copy);
            }
        });
    }

    /**
     * Method that update the buttons login and signup to enable them when the text fields are valid
     */
    private void updateButtons(){
        logInButton.setDisable(!validTxtFields());
        signUpButton.setDisable(!validTxtFields());
    }

    /**
     * Method that verify if the text fields username and password are empty or not
     * @return True when both are filled up and False when both are empty
     */
    private boolean validTxtFields(){
        return !(passwordField.getText().isEmpty() && userField.getText().isEmpty());
    }

    /**
     * Method that clears the txt fields
     */
    @FXML
    public void clear( ) {
        passwordField.clear();
        userField.clear();
    }

    /**
     * Method that logs out the current session
     */
    @FXML
    public void logOut( ) {
        User.redefineInstance(null, null, UserType.USER);
        signUpButton.setVisible(true);
        signUpButton.setDisable(false);
        logInButton.setVisible(true);
        logInButton.setDisable(false);
        logOutButton.setVisible(false);
        this.stage.close();
    }

    /**
     * Method that shows info about the program
     */
    @FXML
    public void about( ) {
            Utils.alert(Alert.AlertType.INFORMATION,"About this app", "Board Game Manager - CRUD board games", "Author: Angel\nVersión: 0.5\nTechnologies: JavaFX + JDBC");
    }
}