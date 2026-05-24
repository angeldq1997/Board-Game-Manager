package es.angeldam.boardgamemanager.dao;

import es.angeldam.boardgamemanager.dataAccess.ConnectionBD;
import es.angeldam.boardgamemanager.model.BoardGame;
import es.angeldam.boardgamemanager.model.Match;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that manages the data access of a match (as an object), this has the objective to do the operations to get the data from the database and bring it to Java.
 * This makes it usable for the methods and systems that requires it.
 * TO AVOID CONFUSION WITH BOARD GAME IT'S BETTER TO USE MATCH INSTEAD OF "GAME"
 */
public class MatchDAO {
    private final static String SQL_ALL = "SELECT * FROM match";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM match where matchCode =?";
    private final static String SQL_FIND_BY_BOARD_MATCH_CODE = "SELECT * FROM game where boardGameCode =?";
    private final static String SQL_INSERT = "INSERT INTO match (place, matchDate, boardGameCode) VALUES (?,?,?)";
    private final static String SQL_UPDATE = "UPDATE match SET name =?, alias =?, birthDate =?, nationality =? WHERE matchCode = ?";
    private final static String SQL_DELETE = "DELETE FROM match WHERE matchCode = ?";

    public static List<Match> findAll() throws SQLException {
        Match match = null;
        BoardGame boardGame = null;
        List<Match> matches = new ArrayList<>();

        ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_ALL);
        while (rs.next()) {
            int matchCode = rs.getInt("matchCode");
            String place = rs.getString("place");
            Timestamp matchDate = rs.getTimestamp("matchDate");
            int boardGameCode = rs.getInt("boardGameCode");
            boardGame = BoardGameDAO.findById(boardGameCode);
            match = new Match(matchCode, place, matchDate, boardGame);
            matches.add(match);
        }
        return matches;
    }

    public static Match findById(int matchCodeToSearch) throws SQLException {
        Match match = null;
        BoardGame boardGame = null;

        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, matchCodeToSearch);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int matchCode = rs.getInt("matchCode");
                String place = rs.getString("place");
                Timestamp matchDate = rs.getTimestamp("matchDate");
                int boardGameCode = rs.getInt("boardGameCode");
                boardGame = BoardGameDAO.findById(boardGameCode);
                match = new Match(matchCode, place, matchDate, boardGame);
            }
        }
        return match;
    }

    public static boolean addMatch(Match match) throws SQLException {
        boolean added = false;
        if ((match != null) && findById(match.getCode()) == null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1, match.getPlace());
                ps.setTimestamp(2, match.getDate());
                ps.setObject(3, match.getBoardGame());
                ps.executeUpdate();
                added = true;
            }
        }
        return added;
    }

    public static boolean updateMatch(Match actualMatch, Match newMatch) throws SQLException {
        boolean updated = false;
        if ((actualMatch != null) && (newMatch != null) && findById(actualMatch.getCode()) != null && findById(newMatch.getCode()) == null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE)) {
                ps.setString(1, newMatch.getPlace());
                ps.setTimestamp(2, newMatch.getDate());
                ps.setInt(3, newMatch.getBoardGame().getCode());
                ps.setInt(4, actualMatch.getCode());
                ps.executeUpdate();
                updated = true;
            }
        }
        return updated;
    }

    public static boolean deleteMatchById(int matchCode) throws SQLException {
        boolean deleted = false;
        if (findById(matchCode) != null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_DELETE)) {
                ps.setInt(1, matchCode);
                ps.executeUpdate();
                deleted = true;
            }
        }
        return deleted;
    }
}