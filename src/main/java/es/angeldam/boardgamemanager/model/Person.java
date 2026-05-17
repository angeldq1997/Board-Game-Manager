package es.angeldam.boardgamemanager.model;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

public abstract class Person {
    private int code;
    private String name;
    private Date birthDate;
    private List<BoardGame> boardGames;

    public Person(int code, String name, Date birthDate, List<BoardGame> boardGames) {
        this.code = code;
        this.name = name;
        this.birthDate = birthDate;
        this.boardGames = boardGames;
    }

    public Person(int code, String name, Date birthDate) {
        this.code = code;
        this.name = name;
        this.birthDate = birthDate;
    }

    public Person(String name, Date birthdate) {
        this.name = name;
        this.birthDate = birthdate;
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

    public Date getBirthDate() {
        return birthDate;
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
                ", birthdate:" + birthDate +
                ", boardGames:" + boardGames;
    }
}