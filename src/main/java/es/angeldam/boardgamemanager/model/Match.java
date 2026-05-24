package es.angeldam.boardgamemanager.model;

import java.sql.Timestamp;

/**
 * Class that stores the data about a match about a board game, with the place, datetime
 */
public class Match {
    private int code;
    private String place;
    private Timestamp date;
    private BoardGame boardGame;

    /**
     * Full-equip constructor of the class
     * @param code Code of the match
     * @param place where the match will take place
     * @param date date of the match
     * @param boardGame board game to be played
     */
    public Match(int code, String place, Timestamp date, BoardGame boardGame) {
        this.code = code;
        this.place = place;
        this.date = date;
        this.boardGame = boardGame;
    }

    /**
     * Constructor of the class
     * @param place where the match will take place
     * @param date date of the match
     * @param boardGame board game to be played
     */
    public Match(String place, Timestamp date, BoardGame boardGame) {
        this.place = place;
        this.date = date;
        this.boardGame = boardGame;
    }

    /**
     * Getter that obtains the match code
     * @return the int of the match code (identifier of match in the database table)
     */
    public int getCode() {
        return code;
    }

    /**
     * Getter that obtains the place where the match will take place
     * @return the string of the direction of the match
     */
    public String getPlace() {
        return place;
    }

    /**
     * Getter that obtains the date and time in timestamp
     * @return the timestamp of celebration of the match
     */
    public Timestamp getDate() {
        return date;
    }

    /**
     * Getter of the board game that will be played at the match
     * @return the board game which players will play
     */
    public BoardGame getBoardGame() {
        return boardGame;
    }

    /**
     * Method that returns the string of the match data
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "Match:\n" +
                "code:" + code +
                ", place:" + place +
                ", date:" + date +
                ", boardGame: " + boardGame;
    }
}