package es.angeldam.boardgamemanager;

/**
 * The launcher of the app when making an executable
 */
public class Launcher {
    /**
     * The main method of the launcher is the first method that is executed by the JVM when the jar is opened
     * @param args The arguments which are related to the main
     */
    public static void main(String[] args) {
        javafx.application.Application.launch(BoardGameManagerApplication.class, args);
    }
}
