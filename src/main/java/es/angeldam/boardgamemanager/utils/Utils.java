package es.angeldam.boardgamemanager.utils;

import javafx.scene.control.Alert;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
    public static void alertError(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
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
