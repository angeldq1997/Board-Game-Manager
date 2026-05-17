package es.angeldam.boardgamemanager.dao;

import es.angeldam.boardgamemanager.dataAccess.ConnectionBD;
import es.angeldam.boardgamemanager.model.BoardGame;
import es.angeldam.boardgamemanager.model.Publisher;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PublisherDAO {
    private final static String SQL_ALL = "SELECT * FROM publisher";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM publisher where publisherCode =?";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM publisher where name =?";
    private final static String SQL_INSERT = "INSERT INTO publisher (name, foundationYear, headquarters) VALUES (?,?,?)";
    private final static String SQL_UPDATE = "UPDATE publisher SET name =?, foundationYear =?, headquarters =? WHERE publisherCode = ?";
    private final static String SQL_DELETE = "DELETE FROM publisher WHERE publisherCode = ?";

    public static List<Publisher> findAll() throws SQLException {
        Publisher publisher = null;
        List<Publisher> publishers = new ArrayList<>();

        try (ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_ALL)) {
            while (rs.next()) {
                int publisherCode = rs.getInt("publisherCode");
                String name = rs.getString("name");
                int foundationYear = rs.getInt("foundationYear");
                String headquarters = rs.getString("headquarters");
                publisher = new Publisher(publisherCode, name, foundationYear, headquarters);
                publishers.add(publisher);
            }
        }
        return publishers;
    }

    public static List<Publisher> findAllEager() throws SQLException {
        Publisher publisher = null;
        List<Publisher> publishers = new ArrayList<>();

        try (ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_ALL)) {
            while (rs.next()) {
                int publisherCode = rs.getInt("publisherCode");
                String name = rs.getString("name");
                int foundationYear = rs.getInt("foundationYear");
                String headquarters = rs.getString("headquarters");
                List<BoardGame> boardGames = findById(publisherCode).getBoardGames();
                publisher = new Publisher(publisherCode, name, foundationYear, headquarters, boardGames);
                publishers.add(publisher);
            }
        }
        return publishers;
    }

    public static Publisher findById(int publisherCodeToSearch) throws SQLException {
        Publisher publisher = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, publisherCodeToSearch);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int publisherCode = rs.getInt("publisherCode");
                String name = rs.getString("name");
                int foundationYear = rs.getInt("foundationYear");
                String headquarters = rs.getString("headquarters");
                List<BoardGame> boardGames = findById(publisherCode).getBoardGames();
                publisher = new Publisher(publisherCode, name, foundationYear, headquarters, boardGames);
            }
        }
        return publisher;
    }

    public static boolean addPublisher(Publisher publisher) throws SQLException {
        boolean added = false;
        if ((publisher != null) && findById(publisher.getPublisherCode()) == null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1, publisher.getName());
                ps.setInt(2, publisher.getFoundationYear());
                ps.setString(3, publisher.getHeadquarters());
                ps.executeUpdate();
                added = true;
            }
        }
        return added;
    }

    public static boolean updatePublisher(Publisher actualPublisher, Publisher newPublisher) throws SQLException {
        boolean updated = false;
        if ((actualPublisher != null) && (newPublisher != null) && findById(actualPublisher.getPublisherCode()) != null && findById(newPublisher.getPublisherCode()) == null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE)) {
                ps.setString(1, newPublisher.getName());
                ps.setInt(2, newPublisher.getFoundationYear());
                ps.setString(3, newPublisher.getHeadquarters());
                ps.setInt(4, actualPublisher.getPublisherCode());
                ps.executeUpdate();
                updated = true;
            }
        }
        return updated;
    }

    public static boolean deletePublisherById(int publisherCode) throws SQLException {
        boolean deleted = false;
        if (findById(publisherCode) != null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_DELETE)) {
                ps.setInt(1, publisherCode);
                ps.executeUpdate();
                deleted = true;
            }
        }
        return deleted;
    }
}