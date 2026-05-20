package es.angeldam.boardgamemanager.model;

import es.angeldam.boardgamemanager.utils.Difficulty;

import java.util.List;
import java.util.Objects;

public class BoardGame {
    private int code;
    private String name;
    private int minPlayers;
    private int maxPlayers;
    private int averageDuration;
    private String recommendedAge;
    private int publicationYear;
    private Difficulty difficulty;
    private int ranking;
    private String mechanics;
    private List<Designer> designers;
    private List<Illustrator> illustrators;
    private List<Publisher> publishers;

    public BoardGame(int code, String name, int minPlayers, int maxPlayers, int averageDuration, String recommendedAge, int publicationYear, Difficulty difficulty, int ranking, String mechanics) {
        this.code = code;
        this.name = name;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.averageDuration = averageDuration;
        this.recommendedAge = recommendedAge;
        this.publicationYear = publicationYear;
        this.difficulty = difficulty;
        this.ranking = ranking;
        this.mechanics = mechanics;
    }

    public BoardGame(String name, int minPlayers, int maxPlayers, int averageDuration, String recommendedAge, int publicationYear, Difficulty difficulty, int ranking, String mechanics, List<Designer> designers, List<Illustrator> illustrators, List<Publisher> publishers) {
        this.name = name;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.averageDuration = averageDuration;
        this.recommendedAge = recommendedAge;
        this.publicationYear = publicationYear;
        this.difficulty = difficulty;
        this.ranking = ranking;
        this.mechanics = mechanics;
        this.designers = designers;
        this.illustrators = illustrators;
        this.publishers = publishers;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getAverageDuration() {
        return averageDuration;
    }

    public String getRecommendedAge() {
        return recommendedAge;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int getRanking() {
        return ranking;
    }

    public String getMechanics() {
        return mechanics;
    }

    public List<Designer> getDesigners() {
        return designers;
    }

    public List<Illustrator> getIllustrators() {
        return illustrators;
    }

    public List<Publisher> getPublishers() {
        return publishers;
    }

    public void setDesigners(List<Designer> designers) {
        this.designers = designers;
    }

    public void setIllustrators(List<Illustrator> illustrators) {
        this.illustrators = illustrators;
    }

    public void setPublishers(List<Publisher> publishers) {
        this.publishers = publishers;
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
                ", min Players: " + this.minPlayers +
                ", max Players: " + this.maxPlayers +
                ", average duration: " + this.averageDuration +
                ", recommended age: " + this.recommendedAge +
                ", publication year: " + this.publicationYear +
                ", ranking: " + this.ranking +
                ", mechanics:" + this.mechanics;
    }
}