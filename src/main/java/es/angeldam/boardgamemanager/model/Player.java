package es.angeldam.boardgamemanager.model;

/**
 * Class that stores the info about a player who participates on board games
 */
public class Player extends Person{

    /**
     * Full-equip constructor of the class (all attributes)
     * @param code Code of the player
     * @param name Name of the player
     * @param birthYear Year of birth of the player
     */
    public Player(int code, String name, int birthYear) {
        super(code, name, birthYear);
    }

    /**
     * Constructor of the class
     * @param name Name of the player
     * @param birthYear Year of birth of the player
     */
    public Player( String name, int birthYear) {
        super(name, birthYear);
    }
}