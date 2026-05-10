package es.angeldam.boardgamemanager.dao;

import es.angeldam.boardgamemanager.dataAccess.ConnectionBD;
import es.angeldam.boardgamemanager.model.BoardGame;
import es.angeldam.boardgamemanager.model.Game;
import es.angeldam.boardgamemanager.model.Player;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

public class GameDAO {
    private final static String SQL_ALL = "SELECT * FROM game";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM game where gameCode =?";
    private final static String SQL_FIND_BY_BOARD_GAME_CODE = "SELECT * FROM game where boardGameCode =?";
    private final static String SQL_INSERT = "INSERT INTO game (place, gameDate, boardGameCode) VALUES (?,?,?)";
    private final static String SQL_UPDATE = "UPDATE game SET name =?, alias =?, birthDate =?, nationality =? WHERE gameCode = ?";
    private final static String SQL_DELETE = "DELETE FROM game WHERE gameCode = ?";

    public static HashSet<Game> findAll() throws SQLException {
        Game game = null;
        BoardGame boardGame = null;
        ArrayList<Player> players = null;
        HashSet<Game> games = new HashSet<>();

        ResultSet rs = ConnectionBD.getInstance().getConnection().createStatement().executeQuery(SQL_ALL);
        while (rs.next()) {
            int gameCode = rs.getInt("gameCode");
            String place = rs.getString("place");
            Date gameDate = rs.getDate("gameDate");
            int gameBoardCode = rs.getInt("gameBoardCode");
            boardGame = BoardGameDAO.findBoardGame(gameBoardCode);
            game = new Game(gameCode, place, gameDate, players, boardGame);
            games.add(game);
        }
        return games;
    }

    public static Game findById (int gameCode){

    }

    public static boolean deleteAuthorById(int gameCode) throws SQLException {
        boolean deleted = false;
        if (findById(gameCode) != null) {
            try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_DELETE)) {
                ps.setInt(1, gameCode);
                ps.executeUpdate();
                deleted = true;
            }
        }
        return deleted;
    }
}