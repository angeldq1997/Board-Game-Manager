package es.angeldam.boardgamemanager.dao;

import es.angeldam.boardgamemanager.dataAccess.ConnectionBD;
import es.angeldam.boardgamemanager.model.*;
import es.angeldam.boardgamemanager.utils.Difficulty;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that manages the data access of a board game (as an object), this has the objective to do the operations to get the data from the database and bring it to Java.
 * This makes it usable for the methods and systems that requires it.
 */
public class BoardGameDAO {
    private static final String SQL_ALL = "SELECT * FROM boardgame";

    //This line could be useful when the app want to show a board game with the name of the designers, illustrators and publishers
    private static final String SQL_ALL_ONE_LINE = "SELECT DISTINCT bg.*, " +
            "(SELECT group_concat(name) FROM designer), " +
            "(SELECT group_concat(name) FROM illustrator) , " +
            "(SELECT group_concat(name) FROM publisher) " +
            "FROM boardGame bg, designer des,illustrator ill, publisher pub, depict, make, produce " +
            "WHERE bg.boardGameCode = depict.boardGameCode AND ill.illustratorCode = depict.illustratorCode " +
            "AND bg.boardGameCode = make.boardGameCode AND des.designerCode = make.designerCode " +
            "AND bg.boardGameCode = produce.boardGameCode AND pub.publisherCode = produce.publisherCode; ";

    private static final String SQL_ALL_WITH_CODES = "SELECT DISTINCT boardgame.* , " +
            "(SELECT group_concat(designerCode) FROM make WHERE boardgame.boardGameCode = boardGameCode) designerCodes, " +
            "(SELECT group_concat(illustratorCode) FROM depict WHERE boardgame.boardGameCode = boardGameCode) illustratorCodes, " +
            "(SELECT group_concat(publisherCode) FROM produce WHERE boardgame.boardGameCode = boardGameCode) publisherCodes" +
            " FROM boardgame LIMIT 100; ";

    private static final String SQL_PARTIAL = "SELECT * FROM boardgame WHERE ? LIKE ?";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM boardgame WHERE boardGameCode =?";
    private static final String SQL_INSERT = "INSERT INTO boardgame (name, minPlayers, maxPlayers, averageDuration" +
            ", recommendedAge, publicationYear, difficulty, ranking, mechanics) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE boardgame SET name =?, minPlayers =?, maxPlayers =?" +
            ", averageDuration=?, recommendedAge=?, publicationYear=?, difficulty=?, ranking=?, mechanics = ? WHERE boardGameCode = ?";
    private static final String SQL_DELETE = "DELETE FROM boardgame WHERE boardGameCode = ?";

