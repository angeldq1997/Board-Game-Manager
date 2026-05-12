package es.angeldam.boardgamemanager.dao;

import es.angeldam.boardgamemanager.dataAccess.ConnectionBD;
import es.angeldam.boardgamemanager.model.BoardGame;
import es.angeldam.boardgamemanager.model.Illustrator;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class IllustratorDAO {
    private final static String SQL_ALL = "SELECT * FROM illustrator";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM illustrator where illustratorCode =?";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM illustrator where name =?";
    private final static String SQL_INSERT = "INSERT INTO illustrator (name, birthDate, nationality) VALUES (?,?,?)";
    private final static String SQL_UPDATE = "UPDATE illustrator SET name =?, birthDate =?, nationality =? WHERE illustratorCode = ?";
    private final static String SQL_DELETE = "DELETE FROM illustrator WHERE illustratorCode = ?";


    public static HashSet<Illustrator> findAll() throws SQLException {
        Illustrator illustrator = null;
        HashSet<Illustrator> illustrators = new HashSet<>();

        ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_ALL);
        while (rs.next()) {
            int illustratorCode = rs.getInt("illustratorCode");
            String name = rs.getString("name");
            Date birthDate = rs.getDate("birthDate");
            String nationality = rs.getString("nationality");
            illustrator = new Illustrator(illustratorCode, name, birthDate, nationality);
            illustrators.add(illustrator);
        }
        return illustrators;
    }

    public static HashSet<Illustrator> findAllEager() throws SQLException {
        Illustrator illustrator = null;
        HashSet<Illustrator> illustrators = new HashSet<>();

        ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_ALL);
        while (rs.next()) {
            int illustratorCode = rs.getInt("illustratorCode");
            String name = rs.getString("name");
            Date birthDate = rs.getDate("birthDate");
            String nationality = rs.getString("nationality");
            HashSet<BoardGame> boardGames = findById(illustratorCode).getBoardGames();
            illustrator = new Illustrator(illustratorCode, name, birthDate, nationality, boardGames);
            illustrators.add(illustrator);
        }
        return illustrators;
    }

    public static Illustrator findById(int illustratorCodeToSearch) throws SQLException {
        Illustrator illustrator = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, illustratorCodeToSearch);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int illustratorCode = rs.getInt("illustratorCode");
                String name = rs.getString("name");
                Date birthDate = rs.getDate("birthDate");
                String nationality = rs.getString("nationality");
                illustrator = new Illustrator(illustratorCode, name, birthDate, nationality);
            }
        }
        return illustrator;
    }

    private static Illustrator findByName(String nameToSearch) throws SQLException {
        Illustrator illustrator = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_NAME)) {
            ps.setString(1, nameToSearch);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int illustratorCode = rs.getInt("illustratorCode");
                String name = rs.getString("name");
                Date birthDate = rs.getDate("birthDate");
                String nationality = rs.getString("nationality");
                illustrator = new Illustrator(illustratorCode, name, birthDate, nationality);
            }
        }
        return illustrator;
    }

    public static boolean addIllustrator(Illustrator illustrator) throws SQLException {
        boolean added = false;
        if ((illustrator != null) && findByName(illustrator.getName()) == null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1, illustrator.getName());
                ps.setDate(2, illustrator.getBirthDate());
                ps.setString(3, illustrator.getNationality());
                ps.executeUpdate();
                added = true;
            }
        }
        return added;
    }

    public static boolean updateIllustrator(Illustrator actualIllustrator, Illustrator newIllustrator) throws SQLException {
        boolean updated = false;
        if ((actualIllustrator != null) && (newIllustrator != null) && findByName(actualIllustrator.getName()) != null && findByName(newIllustrator.getName()) == null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE)) {
                ps.setString(1, newIllustrator.getName());
                ps.setDate(2, newIllustrator.getBirthDate());
                ps.setString(3, newIllustrator.getNationality());
                ps.setInt(4, actualIllustrator.getCode());
                ps.executeUpdate();
                updated = true;
            }
        }
        return updated;
    }

    public static boolean deleteIllustratorById(int illustratorCode) throws SQLException {
        boolean deleted = false;
        if (findById(illustratorCode) != null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_DELETE)) {
                ps.setInt(1, illustratorCode);
                ps.executeUpdate();
                deleted = true;
            }
        }
        return deleted;
    }

}