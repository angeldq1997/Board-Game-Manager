package es.angeldam.boardgamemanager.model;

import java.util.List;

/**
 * Class that stores the information that has a designer of a board game in the app, it extends from person
 */
public class Designer extends Person {
    private String alias;
    private String nationality;

    /**
     * Full-equip constructor of the class (all attributes)
     * @param code code of the designer which is in the database
     * @param name name of the designer
     * @param alias alias of the designer or short form to refer to him/her
     * @param birthYear the year of birth of the designer
     * @param nationality the nationality of the designer (Spanish, Russian, German, etc.)
     * @param boardGames the board games which the designer made with the balance, mechanics and game loops
     */
    public Designer(int code, String name, String alias, int birthYear, String nationality, List<BoardGame> boardGames) {
        super(code, name, birthYear, boardGames);
        this.alias = alias;
        this.nationality = nationality;
    }

    /**
     * Constructor of the class
     * @param code code of the designer which is in the database
     * @param name name of the designer
     * @param alias alias of the designer or short form to refer to him/her
     * @param birthYear the year of birth of the designer
     * @param nationality the nationality of the designer (Spanish, Russian, German, etc.)
     */
    public Designer(int code, String name, String alias, int birthYear, String nationality) {
        super(code, name, birthYear);
        this.alias = alias;
        this.nationality = nationality;
    }

    /**
     * Constructor of the class
     * @param name name of the designer
     * @param alias alias of the designer or short form to refer to him/her
     * @param birthYear the year of birth of the designer
     * @param nationality the nationality of the designer (Spanish, Russian, German, etc.)
     */
    public Designer(String name, String alias, int birthYear, String nationality) {
        super(name, birthYear);
        this.alias = alias;
        this.nationality = nationality;
    }

    /**
     * Getter that obtains the nationality of the designer
     * @return the nationality of the designer
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * Getter that obtains the alias of the designer
     * @return the alias / short form to refer to designer
     */
    public String getAlias() {
        return alias;
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
     * Method that appends the data of the designer in a string
     * @return a string representation of the designer with the list of board games if isn't null.
     */
    @Override
    public String toString() {
        if (!listBoardGames().trim().isBlank()){
            return "Designer{ "  + super.toString() +
                    ", alias: " + alias +
                    ", nationality: " + nationality + listBoardGames() + "}";
        }else{
            return "Designer{ "  + super.toString() +
                    ", alias: " + alias +
                    ", nationality: " + nationality;
        }
    }
}