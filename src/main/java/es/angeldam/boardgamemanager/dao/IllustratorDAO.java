package es.angeldam.boardgamemanager.dao;

import es.angeldam.boardgamemanager.dataAccess.ConnectionBD;
import es.angeldam.boardgamemanager.model.BoardGame;
import es.angeldam.boardgamemanager.model.Illustrator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that manages the data access of an illustrator (as an object), this has the objective to do the operations to get the data from the database and bring it to Java.
 * This makes it usable for the methods and systems that requires it.
 */
public class IllustratorDAO {
    private final static String SQL_ALL = "SELECT * FROM illustrator";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM illustrator where illustratorCode =?";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM illustrator where name =?";
    private final static String SQL_INSERT = "INSERT INTO illustrator (name, birthYear, nationality) VALUES (?,?,?)";
    private final static String SQL_UPDATE = "UPDATE illustrator SET name =?, birthYear =?, nationality =? WHERE illustratorCode = ?";
    private final static String SQL_DELETE = "DELETE FROM illustrator WHERE illustratorCode = ?";


    /**
     * Static method that retrieve all illustrator data from the database including board games
     * @return the illustrator List with their board games
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called on a closed result set
     */
    public static List<Illustrator> findAllEager() throws SQLException {
        Illustrator illustrator = null;
        List<Illustrator> illustrators = new ArrayList<>();
        String boardGameCodes = null;
        List<BoardGame> boardGames = new ArrayList<>();

        ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_ALL);
        while (rs.next()) {
            int illustratorCode = rs.getInt("illustratorCode");
            String name = rs.getString("name");
            int birthYear = rs.getInt("birthYear");
            String nationality = rs.getString("nationality");
            boardGameCodes = rs.getString("boardGameCodes");
            if ( boardGameCodes != null ) {
                String[] boardGameCodeList = boardGameCodes.split(",");
                for (String boardGameCode : boardGameCodeList) {
                    boardGames.add(BoardGameDAO.findById(Integer.parseInt(boardGameCode)));
                }
                illustrator = new Illustrator(illustratorCode, name, birthYear, nationality, boardGames);
            }else{
                illustrator = new Illustrator(illustratorCode, name, birthYear, nationality);
            }
            illustrators.add(illustrator);
        }
        return illustrators;
    }

    /**
     * Static method that retrieve all illustrator data from the database including board games
     * @return the illustrator List without their board games
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called on a closed result set
     */
    public static List<Illustrator> findAll() throws SQLException {
        Illustrator illustrator = null;
        List<Illustrator> illustrators = new ArrayList<>();

        ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_ALL);
        while (rs.next()) {
            int illustratorCode = rs.getInt("illustratorCode");
            String name = rs.getString("name");
            int birthYear = rs.getInt("birthYear");
            String nationality = rs.getString("nationality");
            illustrator = new Illustrator(illustratorCode, name, birthYear, nationality);
            illustrators.add(illustrator);
        }
        return illustrators;
    }

    /**
     * Static method that find an illustrator by its code
     * @param illustratorCodeToSearch code of the illustrator to find
     * @return the illustrator with the data from database or NULL when cannot find it
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called on a closed result set
     */
    public static Illustrator findById(int illustratorCodeToSearch) throws SQLException {
        Illustrator illustrator = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, illustratorCodeToSearch);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int illustratorCode = rs.getInt("illustratorCode");
                String name = rs.getString("name");
                int birthYear = rs.getInt("birthYear");
                String nationality = rs.getString("nationality");
                illustrator = new Illustrator(illustratorCode, name, birthYear, nationality);
            }
        }
        return illustrator;
    }

    /**
     * Static method that find an illustrator by its name
     * @param nameToSearch name of the illustrator to find
     * @return the illustrator if found without an error or NULL if there was an exception in the process/couldn't be found
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called on a closed result set
     */
    private static Illustrator findByName(String nameToSearch) throws SQLException {
        Illustrator illustrator = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_NAME)) {
            ps.setString(1, nameToSearch);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int illustratorCode = rs.getInt("illustratorCode");
                String name = rs.getString("name");
                int birthYear = rs.getInt("birthYear");
                String nationality = rs.getString("nationality");
                illustrator = new Illustrator(illustratorCode, name, birthYear, nationality);
            }
        }
        return illustrator;
    }

    /**
     * Static method that adds an illustrator to the database
     * @param illustrator the illustrator with the info to be stored in the database
     * @return True if added without an error or FALSE if there was an exception in the process/couldn't be added
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called on a closed result set
     */
    public static boolean addIllustrator(Illustrator illustrator) throws SQLException {
        boolean added = false;
        if ((illustrator != null) && findByName(illustrator.getName()) == null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1, illustrator.getName());
                ps.setInt(2, illustrator.getBirthYear());
                ps.setString(3, illustrator.getNationality());
                ps.executeUpdate();
                added = true;
            }
        }
        return added;
    }

    /**
     * Static method that updates an illustrator of the database
     * @param actualIllustrator the illustrator with the info to be extracted from the database
     * @param newIllustrator the illustrator with the info to be replaced in the database
     * @return True if updated without an error or FALSE if there was an exception in the process/couldn't be updated
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called on a closed result set
     */
    public static boolean updateIllustrator(Illustrator actualIllustrator, Illustrator newIllustrator) throws SQLException {
        boolean updated = false;
        if ((actualIllustrator != null) && (newIllustrator != null) && findByName(actualIllustrator.getName()) != null && findByName(newIllustrator.getName()) == null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE)) {
                ps.setString(1, newIllustrator.getName());
                ps.setInt(2, newIllustrator.getBirthYear());
                ps.setString(3, newIllustrator.getNationality());
                ps.setInt(4, actualIllustrator.getCode());
                ps.executeUpdate();
                updated = true;
            }
        }
        return updated;
    }

    /**
     * Static method that erase an illustrator of the database
     * @param illustratorCode the illustrator with the info to be erased from the database
     * @return True if erased without an error or FALSE if there was an exception in the process/couldn't be updated
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called on a closed result set
     */
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