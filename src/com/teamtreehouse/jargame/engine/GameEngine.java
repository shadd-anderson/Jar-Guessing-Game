package com.teamtreehouse.jargame.engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class GameEngine {
    private Jar jar = new Jar();
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private UserList userList;
    private Map<String, String> gameMenu;
    private Map<String, String> adminMenu;
    private Map<String, String> playerMenu;

    //Defines the various menu choices
    public GameEngine(UserList userList) {
        this.userList = userList;
        gameMenu = new HashMap<>();
        adminMenu = new HashMap<>();
        playerMenu = new HashMap<>();
        gameMenu.put("1", "Administrator - Edit settings for the jar and see high scores");
        gameMenu.put("2", "Player - Guess how many items are in the jar!");
        gameMenu.put("3", "Quit the game");
        adminMenu.put("1", "Change the name of the item");
        adminMenu.put("2", "Change the max amount of the item");
        adminMenu.put("3", "View players and high scores");
        adminMenu.put("4", "Delete a player");
        adminMenu.put("5", "Go to users menu");
        adminMenu.put("6", "Go back to the main menu");
        playerMenu.put("1", "Start guessing and try to beat your high score");
        playerMenu.put("2", "View your current high score");
        playerMenu.put("3", "Change users");
        playerMenu.put("4", "Go back to the main menu");
    }

    /*Since there's a lot of user-input, I put this in its own method to avoid code stink.
    I made it static so it can be used in other classes.*/
    public static String readLine() {
        String line = "";
        try {
            line = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    //Another case of avoiding code stink. This is for converting a guess into an int.
    public static int choiceToInt() {
        int guess = 0;
        try {
            //Splits the String from the user input into only numbers.
            guess = Integer.parseInt(readLine().replaceAll("[\\D]", ""));
        } catch (NumberFormatException nfe) {
            System.out.println("That's not a number!");
        }
        return guess;
    }

    //Code stink stinks
    private void enter() {
        System.out.println("Please press enter to continue");
        readLine();
    }

    //Sometimes code stink stinks really bad
    private String choiceTrimmer() {
        return readLine().trim().toLowerCase();
    }

    //Separate choice-making methods for the 3 different menus (Main, Admin, and User)
    private String promptMainMenuChoice() {
        System.out.println("Welcome to the jar game! Here are the different kinds of users:");
        for (Map.Entry<String, String> option : gameMenu.entrySet()) {
            System.out.printf("%s. %s %n", option.getKey(), option.getValue());
        }
        System.out.println();
        System.out.println("So? Who are ya? (Please enter the number)");
        return choiceTrimmer();
    }

    private String promptPlayerChoice(User user) {
        System.out.printf("%n%nWelcome %s! Here are your options: %n%n", user.getName());
        for (Map.Entry<String, String> option : playerMenu.entrySet()) {
            System.out.printf("%s: %s%n", option.getKey(), option.getValue());
        }
        System.out.println();
        System.out.println("What would you like to do? (Type the number): ");
        return choiceTrimmer();
    }

    private String promptAdminChoice() {
        System.out.printf("%n%nWelcome, administrator! The current jar is filled with a max of %d %s. Here's what you can do:%n",
                jar.getMaxAmount(), jar.getName());
        for (Map.Entry<String, String> option : adminMenu.entrySet()) {
            System.out.printf("%s: %s %n", option.getKey(), option.getValue());
        }
        System.out.println();
        System.out.println("What would you like to do? (Type the number): ");
        return choiceTrimmer();
    }

    //Starts up the main menu after first making sure a jar has already been set up. Sets one up if not
    public void run() {
        jar.importJar("Jar");
        if (jar.getName() == null) {
            System.out.println("No jar has been set up yet!");
            setUpJar();
        }
        String choice;
        do {
            choice = promptMainMenuChoice();
            switch (choice) {
                case "1":
                    adminMenuSwitch();
                    break;
                case "2":
                    playerMenuSwitch();
                    break;
                case "3":
                    System.out.println("Thanks for playing!");
                    jar.exportJar("Jar");
                    break;
                default:
                    System.out.printf("Whoops! Looks like you chose an option that's not available. " +
                            "Make sure you type the number of the choice you would like.%n%n");
            }
        } while (!choice.equals("3"));
    }

    //The various choices the admin can do.
    private void adminMenuSwitch() {
        String choice = "";
        do {
            choice = promptAdminChoice();
            switch (choice) {
                case "1":
                    System.out.printf("The current jar is filled with %s.%n", jar.getName());
                    jar.setName();
                    System.out.printf("Item changed to %s.%n%n", jar.getName());
                    enter();
                    break;
                case "2":
                    if (jar.getName() != null) {
                        System.out.printf("The current jar has a max of %d %s.%n", jar.getMaxAmount(), jar.getName());
                        jar.setMaxAmount();
                        System.out.printf("Max amount of %s changed to %d.%n%n", jar.getName(), jar.getMaxAmount());
                    } else {
                        System.out.println("Whoops! The item name has not been set yet.");
                        System.out.println("Please change the item name first.");
                        System.out.printf("%n%n%n");
                    }
                    enter();
                    break;
                case "3":
                    System.out.println("Here is the high score leaderboard:");
                    for (Map.Entry<String, Integer> entry : userList.leaderboard(userList.getUserList()).entrySet()) {
                        if (entry.getValue() != -1) {
                            System.out.printf("%s ------ %d%n", entry.getKey(), entry.getValue());
                        } else {
                            System.out.printf("%s has signed up but has not played yet.%n", entry.getKey());
                        }
                    }
                    enter();
                    break;
                case "4":
                    System.out.println("Please enter the name of the player you would like to delete:");
                    String name = readLine();
                    if (userList.getUserList().containsKey(name)) {
                        System.out.printf("Are you sure you'd like to delete %s?%n", name);
                        System.out.println("Please type 'yes' if you are sure. If not, please type 'no'.");
                        String confirmation = choiceTrimmer();
                        do {
                            switch (confirmation) {
                                case "yes":
                                    System.out.println("Please wait.....");
                                    userList.removeUser(name);
                                    System.out.printf("%s deleted!%n", name);
                                    break;
                                case "no":
                                    System.out.println("Returning to admin menu");
                                    break;
                                default:
                                    System.out.println("Please enter either 'yes' or 'no'");
                            }
                        } while (!confirmation.equals("yes") && !confirmation.equals("no"));
                    } else {
                        System.out.printf("User %s does not exist! Please enter a valid user. (Usernames are case-sensitive)", name);
                    }
                    enter();
                    break;
                case "5":
                    playerMenuSwitch();
                    break;
                case "6":
                    System.out.printf("Thanks!%n%n");
                    break;
                default:
                    System.out.printf("Whoops! Looks like you chose an option that's not available." +
                            "Make sure you type the number of the choice you would like.%n%n");
            }
        } while (!choice.equals("6") && !choice.equals("5"));

    }

    /*Method used to define current user. When first logging in, and when changing users,
    this method is called. It adds the user to the user list if it hasn't already*/
    private User selectUser() {
        System.out.printf("%n%nThe following players have already signed up to play:%n");
        for (Map.Entry player : userList.getUserList().entrySet()) {
            System.out.printf("%s%n", player.getKey());
        }
        System.out.printf("%nPlease enter your name: (Usernames are case-sensitive)%n");
        String name = readLine();
        if (!userList.getUserList().containsKey(name)) {
            userList.addUser(createUser(name));
            userList.exportList("Users");
        }
        return new User(name, userList.getUserList().get(name));
    }

    //Menu switch for the player
    private void playerMenuSwitch() {
        User user = selectUser();
        String choice;
        do {
            choice = promptPlayerChoice(user);
            switch (choice) {
                case "1":
                    user.setHighScore(startGuessing(user));
                    userList.addUser(user);
                    enter();
                    break;
                case "2":
                    if (user.getHighScore() != -1) {
                        System.out.printf("Here's your current high score: %d%n" +
                                "Try to beat it!%n", user.getHighScore());
                    } else {
                        System.out.println("You haven't played yet! Select 1 to make your first guess.");
                    }
                    enter();
                    break;
                case "3":
                    user = selectUser();
                    break;
                case "4":
                    System.out.printf("I hope you had fun!%n%n");
                    userList.addUser(user);
                    break;
                default:
                    System.out.printf("Whoops! Looks like you chose an option that's not available." +
                            "Make sure you type the number of the choice you would like.%n%n");
            }
        } while (!choice.equals("4"));
    }

    //Used if a jar isn't already created from earlier
    public void setUpJar() {
        jar.setName();
        jar.setMaxAmount();
    }

    //The actual guessing game itself
    public int startGuessing(User user) {
        int number = jar.fill();
        int guess = 0;
        int numberOfGuesses = 0;
        System.out.printf("Try to guess how many %s are in the jar! (There is a max of %d)%n", jar.getName(), jar.getMaxAmount());
        do {
            do {
                System.out.print("Guess: ");
                guess = choiceToInt();
                if (guess == 0) {
                    System.out.printf("Please enter a valid number between 1 and %s.%n", jar.getMaxAmount());
                }
            } while (guess == 0);
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
        /*Below lets the user know whether or not he beat his personal high score,
        as well as (at least tied) the global high score*/
        System.out.printf("That's correct! There were %d %s in the jar. It only took you %d guess(es)!%n", number, jar.getName(), numberOfGuesses);
        if (numberOfGuesses < user.getHighScore() || user.getHighScore() == -1) {
            System.out.println("You set a new personal high score!");
            if (numberOfGuesses <= (int) userList.leaderboard(userList.getUserList()).values().toArray()[0]
                    || ((int) userList.leaderboard(userList.getUserList()).values().toArray()[0]) == -1) {
                System.out.println("You set a new global high score!");
            } else {
                System.out.println("Unfortunately, however, this did not beat the global high score. Try again!");
            }
            return numberOfGuesses;
        } else {
            System.out.printf("Unfortunately this did not beat your old high score of %d. Try again!%n", user.getHighScore());
            return user.getHighScore();
        }
    }

    //When a new user is created, it auto-assigns a value of -1 to the high score. This is used as my "null"
    public User createUser(String name) {
        return new User(name, -1);
    }

}
