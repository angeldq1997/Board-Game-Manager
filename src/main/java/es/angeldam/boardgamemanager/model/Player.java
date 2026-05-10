package es.angeldam.boardgamemanager.model;


import java.sql.Date;

public class Player extends Person{

    public Player(String name, Date birthDate) {
        super(name, birthDate);
    }

    public Player(int code, String name, Date birthDate) {
        super(code, name, birthDate);
    }
}