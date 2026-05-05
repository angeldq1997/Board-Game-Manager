package es.angeldam.boardgamemanager.model;

import java.util.ArrayList;
import java.util.Date;

public class Game {
    private int code;
    private String place;
    private Date date;
    private ArrayList<Player> players;
    private ArrayList<Player> winners;

    public Game(String place, Date date, ArrayList<Player> players, ArrayList<Player> winners) {
        this.place = place;
        this.date = date;
        this.players = players;
        this.winners = winners;
    }

    @Override
    public String toString() {
        return "Game:\n" +
                "code:" + code +
                ", place:" + place +
                ", date:" + date +
                ", players:" + players +
                ", winners:" + winners;
    }
}