    /**
     * Static method that retrieve all board game data from the database except illustrators, publishers or designers of the board game
     * @return the board game List without illustrators, publishers or designers or NULL if it couldn't find any
     * @throws SQLException when there is: a problem retrieving data, an error with the query
     */
    public static ArrayList<BoardGame> findAll() throws SQLException {
        ArrayList<BoardGame> boardGames = new ArrayList<>();

        try (ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_ALL)) {
            while (rs.next()) {
                boardGames.add(getBoardGameData(rs));
            }
        }
        return boardGames;
    }

    /**
     * Static method that groups the board games from the database on a list
     * @return the list of board games with all the data or NULL if it couldn't find any
     * @throws SQLException when there is: a problem retrieving data, an error with the query
     */
    public static List<BoardGame> findAllEager() throws SQLException {
        List<BoardGame> boardGames = new ArrayList<>();
        List<Designer> designers = new ArrayList<>();
        List<Illustrator> illustrators = new ArrayList<>();
        List<Publisher> publishers = new ArrayList<>();
        BoardGame boardGame = null;
        String designerCodes = null;
        String illustratorCodes = null;
        String publisherCodes = null;

        try (ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_ALL_WITH_CODES)) {
            while (rs.next()) {
                boardGame = getBoardGameData(rs);

                //After getting all data of board games, we get from the database designers, illustrators and publishers

                designerCodes = rs.getString("designerCodes");
                if ( designerCodes != null ) {
                    String[] designerCodeList = designerCodes.split(",");
                    for (String designerCode : designerCodeList) {
                        designers.add(DesignerDAO.findById(Integer.parseInt(designerCode)));
                    }
                    boardGame.setDesigners(designers);
                }

                publisherCodes = rs.getString("publisherCodes");
                if ( publisherCodes != null ) {
                    String[] publisherCodeList = publisherCodes.split(",");
                    for (String publisherCode : publisherCodeList) {
                        publishers.add(PublisherDAO.findById(Integer.parseInt(publisherCode)));
                    }
                    boardGame.setPublishers(publishers);
                }

                illustratorCodes = rs.getString("illustratorCodes");
                if ( illustratorCodes != null ) {
                    String[] illustratorCodeList = illustratorCodes.split(",");
                    for (String illustratorCode : illustratorCodeList) {
                        illustrators.add(IllustratorDAO.findById(Integer.parseInt(illustratorCode)));
                    }
                    boardGame.setIllustrators(illustrators);
                }

                boardGames.add(boardGame);
            }
        }
        return boardGames;
    }

    /**
     * Static method that retrieve only the board games that contains a string on their respective column
     * @param attributeToSearch the attribute to search in the board game table
     * @param textToSearch the text to filter the board games
     * @return the board game List without illustrators, publishers or designers or NULL if it couldn't find any
     * @throws SQLException when there is: a problem retrieving data, an error with the query
     */
    public static ArrayList<BoardGame> findPartial(String attributeToSearch, String textToSearch) throws SQLException{
        ArrayList<BoardGame> boardGames = new ArrayList<>();

        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_PARTIAL)) {
            ps.setString(1, attributeToSearch);
            ps.setString(2, "%" + textToSearch + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                boardGames.add(getBoardGameData(rs));
            }
        }
        return boardGames;
    }

    /**
     * Static method that retrieve the board game which code is received by the method
     * @param boardGameCodeToSearch the board game code, which info will be taken from the database
     * @return the board game List without illustrator, publisher or designer data or NULL if it couldn't find any
     * @throws SQLException when there is: a problem retrieving data, an error with the query
     */
    public static BoardGame findById (int boardGameCodeToSearch) throws SQLException {
        BoardGame boardGame = null;

        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, boardGameCodeToSearch);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                boardGame = getBoardGameData(rs);
            }
        }
        return boardGame;
    }

    /**
     * Static method that adds a board game to the database
     * @param boardGame the board game with the info to be stored in the database
     * @return True if the board game was added without a problem or False if there was an exception in the process
     * @throws SQLException when there is: a problem sending data, an error with the query or a conflict with data types
     */
    public static boolean addBoardGame(BoardGame boardGame) throws SQLException {
        boolean added = false;
        if ((boardGame != null) && findById(boardGame.getCode()) == null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1, boardGame.getName());
                ps.setInt(2, boardGame.getMinPlayers());
                ps.setInt(3, boardGame.getMaxPlayers());
                ps.setInt(4, boardGame.getAverageDuration());
                ps.setString(5, boardGame.getRecommendedAge());
                ps.setInt(6, boardGame.getPublicationYear());
                ps.setString(7, boardGame.getDifficulty().name());
                ps.setInt(8, boardGame.getRanking());
                ps.setString(9, boardGame.getMechanics());
                ps.executeUpdate();
                added = true;
            }
        }
        return added;
    }

    /**
     * Static method that updates a board game from the database modifying some of its values
     * @param actualBoardGame the board game which has the code in the database
     * @param newBoardGame the board game which data will replace actualboardgame's data
     * @return True if the board game was updated with the new data or False if there was an error/exception
     * @throws SQLException when there is: a problem sending new data, an error with the query or a conflict with data types
     */
    public static boolean updateBoardGame(BoardGame actualBoardGame, BoardGame newBoardGame) throws SQLException {
        boolean updated = false;
        if ((actualBoardGame != null) && (newBoardGame != null) && findById(actualBoardGame.getCode()) != null && findById(newBoardGame.getCode()) == null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE)) {
                ps.setString(1, newBoardGame.getName());
                ps.setInt(2, newBoardGame.getMinPlayers());
                ps.setInt(3, newBoardGame.getMaxPlayers());
                ps.setInt(4, newBoardGame.getAverageDuration());
                ps.setString(5, newBoardGame.getRecommendedAge());
                ps.setInt(6, newBoardGame.getPublicationYear());
                ps.setString(7, newBoardGame.getDifficulty().name());
                ps.setInt(8, newBoardGame.getRanking());
                ps.setString(9, newBoardGame.getMechanics());
                ps.setInt(10, actualBoardGame.getCode());
                ps.executeUpdate();
                updated = true;
            }
        }
        return updated;
    }

    /**
     * Static method that deletes a board game from the database by its identifier
     * @param boardGameCode the board game which has the data to be erased from the database
     * @return True if the board game was deleted or False if the data couldn't be erased
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called on a closed result set
     */
    public static boolean deleteGameById(int boardGameCode) throws SQLException {
        boolean deleted = false;
        if (findById(boardGameCode) != null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_DELETE)) {
                ps.setInt(1, boardGameCode);
                ps.executeUpdate();
                deleted = true;
            }
        }
        return deleted;
    }

    /**
     * Secondary method that is used by several of the principal methods of the class, it sets the data of a board game from a resultset
     * @param rs resultset of the board games data which want to be transferred to Java
     * @return the board game with all the relevant info or NULL if it couldn't set its values
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called on a closed result set
     */
    private static BoardGame getBoardGameData(ResultSet rs) throws SQLException {
        BoardGame boardGame = null;
        int gameCode = rs.getInt("boardGameCode");
        String name = rs.getString("name");
        int minPlayers = rs.getInt("minPlayers");
        int maxPlayers = rs.getInt("maxPlayers");
        int averageDuration = rs.getInt("averageDuration");
        String recommendedAge = rs.getString("recommendedAge");
        int publicationYear = rs.getInt("publicationYear");
        Difficulty difficulty = Difficulty.valueOf(rs.getString("difficulty"));
        int ranking = rs.getInt("ranking");
        String mechanics = rs.getString("mechanics");
        boardGame = new BoardGame(gameCode, name, minPlayers, maxPlayers, averageDuration, recommendedAge, publicationYear, difficulty, ranking, mechanics);
        return boardGame;
    }
}