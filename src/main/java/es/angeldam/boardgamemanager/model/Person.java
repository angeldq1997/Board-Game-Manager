package es.angeldam.boardgamemanager.model;

import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;

public abstract class Person {
    private int code;
    private String name;
    private Date birthDate;
    private String nationality;
    private HashSet<BoardGame> boardGames;

    public Person(int code, String name, Date birthDate, String nationality, HashSet<BoardGame> boardGames) {
        this.code = code;
        this.name = name;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.boardGames = boardGames;
    }

    public Person(int code, String name, Date birthDate, String nationality) {
        this.code = code;
        this.name = name;
        this.birthDate = birthDate;
        this.nationality = nationality;
    }

    public Person(String name, Date birthdate, String nationality) {
        this.name = name;
        this.birthDate = birthdate;
        this.nationality = nationality;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    public void setBirthDate(Date birthdate) {
        this.birthDate = birthdate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public HashSet<BoardGame> getBoardGames() {
        return boardGames;
    }

    public void setBoardGames(HashSet<BoardGame> boardGames) {
        this.boardGames = boardGames;
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
                ", nationality:" + nationality +
                ", boardGames:" + boardGames;
    }
}