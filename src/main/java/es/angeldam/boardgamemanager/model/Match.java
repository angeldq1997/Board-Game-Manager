package es.angeldam.boardgamemanager.model;

import java.sql.Timestamp;


public class Match {
    private int code;
    private String place;
    private Timestamp date;
    private BoardGame boardGame;

    public Match(int code, String place, Timestamp date, BoardGame boardGame) {
        this.code = code;
        this.place = place;
        this.date = date;
        this.boardGame = boardGame;
    }

    public Match(String place, Timestamp date, BoardGame boardGame) {
        this.place = place;
        this.date = date;
        this.boardGame = boardGame;
    }

    public int getCode() {
        return code;
    }

    public String getPlace() {
        return place;
    }

    public Timestamp getDate() {
        return date;
    }

    public BoardGame getBoardGame() {
        return boardGame;
    }


    @Override
    public String toString() {
        return "Match:\n" +
                "code:" + code +
                ", place:" + place +
                ", date:" + date +
                ", boardGame: " + boardGame;
    }
}