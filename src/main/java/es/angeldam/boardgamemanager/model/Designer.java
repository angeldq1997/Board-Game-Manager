package es.angeldam.boardgamemanager.model;

import java.sql.Date;
import java.util.List;

public class Designer extends Person{
    private String alias;
    private String nationality;

    public Designer(String name, Date birthDate, String alias, String nationality) {
        super(name, birthDate);
        this.alias = alias;
        this.nationality = nationality;
    }

    public Designer(int code, String name, String alias, Date birthDate, String nationality, List<BoardGame> boardGames) {
        super(code, name, birthDate, boardGames);
        this.alias = alias;
        this.nationality = nationality;
    }

    public Designer(int code, String name, String alias, Date birthDate, String nationality) {
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
        return "Author{"  + super.toString() +
                "\n alias:" + alias +
                ", nationality:" + nationality + "}";
    }
}