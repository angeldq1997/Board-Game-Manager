package es.angeldam.boardgamemanager.model;

import java.util.List;

/**
 * Class that stores the info about a player who participates on board games
 */
public class Player extends Person{
    private List<Match> matchList;

    /**
     *
     * @param name
     * @param birthYear
     */
    public Player(String name, int birthYear) {
        super(name, birthYear);
    }

    /**
     *
     * @param code
     * @param name
     * @param birthYear
     */
    public Player(int code, String name, int birthYear) {
        super(code, name, birthYear);
    }
}