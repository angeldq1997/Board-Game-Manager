package es.angeldam.boardgamemanager.model;

import java.util.List;

public class Designer extends Person{
    private String alias;
    private String nationality;

    public Designer(String name, String alias, int birthYear, String nationality) {
        super(name, birthYear);
        this.alias = alias;
        this.nationality = nationality;
    }

    public Designer(int code, String name, String alias, int birthYear, String nationality, List<BoardGame> boardGames) {
        super(code, name, birthYear, boardGames);
        this.alias = alias;
        this.nationality = nationality;
    }

    public Designer(int code, String name, String alias, int birthYear, String nationality) {
        super(code, name, birthYear);
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
        return "Designer{"  + super.toString() +
                "\n alias:" + alias +
                ", nationality:" + nationality + "}";
    }
}