package com.teamtreehouse.jargame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

    public void setName() {
        System.out.println("Please enter your first name:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            name = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
}
