package es.angeldam.boardgamemanager.model;

import es.angeldam.boardgamemanager.interfaces.StoreBoardGames;

import java.util.List;

/**
 * Class that stores the information that has a publisher of board games in the app
 */
public class Publisher implements StoreBoardGames {
    private int publisherCode;
    private String name;
    private int foundationYear;
    private String headquarters;
    private List<BoardGame> boardGames;

    /**
     * Full-equip constructor of the class (all attributes)
     * @param publisherCode the code which the publisher identify on the database
     * @param name name of the publisher
     * @param foundationYear the foundation year of the publisher
     * @param headquarters the headquarters of the publisher
     * @param boardGames the board games which the publisher produce and distribute to the public
     */
    public Publisher(int publisherCode, String name, int foundationYear, String headquarters, List<BoardGame> boardGames) {
        this.publisherCode = publisherCode;
        this.name = name;
        this.foundationYear = foundationYear;
        this.headquarters = headquarters;
        this.boardGames = boardGames;
    }

    /**
     * Constructor of the class
     * @param publisherCode the code which the publisher identify on the database
     * @param name name of the publisher
     * @param foundationYear the foundation year of the publisher
     * @param headquarters the headquarters of the publisher
     */
    public Publisher(int publisherCode, String name, int foundationYear, String headquarters) {
        this.publisherCode = publisherCode;
        this.name = name;
        this.foundationYear = foundationYear;
        this.headquarters = headquarters;
    }

    /**
     * Constructor of the class
     * @param name name of the publisher
     * @param foundationYear the foundation year of the publisher
     * @param headquarters the headquarters of the publisher
     */
    public Publisher(String name, int foundationYear, String headquarters) {
        this.name = name;
        this.foundationYear = foundationYear;
        this.headquarters = headquarters;
    }

    /**
     * Getter that obtains the code of the publisher
     * @return the code which the publisher identifies on the database
     */
    public int getCode() {
        return publisherCode;
    }

    /**
     * Getter that obtains the name of the publisher
     * @return the name of the publisher
     */
    public String getName() {
        return name;
    }

    /**
     * Getter that obtains the year of the foundation of the publisher
     * @return the foundation year of the publisher
     */
    public int getFoundationYear() {
        return foundationYear;
    }

    /**
     * Getter that obtains the headquarters of the publisher
     * @return the headquarters of the publisher
     */
    public String getHeadquarters() {
        return headquarters;
    }

    /**
     * Getter that obtains the board games that publish and distribute the publisher
     * @return the list of the board games of the publisher
     */
    public List<BoardGame> getBoardGames() {
        return boardGames;
    }

    /**
     * Method that appends the list of names the board games
     * @return
     */
    public String listBoardGames() {
        String list = "";
        if (this.getBoardGames() != null) {
            for (BoardGame b : this.getBoardGames()) {
                list += b.getName() + "  ";
            }
        }
        return list;
    }

    /**
     * Method that appends the data of the publisher in a string
     * @return a string representation of the publisher with the list of board games if isn't null.
     */
    @Override
    public String toString() {
        if (!listBoardGames().trim().isEmpty()){
            return "Publisher{ " +
                    "publisherCode: " + publisherCode +
                    ", name: " + name  +
                    ", foundationYear: " + foundationYear +
                    ", headquarters: " + headquarters  +
                    ", boardGames: " + boardGames + this.listBoardGames()  + "}";
        }
        return "Publisher{" +
                "publisherCode: " + publisherCode +
                ", name: " + name  +
                ", foundationYear: " + foundationYear +
                ", headquarters: " + headquarters  +
                ", boardGames: " + boardGames +  "}";
    }
}