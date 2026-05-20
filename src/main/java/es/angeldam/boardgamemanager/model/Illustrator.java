package es.angeldam.boardgamemanager.model;


import java.sql.Date;
import java.util.List;

public class Illustrator extends Person{
    private String nationality;

    public Illustrator(String name, int birthYear, String nationality) {
        super(name, birthYear);
        this.nationality = nationality;
    }

    public Illustrator(int code, String name, int birthYear, String nationality) {
        super(code, name, birthYear);
        this.nationality = nationality;
    }

    public Illustrator(int code, String name, int birthYear, String nationality,List<BoardGame> boardGames) {
        super(code, name, birthYear, boardGames);
        this.nationality = nationality;
    }

    public String getNationality() {
        return nationality;
    }

    @Override
    public String toString() {
        return "Illustrator{" +
                "nationality='" + nationality + '\'' +
                '}';
    }
}