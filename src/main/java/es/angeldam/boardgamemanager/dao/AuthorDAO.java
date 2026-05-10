package es.angeldam.boardgamemanager.dao;

import es.angeldam.boardgamemanager.dataAccess.ConnectionBD;
import es.angeldam.boardgamemanager.model.Author;
import es.angeldam.boardgamemanager.model.BoardGame;

import java.sql.*;
import java.util.HashSet;


public class AuthorDAO {
    private final static String SQL_ALL = "SELECT * FROM author";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM author where authorCode =?";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM author where name =?";
    private final static String SQL_INSERT = "INSERT INTO author (name, alias, birthDate, nationality) VALUES (?,?,?,?)";
    private final static String SQL_UPDATE = "UPDATE author SET name =?, alias =?, birthDate =?, nationality =? WHERE authorCode = ?";
    private final static String SQL_DELETE = "DELETE FROM author WHERE authorCode = ?";


    public static HashSet<Author> findAll() throws SQLException {
        Author author = null;
        HashSet<Author> authors = new HashSet<>();

        ResultSet rs = ConnectionBD.getInstance().getConnection().createStatement().executeQuery(SQL_ALL);
        while (rs.next()) {
            int authorCode = rs.getInt("authorCode");
            String name = rs.getString("name");
            String alias = rs.getString("alias");
            Date birthDate = rs.getDate("birthDate");
            String nationality = rs.getString("nationality");
            author = new Author(authorCode, name, alias, birthDate, nationality);
            authors.add(author);
        }
        return authors;
    }

    public static HashSet<Author> findAllEager() throws SQLException {
        Author author = null;
        HashSet<Author> authors = new HashSet<>();

        ResultSet rs = ConnectionBD.getInstance().getConnection().createStatement().executeQuery(SQL_ALL);
        while (rs.next()) {
            int authorCode = rs.getInt("authorCode");
            String name = rs.getString("name");
            String alias = rs.getString("alias");
            Date birthDate = rs.getDate("birthDate");
            String nationality = rs.getString("nationality");
            HashSet<BoardGame> boardGames = findById(authorCode).getBoardGames();
            author = new Author(authorCode, name, alias, birthDate, nationality, boardGames);
            authors.add(author);
        }
        return authors;
    }

    public static Author findById(int authorCodeToSearch) throws SQLException {
        Author author = null;
        try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, authorCodeToSearch);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int authorCode = rs.getInt("authorCode");
                String name = rs.getString("name");
                String alias = rs.getString("alias");
                Date birthDate = rs.getDate("birthDate");
                String nationality = rs.getString("nationality");
                author = new Author(authorCode, name, alias, birthDate, nationality);
            }
        }
        return author;
    }

    public static Author findByIdEager(int authorCodeToSearch) throws SQLException {
        Author author = null;
        try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, authorCodeToSearch);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int authorCode = rs.getInt("authorCode");
                String name = rs.getString("name");
                String alias = rs.getString("alias");
                Date birthDate = rs.getDate("birthDate");
                String nationality = rs.getString("nationality");
                HashSet<BoardGame> boardGames = findById(authorCode).getBoardGames();
                author = new Author(authorCode, name, alias, birthDate, nationality);
            }
        }
        return author;
    }

    private static Author findByName(String nameToSearch) throws SQLException {
        Author author = null;
        try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_FIND_BY_NAME)) {
            ps.setString(1, nameToSearch);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int authorCode = rs.getInt("authorCode");
                String name = rs.getString("name");
                String alias = rs.getString("alias");
                Date birthDate = rs.getDate("birthDate");
                String nationality = rs.getString("nationality");
                author = new Author(authorCode, name, alias, birthDate, nationality);
            }
        }
        return author;
    }

    public static boolean addAuthor(Author author) throws SQLException {
        boolean added = false;
        if ((author != null) && findByName(author.getName()) == null) {
            try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1, author.getName());
                ps.setString(2, author.getAlias());
                ps.setDate(3, author.getBirthDate());
                ps.setString(4, author.getNationality());
                ps.executeUpdate();
                added = true;
            }
        }
        return added;
    }

    public static boolean updateAuthor(Author newAuthor, Author actualAuthor) throws SQLException {
        boolean updated = false;
        if ((actualAuthor != null) && (newAuthor != null) && findByName(actualAuthor.getName()) != null && findByName(newAuthor.getName()) == null) {
            try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_UPDATE)) {
                ps.setString(1, newAuthor.getName());
                ps.setString(2, newAuthor.getAlias());
                ps.setDate(3, newAuthor.getBirthDate());
                ps.setString(4, newAuthor.getNationality());
                ps.setInt(5, actualAuthor.getCode());
                ps.executeUpdate();
                updated = true;
            }
        }
        return updated;
    }

    public static boolean deleteAuthorById(int authorCodeToSearch) throws SQLException {
        boolean deleted = false;
        if (findById(authorCodeToSearch) != null) {
            try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_DELETE)) {
                ps.setInt(1, authorCodeToSearch);
                ps.executeUpdate();
                deleted = true;
            }
        }
        return deleted;
    }
}