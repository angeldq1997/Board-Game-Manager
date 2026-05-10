package es.angeldam.boardgamemanager.model;

import java.sql.Date;
import java.util.HashSet;

public class Author extends Person{
    private String alias;
    private String nationality;

    public Author(String name, Date birthDate, String alias, String nationality) {
        super(name, birthDate);
        this.alias = alias;
        this.nationality = nationality;
    }

    public Author(int code, String name, String alias, Date birthDate, String nationality, HashSet<BoardGame> boardGames) {
        super(code, name, birthDate, boardGames);
        this.alias = alias;
        this.nationality = nationality;
    }

    public Author(int code, String name, String alias, Date birthDate, String nationality) {
        super(code, name, birthDate);
        this.alias = alias;
        this.nationality = nationality;
    }

    public String getNationality() {
        return nationality;
    }

    public String getAlias() {
        return alias;
    }


    @Override
    public String toString() {
        return "Author{" +
                "alias:" + alias +
                ", nationality:" + nationality + "}";
    }
}