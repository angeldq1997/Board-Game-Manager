package es.angeldam.boardgamemanager.model;

import es.angeldam.boardgamemanager.interfaces.StoreBoardGames;

import java.util.List;

public class Publisher implements StoreBoardGames {
    private int publisherCode;
    private String name;
    private int foundationYear;
    private String headquarters;
    private List<BoardGame> boardGames;

    public Publisher(int publisherCode, String name, int foundationYear, String headquarters, List<BoardGame> boardGames) {
        this.publisherCode = publisherCode;
        this.name = name;
        this.foundationYear = foundationYear;
        this.headquarters = headquarters;
        this.boardGames = boardGames;
    }

    public Publisher(int publisherCode, String name, int foundationYear, String headquarters) {
        this.publisherCode = publisherCode;
        this.name = name;
        this.foundationYear = foundationYear;
        this.headquarters = headquarters;
    }

    public Publisher(String name, int foundationYear, String headquarters) {
        this.name = name;
        this.foundationYear = foundationYear;
        this.headquarters = headquarters;
    }

    public int getCode() {
        return publisherCode;
    }

    public String getName() {
        return name;
    }

    public int getFoundationYear() {
        return foundationYear;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    public List<BoardGame> getBoardGames() {
        return boardGames;
    }

    public String listBoardGames() {
        String list = "";
        if (this.getBoardGames() != null) {
            for (BoardGame b : this.getBoardGames()) {
                list += b.getName() + "  ";
            }
        }
        return list;
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "publisherCode=" + publisherCode +
                ", name=" + name  +
                ", foundationYear=" + foundationYear +
                ", headquarters=" + headquarters  +
                ", boardGames=" + boardGames +  "}";
    }
}