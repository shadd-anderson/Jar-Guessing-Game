package com.teamtreehouse.jargame.engine;

public class User {
    private String name;
    private int highScore;

    public User(String name, int highScore) {
        this.name = name;
        this.highScore = highScore;
    }

    public String getName() {
        return name;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    /*There is no "setName" method here, because another method in the GameEngine allows for creation
    of a new user if one does not exist*/
}
