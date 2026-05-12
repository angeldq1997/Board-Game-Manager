package es.angeldam.boardgamemanager.model;

import es.angeldam.boardgamemanager.utils.UserType;

public class User{
    private int userCode;
    private String userName;
    private String password;
    private UserType userType;

    public User(int userCode, String userName, String password, UserType userType) {
        this.userCode = userCode;
        this.userName = userName;
        this.password = password;
        this.userType = userType;
    }

    public User(String userName, String password, UserType userType) {
        this.userName = userName;
        this.password = password;
        this.userType = userType;
    }

    public int getUserCode() {
        return userCode;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public UserType getUserType() {
        return userType;
    }
}