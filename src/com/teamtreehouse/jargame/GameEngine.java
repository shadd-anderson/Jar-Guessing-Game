package com.teamtreehouse.jargame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameEngine {
    private UserList userList;
    private Map<String, String> gameMenu;
    private Map<String, String> adminMenu;
    private Map<String, String> playerMenu;

    public GameEngine(UserList userList){
        this.userList=userList;
        gameMenu = new HashMap<>();
        adminMenu = new HashMap<>();
        playerMenu = new HashMap<>();
        gameMenu.put("administrator","Edit settings for the jar and see high scores");
        gameMenu.put("player","Guess how many items are in the jar!");
        adminMenu.put("1","Change the name of the ite.");
        adminMenu.put("2","Change the max amount of the item");
        adminMenu.put("3","View players and high scores");
        playerMenu.put("1","Start guessing and try to beat your high score");
        playerMenu.put("2","View your current high score");
        playerMenu.put("3","Change users");
    }

    Jar jar = new Jar();
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public void run() {
        String choice = "";
    }

    public void setUpJar() {
        jar.setName();
        jar.setMaxAmount();
    }

    public void startGuessing() {
        Random random = new Random();
        int number = random.nextInt(jar.getMaxAmount());
        int guess = 0;
        int numberOfGuesses = 0;
        System.out.printf("Try to guess how many %s are in the jar! (There is a max of %d)%n", jar.getName(), jar.getMaxAmount());
        do {
            try {
                System.out.printf("Guess: ");
                guess = Integer.parseInt(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (guess <= jar.getMaxAmount()) {
                numberOfGuesses++;
                if (guess != number) {
                    if (guess < number) {
                        System.out.println("Sorry! Incorrect. Guess higher this time!");
                    } else {
                        System.out.println("Sorry! Incorrect. Guess lower this time!");
                    }
                }
            } else {
                System.out.printf("Whoops! Make sure your guess is less than %d. That's the most that can fit!%n" +
                        "We haven't counted this as a guess. Try again!%n", jar.getMaxAmount());
            }
        } while (guess != number);
        System.out.printf("That's correct! There were %d %s in the jar. It only took you %d guess(es)!", number, jar.getName(), numberOfGuesses);
    }

    public User createUser() throws IOException {
        System.out.println("Please enter the user's name:");
        String name = br.readLine();
        return new User(name,-1);
    }
}
