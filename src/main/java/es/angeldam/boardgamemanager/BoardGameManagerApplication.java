package es.angeldam.boardgamemanager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class that extends from a javafx app, it has the start point of the app
 */
public class BoardGameManagerApplication extends javafx.application.Application {
    /**
     * Method that starts the app with a stage window
     * @param stage The stage which the program starts at
     * @throws IOException An exception that signals that an Input/Output operation has failed
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BoardGameManagerApplication.class.getResource("userSession-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 300);
        stage.setTitle("Board Game Manager: User manager");
        stage.setScene(scene);
        stage.show();
    }
}