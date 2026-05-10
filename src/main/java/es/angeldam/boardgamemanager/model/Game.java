package es.angeldam.boardgamemanager.model;

import java.util.ArrayList;
import java.util.Date;

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