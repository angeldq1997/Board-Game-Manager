package es.angeldam.boardgamemanager.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

/**
 * Class that groups the static methods that are used by classes, utilities that could be useful
 */
public class Utils {
    /**
     * Static method that show a window to the user with different layouts
     * @param alertType The type of alert (or window) that will be displayed, each type has differences between each other
     * @param title title of the alert will be displayed as the title of window
     * @param header header of the alert, it has a summary of the alert itself
     * @param content content that will show to the user more info about what's happening
     * @return an Optional buttonType which could be interpreted as an answer when the type is a confirmation, waiting for the user to send OK or to cancel
     */
    public static Optional<ButtonType> alert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert.showAndWait();
    }

    /**
     * Static method that encrypt a password with the algorithm SHA-256 with the purpose of storing it in a database increasing security
     * @param password Password that will be encrypted
     * @return The password already encrypted
     * @throws NoSuchAlgorithmException if no Provider supports a MessageDigestSpi implementation for SHA-256
     */
    public static String sha256(String password) throws NoSuchAlgorithmException {
        MessageDigest sha = null;
        sha = MessageDigest.getInstance("SHA-256");
        byte[] digest = sha.digest(password.getBytes());

        StringBuilder hexString = new StringBuilder();

        for (byte b : digest) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}
