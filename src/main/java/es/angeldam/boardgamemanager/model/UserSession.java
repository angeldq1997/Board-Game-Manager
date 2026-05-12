package es.angeldam.boardgamemanager.model;

import es.angeldam.boardgamemanager.utils.UserType;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserSession {
    private static UserSession instance;

    private String userName;
    private UserType userType;

    private UserSession(String userName, UserType userType) {
        this.userName = userName;
        this.userType = userType;
    }

    public static UserSession getInstance(String userName, UserType userType) {
        if (instance == null) {
            instance = new UserSession(userName, userType);
        }
        return instance;
    }

    public String getUserName() {
        return this.userName;
    }

    public UserType getUserType() {
        return userType;
    }

    public void cleanUserSession() {
        userName = "";
        userType = UserType.USER;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "userName:" + userName +
                ", userType:" + userType;
    }
}