package es.angeldam.boardgamemanager.model;


import java.sql.Date;
import java.util.List;

public class Illustrator extends Person{
    private String nationality;

    public Illustrator(String name, Date birthDate, String nationality) {
        super(name, birthDate);
        this.nationality = nationality;
    }

    public Illustrator(int code, String name, Date birthDate, String nationality) {
        super(code, name, birthDate);
        this.nationality = nationality;
    }

    public Illustrator(int code, String name, Date birthDate, String nationality,List<BoardGame> boardGames) {
        super(code, name, birthDate, boardGames);
        this.nationality = nationality;
    }

    public String getNationality() {
        return nationality;
    }
}
