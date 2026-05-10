package es.angeldam.boardgamemanager.dao;

import es.angeldam.boardgamemanager.dataAccess.ConnectionBD;
import es.angeldam.boardgamemanager.model.BoardGame;
import es.angeldam.boardgamemanager.utils.Difficulty;
import es.angeldam.boardgamemanager.utils.Mechanic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class BoardGameDAO {
    private final static String SQL_ALL = "SELECT * FROM boardgame";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM boardgame where boardGameCode =?";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM boardgame where name =?";
    private final static String SQL_INSERT = "INSERT INTO boardgame (name, principalGenre, minPlayers, maxPlayers, averageDuration" +
            ", recommendedAge, publicationYear, difficulty, ranking, mechanics) VALUES (?,?,?,?,?,?,?,?,?,?)";
    private final static String SQL_UPDATE = "UPDATE boardgame SET name =?, principalGenre =?, minPlayers =?, maxPlayers =?" +
            ", averageDuration=?, recommendedAge=?, publicationYear=?, difficulty=?, ranking=?, mechanics = ? WHERE boardGameCode = ?";
    private final static String SQL_DELETE = "DELETE FROM boardgame WHERE boardGameCode = ?";

    public static BoardGame findById (int boardGameCodeToSearch) throws SQLException {
        BoardGame boardGame = null;
        HashSet<BoardGame> boardGames = new HashSet<>();
        HashSet<Mechanic> mechanics = new HashSet<>();

        try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, boardGameCodeToSearch);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int gameCode = rs.getInt("gameCode");
                String name = rs.getString("name");
                String principalGenre = rs.getString("principalGenre");
                int minPlayers = rs.getInt("minPlayers");
                int maxPlayers = rs.getInt("maxPlayers");
                int averageDuration = rs.getInt("averageDuration");
                String recommendedAge = rs.getString("recommendedAge");
                int publicationYear = rs.getInt("publicationYear");
                Difficulty difficulty = (Difficulty) rs.getObject("difficulty");
                int ranking = rs.getInt("ranking");
                String mechanic = rs.getString("mechanics");
                boardGame = new BoardGame(gameCode, name, principalGenre, minPlayers, maxPlayers, averageDuration, recommendedAge, publicationYear, difficulty, ranking, mechanics);
            }
        }
        return boardGame;
    }

    public static boolean addBoardGame(BoardGame boardGame) throws SQLException {
        boolean added = false;
        if ((boardGame != null) && findById(boardGame.getCode()) == null) {
            try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1, boardGame.getName());
                ps.setString(2, boardGame.getPrincipalGenre());
                ps.setInt(3, boardGame.getMinPlayers());
                ps.setInt(4, boardGame.getMaxPlayers());
                ps.setInt(5, boardGame.getAverageDuration());
                ps.setString(6, boardGame.getRecommendedAge());
                ps.setInt(7, boardGame.getPublicationYear());
                ps.setObject(8, boardGame.getDifficulty());
                ps.setInt(9, boardGame.getRanking());
                ps.setString(10, boardGame.getMechanics().toString());
                ps.executeUpdate();
                added = true;
            }
        }
        return added;
    }

    public static boolean updateBoardGame(BoardGame actualBoardGame, BoardGame newBoardGame) throws SQLException {
        boolean updated = false;
        if ((actualBoardGame != null) && (newBoardGame != null) && findById(actualBoardGame.getCode()) != null && findById(newBoardGame.getCode()) == null) {
            try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_UPDATE)) {
                ps.setString(1, newBoardGame.getName());
                ps.setString(2, newBoardGame.getPrincipalGenre());
                ps.setInt(3, newBoardGame.getMinPlayers());
                ps.setInt(4, newBoardGame.getMaxPlayers());
                ps.setInt(5, newBoardGame.getAverageDuration());
                ps.setString(6, newBoardGame.getRecommendedAge());
                ps.setInt(7, newBoardGame.getPublicationYear());
                ps.setObject(8, newBoardGame.getDifficulty());
                ps.setInt(9, newBoardGame.getRanking());
                ps.setString(10, newBoardGame.getMechanics().toString());
                ps.setInt(11, actualBoardGame.getCode());
                ps.executeUpdate();
                updated = true;
            }
        }
        return updated;
    }

    public static boolean deleteGameById(int boardGameCode) throws SQLException {
        boolean deleted = false;
        if (findById(boardGameCode) != null) {
            try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_DELETE)) {
                ps.setInt(1, boardGameCode);
                ps.executeUpdate();
                deleted = true;
            }
        }
        return deleted;
    }
}