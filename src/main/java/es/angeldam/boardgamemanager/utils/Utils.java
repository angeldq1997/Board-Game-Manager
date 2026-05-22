package es.angeldam.boardgamemanager.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

/**
 *
 */
public class Utils {
    public static Optional<ButtonType> alert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert.showAndWait();
    }

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
