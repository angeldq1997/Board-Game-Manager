package es.angeldam.boardgamemanager.dao;

import es.angeldam.boardgamemanager.dataAccess.ConnectionBD;
import es.angeldam.boardgamemanager.model.*;
import es.angeldam.boardgamemanager.utils.Difficulty;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardGameDAO {
    private static final String SQL_ALL = "SELECT * FROM boardgame";

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
            " FROM boardgame; ";

    private static final String SQL_PARTIAL = "SELECT * FROM boardgame WHERE ? LIKE ?";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM boardgame WHERE boardGameCode =?";
    private static final String SQL_INSERT = "INSERT INTO boardgame (name, minPlayers, maxPlayers, averageDuration" +
            ", recommendedAge, publicationYear, difficulty, ranking, mechanics) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE boardgame SET name =?, minPlayers =?, maxPlayers =?" +
            ", averageDuration=?, recommendedAge=?, publicationYear=?, difficulty=?, ranking=?, mechanics = ? WHERE boardGameCode = ?";
    private static final String SQL_DELETE = "DELETE FROM boardgame WHERE boardGameCode = ?";

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

                //After getting all data of BG, we get from the database designers, illustrators and publishers

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

    public static ArrayList<BoardGame> findAll() throws SQLException {
        ArrayList<BoardGame> boardGames = new ArrayList<>();

        try (ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_ALL)) {
            while (rs.next()) {
                boardGames.add(getBoardGameData(rs));
            }
        }
        return boardGames;
    }

    public static ArrayList<BoardGame> findPartial(String locationToSearch, String textToSearch) throws SQLException{
        ArrayList<BoardGame> boardGames = new ArrayList<>();

        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_PARTIAL)) {
            ps.setString(1, locationToSearch);
            ps.setString(2, "%" + textToSearch + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                boardGames.add(getBoardGameData(rs));
            }
        }
        return boardGames;
    }

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

    private static BoardGame getBoardGameData(ResultSet rs) throws SQLException {
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
        return new BoardGame(gameCode, name, minPlayers, maxPlayers, averageDuration, recommendedAge, publicationYear, difficulty, ranking, mechanics);
    }
}