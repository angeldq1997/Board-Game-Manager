package es.angeldam.boardgamemanager.model;

import es.angeldam.boardgamemanager.utils.UserType;

/**
 * Class that store the information structure of a User, it follows a singleton pattern
 */
public class User {
    private static User instance;

    private int userCode;
    private String password;
    private String userName;
    private UserType userType;

    /**
     * Full-equip constructor of a User object
     * @param userCode code of the user that relates its position to the database
     * @param userName username which allows a user to have a nick for the database
     * @param password password that a user uses to enter to the app
     * @param userType the type of user that gives privileges of administrator or normal user
     */
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

    /**
     * Method of the
     * @return
     */
    public static User getInstance() {
        return instance;
    }

    public static User redefineInstance(String userName, String password, UserType userType) {
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