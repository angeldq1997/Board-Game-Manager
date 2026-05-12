package es.angeldam.boardgamemanager.dao;

import es.angeldam.boardgamemanager.dataAccess.ConnectionBD;
import es.angeldam.boardgamemanager.model.User;
import es.angeldam.boardgamemanager.utils.UserType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private final static String SQL_FIND_BY_ID = "SELECT * FROM user where userCode =?";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM user where userName =?";
    private final static String SQL_INSERT = "INSERT INTO user (userName, password, userType) VALUES (?,?,?)";
    private final static String SQL_UPDATE = "UPDATE user SET userName =?, password =?, userType =? WHERE userCode = ?";
    private final static String SQL_DELETE = "DELETE FROM user WHERE userCode = ?";

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

    public static boolean updateUser(User newUser, User actualUser) throws SQLException {
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

    public static boolean deleteUserById(int authorCodeToSearch) throws SQLException {
        boolean deleted = false;
        if (findById(authorCodeToSearch) != null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_DELETE)) {
                ps.setInt(1, authorCodeToSearch);
                ps.executeUpdate();
                deleted = true;
            }
        }
        return deleted;
    }
}