package es.angeldam.boardgamemanager.model;

import java.sql.Timestamp;

public class Participation {
    private int participateCode;
    private int matchCode;
    private String place;
    private Timestamp date;
    private BoardGame boardGame;
    private int score;
    private Player player;

    public Participation(int participateCode , int matchCode , int score, Player player) {
        this.participateCode = participateCode;
        this.matchCode = matchCode;
        this.score = score;
        this.player = player;
    }

    public Participation(int matchCode, int score, Player player) {
        this.matchCode = matchCode;
        this.score = score;
        this.player = player;
    }

    public int getParticipateCode() {
        return participateCode;
    }

    public int getMatchCode() {
        return matchCode;
    }

    public int getScore() {
        return score;
    }

    public Player getPlayer() {
        return player;
    }
}
