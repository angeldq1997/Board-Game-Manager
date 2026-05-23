package es.angeldam.boardgamemanager.model;

import java.util.List;
import java.util.Objects;

/**
 * Abstract class that has common attributes between their
 */
public abstract class Person {
    private int code;
    private String name;
    private int birthYear;
    private List<BoardGame> boardGames;

    /**
     * Full-equip constructor of the class (all attributes)
     * @param code code of the person which is in the database
     * @param name name of the person
     * @param birthYear the year of birth of the person
     * @param boardGames the board games which the person
     */
    public Person(int code, String name, int birthYear, List<BoardGame> boardGames) {
        this.code = code;
        this.name = name;
        this.birthYear = birthYear;
        this.boardGames = boardGames;
    }

    /**
     * Full-equip constructor of the class
     * @param code code of the person which is in the database
     * @param name name of the person
     * @param birthYear the year of birth of the person
     */
    public Person(int code, String name, int birthYear) {
        this.code = code;
        this.name = name;
        this.birthYear = birthYear;
    }

    /**
     * Constructor of the class
     * @param name name of the person
     * @param birthYear the year of birth of the person
     */
    public Person(String name, int birthYear) {
        this.name = name;
        this.birthYear = birthYear;
    }

    /**
     * Getter that obtains the code of the person which reveals the identifier on the database
     * @return the code of the concrete person
     */
    public int getCode() {
        return code;
    }

    /**
     * Getter that obtains the name of the person
     * @return name of the person
     */
    public String getName() {
        return name;
    }

    /**
     * Getter of the birth year of the concrete person
     * @return the year of birth of the person
     */
    public int getBirthYear() {
        return birthYear;
    }

    /**
     * Getter of the board games
     * @return the board games that are related to the person
     */
    public List<BoardGame> getBoardGames() {
        return boardGames;
    }

    /**
     * Methods that compare two person objects and verify if one is equal to the other with the code
     * @param o   the reference object with which to compare.
     * @return True when their code is equals or False when they are different instances of person OR they don't share the same code
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Person person)) return false;
        return code == person.code;
    }

    /**
     * Method that identifies a person with a hash
     * @return the hashcode of the person
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    /**
     * Method that groups the relevant info of the person
     * @return the code, name, birth year
     */
    @Override
    public String toString() {
        return "code: " + code +
                ", name: " + name +
                ", birthYear: " + birthYear;
    }
}