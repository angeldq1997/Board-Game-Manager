package es.angeldam.boardgamemanager.dao;

import es.angeldam.boardgamemanager.dataAccess.ConnectionBD;
import es.angeldam.boardgamemanager.model.Author;
import es.angeldam.boardgamemanager.model.BoardGame;

import java.sql.*;
import java.util.HashSet;

public class BoardGameDAO {
    private final static String SQL_ALL = "SELECT * FROM boardgame";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM boardgame where boardGameCode =?";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM boardgame where name =?";
    private final static String SQL_INSERT = "INSERT INTO boardgame (name, mechanics, minPlayers, maxPlayers, averageDuration" +
            ", recommendedAge, publicationYear, difficulty, ranking) VALUES (?,?,?,?,?,?,?,?,?)";
    private final static String SQL_UPDATE = "UPDATE boardgame SET name =?, mechanics =?, minPlayers =?, maxPlayers =?" +
            ", averageDuration=?, recommendedAge=?, publicationYear=?, difficulty=?, ranking=? WHERE boardGameCode = ?";
    private final static String SQL_DELETE = "DELETE FROM boardgame WHERE boardGameCode = ?";


}