package es.angeldam.boardgamemanager.model;

import java.util.List;
import java.util.Objects;

public abstract class Person {
    private int code;
    private String name;
    private int birthYear;
    private List<BoardGame> boardGames;

    public Person(int code, String name, int birthYear, List<BoardGame> boardGames) {
        this.code = code;
        this.name = name;
        this.birthYear = birthYear;
        this.boardGames = boardGames;
    }

    public Person(int code, String name, int birthYear) {
        this.code = code;
        this.name = name;
        this.birthYear = birthYear;
    }

    public Person(String name, int birthYear) {
        this.name = name;
        this.birthYear = birthYear;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public List<BoardGame> getBoardGames() {
        return boardGames;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Person person)) return false;
        return code == person.code;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    @Override
    public String toString() {
        return "code=" + code +
                ", name:" + name +
                ", birthYear:" + birthYear +
                ", boardGames:" + boardGames;
    }
}