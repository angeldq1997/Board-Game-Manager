package es.angeldam.boardgamemanager.dao;

import es.angeldam.boardgamemanager.dataAccess.ConnectionBD;
import es.angeldam.boardgamemanager.model.Designer;
import es.angeldam.boardgamemanager.model.BoardGame;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that manages the data access of a designer (as an object), this has the objective to do the operations to get the data from the database and bring it to Java.
 * This makes it usable for the methods and systems that requires it.
 */
public class DesignerDAO {
    private final static String SQL_ALL = "SELECT des.* , (SELECT group_concat(boardGameCode) FROM MAKE WHERE designerCode = des.designerCode ) boardGameCodes FROM designer des; ";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM designer where designerCode =?";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM designer where name =?";
    private final static String SQL_INSERT = "INSERT INTO designer (name, alias, birthYear, nationality) VALUES (?,?,?,?)";
    private final static String SQL_UPDATE = "UPDATE designer SET name =?, alias =?, birthYear =?, nationality =? WHERE designerCode = ?";
    private final static String SQL_DELETE = "DELETE FROM designer WHERE designerCode = ?";

    /**
     * Static method that retrieve all designer data from the database including board games
     * @return the designer List with their board games
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called on a closed result set
     */
    public static List<Designer> findAll() throws SQLException {
        Designer designer = null;
        List<Designer> designers = new ArrayList<>();
        String boardGameCodes = null;
        List<BoardGame> boardGames = new ArrayList<>();

        try (ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_ALL)) {
            while (rs.next()) {
                int designerCode = rs.getInt("designerCode");
                String name = rs.getString("name");
                String alias = rs.getString("alias");
                int birthYear = rs.getInt("birthYear");
                String nationality = rs.getString("nationality");

                boardGameCodes = rs.getString("boardGameCodes");
                if (boardGameCodes != null) {
                    String[] boardGameCodeList = boardGameCodes.split(",");
                    for (String boardGameCode : boardGameCodeList) {
                        boardGames.add(BoardGameDAO.findById(Integer.parseInt(boardGameCode)));
                    }
                }
                designer = new Designer(designerCode, name, alias, birthYear, nationality, boardGames);
                designers.add(designer);
            }
        }
        return designers;
    }

    /**
     * Static method that find a designer by its code
     * @param designerCodeToSearch code of the designer to find
     * @return the designer with the data from database or NULL when cannot find it
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called on a closed result set
     */
    public static Designer findByIdEager(int designerCodeToSearch) throws SQLException {
        Designer designer = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, designerCodeToSearch);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int designerCode = rs.getInt("designerCode");
                String name = rs.getString("name");
                String alias = rs.getString("alias");
                int birthYear = rs.getInt("birthYear");
                String nationality = rs.getString("nationality");
                List<BoardGame> boardGames = findById(designerCode).getBoardGames();
                designer = new Designer(designerCode, name, alias, birthYear, nationality, boardGames);
            }
        }
        return designer;
    }

    /**
     * Static method that find a designer by its name
     * @param nameToSearch name of the designer to find
     * @return NULL when cannot find the designer OR the designer with the data from database
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called on a closed result set
     */
    private static Designer findByName(String nameToSearch) throws SQLException {
        Designer designer = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_NAME)) {
            ps.setString(1, nameToSearch);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int designerCode = rs.getInt("designerCode");
                String name = rs.getString("name");
                String alias = rs.getString("alias");
                int birthYear = rs.getInt("birthYear");
                String nationality = rs.getString("nationality");
                designer = new Designer(designerCode, name, alias, birthYear, nationality);
            }
        }
        return designer;
    }

    /**
     * Static method that find a designer by its code
     * @param designerCodeToSearch code of the designer to find
     * @return the designer if found without an error or NULL if there was an exception in the process/couldn't be found
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called on a closed result set
     */
    public static Designer findById(int designerCodeToSearch) throws SQLException {
        Designer designer = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, designerCodeToSearch);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int designerCode = rs.getInt("designerCode");
                String name = rs.getString("name");
                String alias = rs.getString("alias");
                int birthYear = rs.getInt("birthYear");
                String nationality = rs.getString("nationality");
                designer = new Designer(designerCode, name, alias, birthYear, nationality);
            }
        }
        return designer;
    }

    /**
     * Static method that adds a designer to the database
     * @param designer the designer with the info to be stored in the database
     * @return True if the designer was added without a problem or False if there was an exception in the process
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called on a closed result set
     */
    public static boolean addDesigner(Designer designer) throws SQLException {
        boolean added = false;
        if ((designer != null) && findByName(designer.getName()) == null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1, designer.getName());
                ps.setString(2, designer.getAlias());
                ps.setInt(3, designer.getBirthYear());
                ps.setString(4, designer.getNationality());
                ps.executeUpdate();
                added = true;
            }
        }
        return added;
    }

    /**
     * Static method that adds a designer to the database
     * @param actualDesigner the designer with the info to be updated in the database
     * @param newDesigner the designer with the info to be updated in the database
     * @return True if the designer was updated without a problem or False if there was an exception in the process
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called on a closed result set
     */
    public static boolean updateDesigner(Designer actualDesigner, Designer newDesigner) throws SQLException {
        boolean updated = false;
        if ((actualDesigner != null) && (newDesigner != null) && findById(actualDesigner.getCode()) != null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE)) {
                ps.setString(1, newDesigner.getName());
                ps.setString(2, newDesigner.getAlias());
                ps.setInt(3, newDesigner.getBirthYear());
                ps.setString(4, newDesigner.getNationality());
                ps.setInt(5, actualDesigner.getCode());
                ps.executeUpdate();
                updated = true;
            }
        }
        return updated;
    }

    /**
     * Static method that erase a designer from the database
     * @param designerCodeToSearch the designer code whom will be erased
     * @return True if the designer was erased without a problem or False if there was an exception in the process
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called on a closed result set
     */
    public static boolean deleteDesignerById(int designerCodeToSearch) throws SQLException {
        boolean deleted = false;
        if (findById(designerCodeToSearch) != null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_DELETE)) {
                ps.setInt(1, designerCodeToSearch);
                ps.executeUpdate();
                deleted = true;
            }
        }
        return deleted;
    }
}