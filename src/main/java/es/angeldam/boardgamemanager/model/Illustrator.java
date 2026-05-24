package es.angeldam.boardgamemanager.model;


import es.angeldam.boardgamemanager.interfaces.StoreBoardGames;

import java.util.List;

/**
 * Class that stores the information that has an illustrator of a board game in the app, it extends from person
 */
public class Illustrator extends Person implements StoreBoardGames {
    private String nationality;

    /**
     * Full-equip constructor of the class (all attributes)
     * @param code code of the illustrator
     * @param name name of the illustrator
     * @param birthYear the year of birth of the illustrator
     * @param nationality the nationality of the illustrator (Spanish, Russian, German, etc.)
     * @param boardGames the board games which the illustrator depicted, made the art and others collaborations that implied his/her work
     */
    public Illustrator(int code, String name, int birthYear, String nationality, List<BoardGame> boardGames) {
        super(code, name, birthYear, boardGames);
        this.nationality = nationality;
    }

    /**
     * Constructor of the class
     * @param code of the illustrator
     * @param name name of the illustrator
     * @param birthYear the year of birth of the illustrator
     * @param nationality the nationality of the illustrator (Spanish, Russian, German, etc.)
     */
    public Illustrator(int code, String name, int birthYear, String nationality) {
        super(code, name, birthYear);
        this.nationality = nationality;
    }

    /**
     * Constructor of the class
     * @param name name of the illustrator
     * @param birthYear the year of birth of the illustrator
     * @param nationality the nationality of the illustrator (Spanish, Russian, German, etc.)
     */
    public Illustrator(String name, int birthYear, String nationality) {
        super(name, birthYear);
        this.nationality = nationality;
    }

    /**
     * Getter that obtains the nationality of the illustrator
     * @return the nationality of the illustrator
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * Method that appends in one string the names of the board games which the designer have worked on
     * @return string with the names of the board games
     */
    public String listBoardGames() {
        String list = "";
        if (super.getBoardGames() != null) {
            for (BoardGame b : super.getBoardGames()) {
                list += b.getName() + "  ";
            }
        }
        return list;
    }

    /**
     * Method that appends the data of the illustrator in a string
     * @return a string representation of the illustrator with the list of board games if isn't null.
     */
    @Override
    public String toString() {
        if (!listBoardGames().trim().isBlank()) {
            return "Designer{" + super.toString() +
                    ", nationality:" + nationality + listBoardGames() + "}";
        } else {
            return "Illustrator{" + super.toString() +
                    "nationality='" + nationality + "\n birth year:" + getBirthYear();
        }
    }
}