package es.angeldam.boardgamemanager.model;

import com.mysql.cj.protocol.a.authentication.Sha256PasswordPlugin;

import java.security.MessageDigest;

public class User implements {
    public static Sha256PasswordPlugin plugin;
    private int userCode;
    private String userName;
    private String password;

    public User(String userName, String password, int userCode) {
        this.userName = userName;
        this.password = password;
        this.userCode = userCode;
    }

    public boolean verifyPassword(){
        plugin.setAuthenticationParameters(userName, password);
        MessageDigest.getInstance("SHA-256");
    }
}
