package es.angeldam.boardgamemanager.dao;

import es.angeldam.boardgamemanager.dataAccess.ConnectionBD;
import es.angeldam.boardgamemanager.model.Designer;
import es.angeldam.boardgamemanager.model.BoardGame;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DesignerDAO {
    private final static String SQL_ALL = "SELECT * FROM author";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM author where authorCode =?";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM author where name =?";
    private final static String SQL_INSERT = "INSERT INTO author (name, alias, birthDate, nationality) VALUES (?,?,?,?)";
    private final static String SQL_UPDATE = "UPDATE author SET name =?, alias =?, birthDate =?, nationality =? WHERE authorCode = ?";
    private final static String SQL_DELETE = "DELETE FROM author WHERE authorCode = ?";


    public static List<Designer> findAll() throws SQLException {
        Designer designer = null;
        List<Designer> designers = new ArrayList<>();

        try (ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_ALL)) {
            while (rs.next()) {
                int authorCode = rs.getInt("authorCode");
                String name = rs.getString("name");
                String alias = rs.getString("alias");
                Date birthDate = rs.getDate("birthDate");
                String nationality = rs.getString("nationality");
                designer = new Designer(authorCode, name, alias, birthDate, nationality);
                designers.add(designer);
            }
        }
        return designers;
    }

    public static List<Designer> findAllEager() throws SQLException {
        Designer designer = null;
        List<Designer> designers = new ArrayList<>();

        try (ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_ALL)) {
            while (rs.next()) {
                int authorCode = rs.getInt("authorCode");
                String name = rs.getString("name");
                String alias = rs.getString("alias");
                Date birthDate = rs.getDate("birthDate");
                String nationality = rs.getString("nationality");
                List<BoardGame> boardGames = findById(authorCode).getBoardGames();
                designer = new Designer(authorCode, name, alias, birthDate, nationality, boardGames);
                designers.add(designer);
            }
        }
        return designers;
    }

    public static Designer findByIdEager(int authorCodeToSearch) throws SQLException {
        Designer designer = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, authorCodeToSearch);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int authorCode = rs.getInt("authorCode");
                String name = rs.getString("name");
                String alias = rs.getString("alias");
                Date birthDate = rs.getDate("birthDate");
                String nationality = rs.getString("nationality");
                List<BoardGame> boardGames = findById(authorCode).getBoardGames();
                designer = new Designer(authorCode, name, alias, birthDate, nationality);
            }
        }
        return designer;
    }

    private static Designer findByName(String nameToSearch) throws SQLException {
        Designer designer = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_NAME)) {
            ps.setString(1, nameToSearch);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int authorCode = rs.getInt("authorCode");
                String name = rs.getString("name");
                String alias = rs.getString("alias");
                Date birthDate = rs.getDate("birthDate");
                String nationality = rs.getString("nationality");
                designer = new Designer(authorCode, name, alias, birthDate, nationality);
            }
        }
        return designer;
    }

    public static Designer findById(int authorCodeToSearch) throws SQLException {
        Designer designer = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, authorCodeToSearch);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int authorCode = rs.getInt("authorCode");
                String name = rs.getString("name");
                String alias = rs.getString("alias");
                Date birthDate = rs.getDate("birthDate");
                String nationality = rs.getString("nationality");
                designer = new Designer(authorCode, name, alias, birthDate, nationality);
            }
        }
        return designer;
    }

    public static boolean addAuthor(Designer designer) throws SQLException {
        boolean added = false;
        if ((designer != null) && findByName(designer.getName()) == null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1, designer.getName());
                ps.setString(2, designer.getAlias());
                ps.setDate(3, designer.getBirthDate());
                ps.setString(4, designer.getNationality());
                ps.executeUpdate();
                added = true;
            }
        }
        return added;
    }

    public static boolean updateAuthor(Designer newDesigner, Designer actualDesigner) throws SQLException {
        boolean updated = false;
        if ((actualDesigner != null) && (newDesigner != null) && findByName(actualDesigner.getName()) != null && findByName(newDesigner.getName()) == null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE)) {
                ps.setString(1, newDesigner.getName());
                ps.setString(2, newDesigner.getAlias());
                ps.setDate(3, newDesigner.getBirthDate());
                ps.setString(4, newDesigner.getNationality());
                ps.setInt(5, actualDesigner.getCode());
                ps.executeUpdate();
                updated = true;
            }
        }
        return updated;
    }

    public static boolean deleteAuthorById(int authorCodeToSearch) throws SQLException {
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