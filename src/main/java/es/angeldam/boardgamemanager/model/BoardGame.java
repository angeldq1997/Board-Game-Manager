package es.angeldam.boardgamemanager.model;

import es.angeldam.boardgamemanager.utils.Mechanic;

import java.util.HashSet;
import java.util.Objects;

public class BoardGame {
    private int code;
    private String name;
    private String principalGenre;
    private int minPlayers;
    private int maxPlayers;
    private int averageDuration;
    private String recommendedAge;
    private int publicationYear;
    private int ranking;
    private HashSet<Mechanic> mechanics;
    private HashSet<Author> authors;
    private HashSet<Illustrator> illustrators;
    private HashSet<Publisher> publishers;

    public BoardGame(String name, String principalGenre, int minPlayers, int maxPlayers,
                     int averageDuration, String recommendedAge, int publicationYear, int ranking,
                     HashSet<Mechanic> mechanics, HashSet<Author> authors, HashSet<Illustrator> illustrators){
        this.name = name;
        this.principalGenre = principalGenre;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.averageDuration = averageDuration;
        this.recommendedAge = recommendedAge;
        this.publicationYear = publicationYear;
        this.ranking = ranking;
        this.mechanics = mechanics;
        this.authors = authors;
        this.illustrators = illustrators;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BoardGame boardGame)) return false;
        return code == boardGame.code;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    @Override
    public String toString() {
        return "BoardGame:\n" +
                "code:" + this.code +
                ", name: " + this.name +
                ", principal genre: " + this.principalGenre +
                ", min Players: " + this.minPlayers +
                ", max Players: " + this.maxPlayers +
                ", average duration: " + this.averageDuration +
                ", recommended age: " + this.recommendedAge +
                ", publication year: " + this.publicationYear +
                ", ranking: " + this.ranking +
                ", mechanics:" + this.mechanics;
    }
}