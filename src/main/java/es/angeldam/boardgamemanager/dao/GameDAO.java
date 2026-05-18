package es.angeldam.boardgamemanager.dao;

import es.angeldam.boardgamemanager.dataAccess.ConnectionBD;
import es.angeldam.boardgamemanager.model.BoardGame;
import es.angeldam.boardgamemanager.model.Game;
import es.angeldam.boardgamemanager.model.Player;

import java.sql.*;
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

        ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_ALL);
        while (rs.next()) {
            int gameCode = rs.getInt("gameCode");
            String place = rs.getString("place");
            Timestamp gameDate = rs.getTimestamp("gameDate");
            int gameBoardCode = rs.getInt("gameBoardCode");
            boardGame = BoardGameDAO.findById(gameBoardCode);
            game = new Game(gameCode, place, gameDate, players, boardGame);
            games.add(game);
        }
        return games;
    }

    public static Game findById(int gameCodeToSearch) throws SQLException {
        Game game = null;
        BoardGame boardGame = null;
        ArrayList<Player> players = null;
        HashSet<Game> games = new HashSet<>();

        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, gameCodeToSearch);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int gameCode = rs.getInt("gameCode");
                String place = rs.getString("place");
                Timestamp gameDate = rs.getTimestamp("gameDate");
                int gameBoardCode = rs.getInt("gameBoardCode");
                boardGame = BoardGameDAO.findById(gameBoardCode);
                game = new Game(gameCode, place, gameDate, players, boardGame);
            }
        }
        return game;
    }

    public static boolean addGame(Game game) throws SQLException {
        boolean added = false;
        if ((game != null) && findById(game.getCode()) == null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1, game.getPlace());
                ps.setTimestamp(2, game.getDate());
                ps.setObject(3, game.getBoardGame());
                ps.executeUpdate();
                added = true;
            }
        }
        return added;
    }

    public static boolean updateGame(Game actualGame, Game newGame) throws SQLException {
        boolean updated = false;
        if ((actualGame != null) && (newGame != null) && findById(actualGame.getCode()) != null && findById(newGame.getCode()) == null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE)) {
                ps.setString(1, newGame.getPlace());
                ps.setTimestamp(2, newGame.getDate());
                ps.setInt(3, newGame.getBoardGame().getCode());
                ps.setInt(4, actualGame.getCode());
                ps.executeUpdate();
                updated = true;
            }
        }
        return updated;
    }

    public static boolean deleteGameById(int gameCode) throws SQLException {
        boolean deleted = false;
        if (findById(gameCode) != null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_DELETE)) {
                ps.setInt(1, gameCode);
                ps.executeUpdate();
                deleted = true;
            }
        }
        return deleted;
    }
}