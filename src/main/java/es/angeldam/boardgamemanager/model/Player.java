package es.angeldam.boardgamemanager.model;


import java.sql.Date;

public class Player extends Person{

    public Player(String name, int birthYear) {
        super(name, birthYear);
    }

    public Player(int code, String name, int birthYear) {
        super(code, name, birthYear);
    }
}