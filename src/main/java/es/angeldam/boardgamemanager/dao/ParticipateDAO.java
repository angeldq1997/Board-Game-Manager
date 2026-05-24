package es.angeldam.boardgamemanager.dao;

import es.angeldam.boardgamemanager.dataAccess.ConnectionBD;
import es.angeldam.boardgamemanager.model.BoardGame;
import es.angeldam.boardgamemanager.model.Match;
import es.angeldam.boardgamemanager.model.Participation;
import es.angeldam.boardgamemanager.model.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ParticipateDAO {

    private final static String SQL_ALL = "SELECT * FROM participate";

    private final static String SQL_FIND_BY_ID = "SELECT * FROM match where participateCode =?";


    private final static String SQL_INSERT = "INSERT INTO participate VALUES (?,?,?,?)";
    private final static String SQL_UPDATE = "UPDATE participate SET matchCode =?, playerCode =?, score =? WHERE participateCode = ? ";
    private final static String SQL_UPDATE_SCORE = "UPDATE participate SET score =? WHERE participateCode = ?";
    private final static String SQL_DELETE = "DELETE FROM participate WHERE participateCode = ?";

    public static List<Participation> findAll() throws SQLException {
        Participation participation = null;
        List<Participation> participationList = new ArrayList<>();

        try (ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_ALL)) {
            while (rs.next()) {
                int participateCode = rs.getInt("participateCode");
                int matchCode = rs.getInt("matchCode");
                Player player = PlayerDAO.findById(Integer.parseInt(rs.getString("Player")));
                int score = rs.getInt("score");
                participation = new Participation(participateCode, matchCode, score, player);
                participationList.add(participation);
            }
        }
        return participationList;
    }

    public static Participation findById(int participateCodeToSearch) throws SQLException {
        Participation participation = null;

        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, participateCodeToSearch);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int participateCode = rs.getInt("participateCode");
                int matchCode = rs.getInt("matchCode");
                Player player = PlayerDAO.findById(Integer.parseInt(rs.getString("Player")));
                int score = rs.getInt("score");
                participation = new Participation(participateCode, matchCode, score, player);
            }
        }
        return participation;
    }

    public static boolean addParticipation(Participation participation) throws SQLException {
        boolean added = false;
        if ((participation != null) && findById(participation.getParticipateCode()) == null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
                ps.setInt(1, participation.getParticipateCode());
                ps.setInt(2, participation.getMatchCode());
                ps.setInt(3, participation.getPlayer().getCode());
                ps.setInt(4, participation.getScore());
                ps.executeUpdate();
                added = true;
            }
        }
        return added;
    }

    public static boolean updateParticipationScore(Participation participation, int score) throws SQLException {
        boolean updated = false;
        if ((participation != null) && (score != 0) && findById(participation.getParticipateCode()) != null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE_SCORE)) {
                ps.setInt(1, score);
                ps.setInt(2, participation.getParticipateCode());
                ps.executeUpdate();
                updated = true;
            }
        }
        return updated;
    }

    public static boolean updateParticipation(Participation actualParticipation, Participation newParticipation) throws SQLException {
        boolean updated = false;
        if ((actualParticipation != null) && (newParticipation != null) && findById(actualParticipation.getParticipateCode()) != null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE)) {
                ps.setInt(1, newParticipation.getMatchCode());
                ps.setInt(2, newParticipation.getPlayer().getCode());
                ps.setInt(3, newParticipation.getScore());
                ps.setInt(4, actualParticipation.getParticipateCode());
                ps.executeUpdate();
                updated = true;
            }
        }
        return updated;
    }

    public static boolean deleteParticipationById(int participationCode) throws SQLException {
        boolean deleted = false;
        if (findById(participationCode) != null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_DELETE)) {
                ps.setInt(1, participationCode);
                ps.executeUpdate();
                deleted = true;
            }
        }
        return deleted;
    }
}
