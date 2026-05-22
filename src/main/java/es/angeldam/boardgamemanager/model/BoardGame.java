package es.angeldam.boardgamemanager.model;

import es.angeldam.boardgamemanager.utils.Difficulty;

import java.util.List;
import java.util.Objects;

/**
 * Class that stores the information of a board game in the app, it has several attributes which are on the database
 */
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

    /**
     * Full-equip constructor of the class
     * @param code Code of the board game, this is the identifier to locate quickly, it makes the board game unique for the database
     * @param name Name of the board game
     * @param minPlayers Minimum number of players of the board game
     * @param maxPlayers Maximum number of players of the board game
     * @param averageDuration Average duration of the board game
     * @param recommendedAge Recommended age of the board game
     * @param publicationYear Year of publication of the board game (normally the first release)
     * @param difficulty Difficulty of the game (low, medium, high)
     * @param ranking Ranking of the board game by the opinion of a lot of users
     * @param mechanics Mechanics of the board game that give information of the actions that could make a player with the game
     * @param designers Designers that make the board game (its mechanics, the playable loop, balance of the game itself, and other)
     * @param illustrators Illustrators that designs the board game (the pictures, manual, cards and other visual elements)
     * @param publishers Publishers that produce and distribute the game on different countries
     */
    public BoardGame(int code, String name, int minPlayers, int maxPlayers, int averageDuration, String recommendedAge, int publicationYear, Difficulty difficulty, int ranking, String mechanics, List<Designer> designers, List<Illustrator> illustrators, List<Publisher> publishers) {
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
        this.designers = designers;
        this.illustrators = illustrators;
        this.publishers = publishers;
    }

    /**
     * Constructor of the class
     * @param code Code of the board game, this is the identifier to locate quickly, it makes the board game unique for the database
     * @param name Name of the board game
     * @param minPlayers Minimum number of players of the board game
     * @param maxPlayers Maximum number of players of the board game
     * @param averageDuration Average duration of the board game
     * @param recommendedAge Recommended age of the board game
     * @param publicationYear Year of publication of the board game (normally the first release)
     * @param difficulty Difficulty of the game (low, medium, high)
     * @param ranking Ranking of the board game by the opinion of a lot of users
     * @param mechanics Mechanics of the board game that give information of the actions that could make a player with the game
     */
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

    /**
     * Full-equip constructor of the class
     * @param name Name of the board game
     * @param minPlayers Minimum number of players of the board game
     * @param maxPlayers Maximum number of players of the board game
     * @param averageDuration Average duration of the board game
     * @param recommendedAge Recommended age of the board game
     * @param publicationYear Year of publication of the board game (normally the first release)
     * @param difficulty Difficulty of the game (low, medium, high)
     * @param ranking Ranking of the board game by the opinion of a lot of users
     * @param mechanics Mechanics of the board game that give information of the actions that could make a player with the game
     * @param designers Designers that make the board game (its mechanics, the playable loop, balance of the game itself, and other)
     * @param illustrators Illustrators that designs the board game (the pictures, manual, cards and other visual elements)
     * @param publishers Publishers that produce and distribute the game on different countries
     */
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

    /**
     * Getter that obtains the code of the board game
     * @return the code of the board game which reveals the location on the database
     */
    public int getCode() {
        return code;
    }

    /**
     * Getter that obtains the name of the board game
     * @return the name of the board game
     */
    public String getName() {
        return name;
    }

    /**
     * Getter that obtains the minimum players of the board game
     * @return the minimum players of the board game
     */
    public int getMinPlayers() {
        return minPlayers;
    }

    /**
     * Getter that obtains the maximum players of the board game
     * @return the maximum players of the board game
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * Getter that obtains the average duration of the board game
     * @return the average duration of the board game
     */
    public int getAverageDuration() {
        return averageDuration;
    }

    /**
     * Getter that obtains the recommended age of the board game
     * @return the recommended age of the board game
     */
    public String getRecommendedAge() {
        return recommendedAge;
    }

    /**
     * Getter that obtains the publication year of the board game
     * @return the publication year of the board game
     */
    public int getPublicationYear() {
        return publicationYear;
    }

    /**
     * Getter that obtains the difficulty of the board game (in one of three levels)
     * @return the difficulty of the board game
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Getter that obtains the ranking of the board game
     * @return the ranking of the board game
     */
    public int getRanking() {
        return ranking;
    }

    /**
     * Getter that obtains the mechanics of the board game
     * @return the mechanics of the board game
     */
    public String getMechanics() {
        return mechanics;
    }

    /**
     * Getter that obtains the list of designers of the board game
     * @return the designers of the board game
     */
    public List<Designer> getDesigners() {
        return designers;
    }

    /**
     * Getter that obtains the list of illustrators of the board game
     * @return the list of illustrators of the board game
     */
    public List<Illustrator> getIllustrators() {
        return illustrators;
    }

    /**
     * Getter that obtains the list of publishers of the board game
     * @return the list of publishers of the board game
     */
    public List<Publisher> getPublishers() {
        return publishers;
    }

    /**
     * Setter that changes the list of designers of the board game
     */
    public void setDesigners(List<Designer> designers) {
        this.designers = designers;
    }

    /**
     * Setter that changes the list of illustrators of the board game
     */
    public void setIllustrators(List<Illustrator> illustrators) {
        this.illustrators = illustrators;
    }

    /**
     * Setter that changes the list of publishers of the board game
     */
    public void setPublishers(List<Publisher> publishers) {
        this.publishers = publishers;
    }

    /**
     * Method that takes all the designers of the board game
     * @return the list of the names of the designers
     */
    public String listDesigners() {
        String list = "";
        if (this.designers != null) {
            for (Designer d : designers) {
                if (d != null){
                    list += d.getName() + "  ";
                }
            }
        }
        return list;
    }

    /**
     * Method that takes all the illustrators of the board game
     * @return the list of the names of the illustrators
     */
    public String listIllustrator() {
        String list = "";
        if (this.illustrators != null) {
            for (Illustrator i : illustrators) {
                if (i != null){
                    list += i.getName() + "  ";
                }
            }
        }
        return list;
    }

    /**
     * Method that takes all the publishers of the board game
     * @return the list of the names of the publishers
     */
    public String listPublisher() {
        String list = "";
        if (this.publishers != null) {
            for (Publisher p : publishers) {
                if (p != null){
                    list += p.getName() + "  ";
                }
            }
        }
        return list;
    }

    /**
     * Method that appends all the data of the board game in one string
     * @return the string with all the board game data (with only the not designers, illustrators and publishers)
     */
    @Override
    public String toString() {
        String listAsociated = " ";
        if (this.designers != null){
            listAsociated += listDesigners() + " ";
        }
        if (this.illustrators != null){
            listAsociated += listIllustrator() + " ";
        }
        if (this.publishers != null){
            listAsociated += listPublisher() + " ";
        }
        return "BoardGame:\n" +
                    "code:" + this.code +
                    ", name: " + this.name +
                    ", min Players: " + this.minPlayers +
                    ", max Players: " + this.maxPlayers +
                    ", average duration: " + this.averageDuration +
                    ", recommended age: " + this.recommendedAge +
                    ", publication year: " + this.publicationYear +
                    ", ranking: " + this.ranking +
                    ", mechanics:" + this.mechanics + listAsociated;
    }
}