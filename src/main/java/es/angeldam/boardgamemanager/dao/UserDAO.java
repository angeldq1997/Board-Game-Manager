package es.angeldam.boardgamemanager.dao;

import es.angeldam.boardgamemanager.dataAccess.ConnectionBD;
import es.angeldam.boardgamemanager.model.User;
import es.angeldam.boardgamemanager.utils.UserType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that manages the data access of a user (as an object), this has the objective to do the operations to get the data from the database and bring it to Java.
 * This makes it usable for the methods and systems that requires it.
 */
public class UserDAO {
    private final static String SQL_FIND_BY_ID = "SELECT * FROM user where userCode =?";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM user where userName =?";
    private final static String SQL_INSERT = "INSERT INTO user (userName, password, userType) VALUES (?,?,?)";
    private final static String SQL_UPDATE = "UPDATE user SET userName =?, password =?, userType =? WHERE userCode = ?";
    private final static String SQL_DELETE = "DELETE FROM user WHERE userCode = ?";


    /**
     * Static method that retrieve the user which code is received by the method
     * @param userCodeToSearch the user code, which will be searched on the database
     * @return the user found with his/her password, type and username or NULL if it couldn't find it
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called on a closed result set
     */
    public static User findById(int userCodeToSearch) throws SQLException {
        User user = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, userCodeToSearch);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int userCode = rs.getInt("userCode");
                String userName = rs.getString("userName");
                String password = rs.getString("password");
                UserType userType = (UserType) rs.getObject("userType");
                user = new User(userCode, userName, password, userType);
            }
        }
        return user;
    }

    /**
     * Static method that retrieve the user which name is received by the method
     * @param userNameToSearch the username, which will be searched on the database
     * @return the user found with his/her password, type and username or NULL if it couldn't find it
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called on a closed result set
     */
    public static User findByName(String userNameToSearch) throws SQLException {
        User user = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_NAME)) {
            ps.setString(1, userNameToSearch);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int userCode = rs.getInt("userCode");
                String userName = rs.getString("userName");
                String password = rs.getString("password");
                UserType userType = UserType.valueOf(rs.getString("userType"));
                user = new User(userCode, userName, password, userType);
            }
        }
        return user;
    }

    /**
     * Static method that send the user data to the database to be stored
     * @param user the user which data will be stored on the database
     * @return True if the user was added to the database or False it couldn't be added
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called on a closed result set
     */
    public static boolean addUser(User user) throws SQLException {
        boolean added = false;
        if ((user != null) && findById(user.getUserCode()) == null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1, user.getUserName());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getUserType().name());
                ps.executeUpdate();
                added = true;
            }
        }
        return added;
    }

    /**
     * Static method that send the user data to the database to be stored
     * @param actualUser the user which data will be replaced on the database
     * @param newUser the user which data will be put on the database
     * @return True if the user was updated to the database or False it couldn't be updated
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called on a closed result set
     */
    public static boolean updateUser(User actualUser, User newUser) throws SQLException {
        boolean updated = false;
        if ((actualUser != null) && (newUser != null) && findById(actualUser.getUserCode()) != null && findById(newUser.getUserCode()) == null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE)) {
                ps.setString(1, newUser.getUserName());
                ps.setString(2, newUser.getPassword());
                ps.setObject(3, newUser.getUserType());
                ps.setInt(4, actualUser.getUserCode());
                ps.executeUpdate();
                updated = true;
            }
        }
        return updated;
    }

    /**
     * Static method that send the user data to the database to be erased
     * @param userCodeToSearch the user code for identifying the user
     * @return True if the user was erased from the database or False it couldn't be erased
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called on a closed result set
     */
    public static boolean deleteUserById(int userCodeToSearch) throws SQLException {
        boolean deleted = false;
        if (findById(userCodeToSearch) != null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_DELETE)) {
                ps.setInt(1, userCodeToSearch);
                ps.executeUpdate();
                deleted = true;
            }
        }
        return deleted;
    }
}