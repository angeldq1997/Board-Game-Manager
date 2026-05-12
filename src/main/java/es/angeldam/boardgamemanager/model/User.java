package es.angeldam.boardgamemanager.model;

import es.angeldam.boardgamemanager.utils.UserType;

public class User{
    private static User instance;

    private int userCode;
    private String password;
    private String userName;
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

    public static User getInstance() {
        return instance;
    }

    public static User redefineInstance(String userName, String password, UserType userType){
        instance = new User(userName, password, userType);
        return instance;
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