package es.angeldam.boardgamemanager.model;

import java.sql.Date;
import java.util.ArrayList;


public class Game {
    private int code;
    private String place;
    private Date date;
    private BoardGame boardGame;
    private ArrayList<Player> players;
    private ArrayList<Player> winners;

    public Game(String place, Date date, BoardGame boardGame,ArrayList<Player> players) {
        this.place = place;
        this.date = date;
        this.boardGame = boardGame;
        this.players = players;
        this.winners = new ArrayList<>();
    }

    public Game(int code, String place, Date date, ArrayList<Player> players, BoardGame boardGame) {
        this.code = code;
        this.place = place;
        this.date = date;
        this.players = players;
        this.winners = new ArrayList<>();
        this.boardGame = boardGame;
    }

    public int getCode() {
        return code;
    }

    public String getPlace() {
        return place;
    }

    public Date getDate() {
        return date;
    }

    public BoardGame getBoardGame() {
        return boardGame;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Player> getWinners() {
        return winners;
    }

    @Override
    public String toString() {
        return "Game:\n" +
                "code:" + code +
                ", place:" + place +
                ", date:" + date +
                ", boardGame: " + boardGame +
                ", players:" + players +
                ", winners:" + winners;
    }
}