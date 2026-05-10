package es.angeldam.boardgamemanager.model;

public class User{
    private int userCode;
    private String userName;
    private String password;

    public User(String userName, String password, int userCode) {
        this.userName = userName;
        this.password = password;
        this.userCode = userCode;
    }
}
