package es.angeldam.boardgamemanager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BoardGameManagerApplication extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BoardGameManagerApplication.class.getResource("userSession-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 250, 270);
        stage.setTitle("Board Game Manager: User manager");
        stage.setScene(scene);
        stage.show();
    }
}