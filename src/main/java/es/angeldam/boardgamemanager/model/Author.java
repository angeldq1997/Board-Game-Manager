package es.angeldam.boardgamemanager.model;

import java.util.Date;
import java.util.HashSet;

public class Author extends Person{
    private String alias;

    public Author(String name, Date birthDate, String nationality, String alias) {
        super(name, birthDate, nationality);
        this.alias = alias;
    }

    public Author(int code, String name, Date birthDate, String nationality, HashSet<BoardGame> boardGames, String alias) {
        super(code, name, birthDate, nationality, boardGames);
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return "Author:" + super.toString() +
                "alias:" + alias;
    }
}