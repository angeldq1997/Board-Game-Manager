package es.angeldam.boardgamemanager.dao;

import es.angeldam.boardgamemanager.dataAccess.ConnectionBD;
import es.angeldam.boardgamemanager.model.Game;
import es.angeldam.boardgamemanager.model.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class PlayerDAO {
    private final static String SQL_ALL = "SELECT * FROM player";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM player where playerCode =?";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM player where name =?";
    private final static String SQL_INSERT = "INSERT INTO player (name, birthYear) VALUES (?,?)";
    private final static String SQL_UPDATE = "UPDATE player SET name =?, birthYear =? WHERE playerCode = ?";
    private final static String SQL_DELETE = "DELETE FROM player WHERE playerCode = ?";

    public static HashSet<Player> findAll() throws SQLException {
        Player player = null;
        HashSet<Player> players = new HashSet<>();

        HashSet<Game> games = new HashSet<>();

        ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_ALL);
        while (rs.next()) {
            int playerCode = rs.getInt("playerCode");
            String name = rs.getString("name");
            int birthYear = rs.getInt("birthYear");
            player = new Player(name, birthYear);
            players.add(player);
        }
        return players;
    }

    public static Player findById(int playerCodeToSearch) throws SQLException {
        Player player = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, playerCodeToSearch);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int playerCode = rs.getInt("playerCode");
                String name = rs.getString("name");
                int birthYear = rs.getInt("birthYear");
                player = new Player(playerCode, name, birthYear);
            }
        }
        return player;
    }

    public static boolean addPlayer(Player player) throws SQLException {
        boolean added = false;
        if ((player != null) && findById(player.getCode()) == null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1,player.getName());
                ps.setInt(2, player.getBirthYear());
                ps.executeUpdate();
                added = true;
            }
        }
        return added;
    }

    public static boolean updatePlayer(Player actualPlayer, Player newPlayer) throws SQLException {
        boolean updated = false;
        if ((actualPlayer != null) && (newPlayer != null) && findById(actualPlayer.getCode()) != null && findById(newPlayer.getCode()) == null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE)) {
                ps.setString(1, newPlayer.getName());
                ps.setInt(2, newPlayer.getBirthYear());
                ps.executeUpdate();
                updated = true;
            }
        }
        return updated;
    }

    public static boolean deletePlayerById(int playerCode) throws SQLException {
        boolean deleted = false;
        if (findById(playerCode) != null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_DELETE)) {
                ps.setInt(1, playerCode);
                ps.executeUpdate();
                deleted = true;
            }
        }
        return deleted;
    }
}