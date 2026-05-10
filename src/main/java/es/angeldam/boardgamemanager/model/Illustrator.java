package es.angeldam.boardgamemanager.model;


import java.sql.Date;

public class Illustrator extends Person{
    private String nationality;

    public Illustrator(String name, Date birthDate, String nationality) {
        super(name, birthDate);
        this.nationality = nationality;
    }

    public String getNationality() {
        return nationality;
    }
}
