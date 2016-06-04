package com.teamtreehouse.jargame;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class GameEngine {
    private UserList userList;
    private Jar jar = new Jar();
    private Map<String, String> gameMenu;
    private Map<String, String> adminMenu;
    private Map<String, String> playerMenu;

    public GameEngine(UserList userList){
        this.userList=userList;
        gameMenu = new HashMap<>();
        adminMenu = new HashMap<>();
        playerMenu = new HashMap<>();
        gameMenu.put("1","Administrator - Edit settings for the jar and see high scores");
        gameMenu.put("2","Player - Guess how many items are in the jar!");
        gameMenu.put("3","Quit the game");
        adminMenu.put("1","Change the name of the item");
        adminMenu.put("2","Change the max amount of the item");
        adminMenu.put("3","View players and high scores");
        adminMenu.put("4","Go to users menu");
        adminMenu.put("5","Go back to the main menu");
        playerMenu.put("1","Start guessing and try to beat your high score");
        playerMenu.put("2","View your current high score");
        playerMenu.put("3","Change users");
        playerMenu.put("4","Go back to the main menu");
    }

    private String choiceExceptionCatcher(){
        String choice = "";
        try {
            choice = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return choice.trim().toLowerCase();
    }

    private String promptMainMenuChoice(){
        System.out.println("Welcome to the jar game! Here are the different kinds of users:");
        for(Map.Entry<String,String> option: gameMenu.entrySet()){
            System.out.printf("%s. %s %n",option.getKey(),option.getValue());
        }
        System.out.println();
        System.out.print("So? Who are ya? (Please enter the number)");
        return choiceExceptionCatcher();
    }

    private String promptPlayerChoice(User user) {
        System.out.printf("Welcome %s! Here are your options: %n%n",user.getName());
        for(Map.Entry<String,String> option: playerMenu.entrySet()){
            System.out.printf("%s: %s%n",option.getKey(),option.getValue());
        }
        System.out.println();
        System.out.print("What would you like to do? (Type the number): ");
        return choiceExceptionCatcher();
    }

    private String promptAdminChoice() {
        System.out.println("Welcome, administrator! Here's what you can do:");
        for(Map.Entry<String,String> option : adminMenu.entrySet()){
            System.out.printf("%s: %s %n",option.getKey(),option.getValue());
        }
        System.out.println();
        System.out.print("What would you like to do? (Type the number): ");
        return choiceExceptionCatcher();
    }

    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public void run() {
        if(jar.getName()==null){
            System.out.println("No jar has been set up yet!");
            setUpJar();
        }
        String choice = "";
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
                    break;
                default:
                    System.out.printf("Whoops! Looks like you chose an option that's not available." +
                            "Make sure you type the number of the choice you would like.%n%n");
                        }
                    } while (!choice.equals("3"));
            }

    private void adminMenuSwitch() {
        String choice = "";
        do {
            choice = promptAdminChoice();
            switch (choice) {
                case "1":
                    jar.setName();
                    System.out.printf("Item changed to %s!%n%n",jar.getName());
                    break;
                case "2":
                    if (jar.getName() != null) {
                        jar.setMaxAmount();
                        System.out.printf("Max amount of %s changed to %d.%n%n",jar.getName(),jar.getMaxAmount());
                    } else {
                        System.out.println("Whoops! The item name has not been set yet.");
                        System.out.println("Please change the item name first.");
                        System.out.printf("%n%n%n");
                    }
                    break;
                case "3":
                    System.out.println("Here is the high score leaderboard:");
                    for(Map.Entry<String,Integer> entry: leaderboard(usersWithHiScores()).entrySet()){
                        if(entry.getValue()!=-1) {
                            System.out.printf("%s ------ %d%n",entry.getKey(),entry.getValue());
                        }
                    }
                    System.out.printf("%n%nPlease press enter to continue.");
                    try {
                        br.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "4":
                    playerMenuSwitch();
                    break;
                case "5":
                    System.out.println("Thanks!");
                    break;
                default:
                    System.out.printf("Whoops! Looks like you chose an option that's not available." +
                                      "Make sure you type the number of the choice you would like.%n%n");
            }
        } while(!choice.equals("4")&&!choice.equals("5"));

    }

    private User selectUser() {
        System.out.println("The following players have already signed up to play:");
        for (User player : userList.getUserList()) {
            System.out.printf("%s%n", player.getName());
        }
        System.out.println("Please enter your name:");
        String name = "";
        try {
            name = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!usersWithHiScores().containsKey(name)) {
            userList.addUser(createUser(name));
            userList.exportList("Users");
        }
        return new User(name, usersWithHiScores().get(name));
    }

    private void playerMenuSwitch(){
        User user = selectUser();
        String choice = "";
        do{
            choice = promptPlayerChoice(user);
            switch(choice){
                case "1":
                    user.setHighScore(startGuessing(user));
                    break;
                case "2":
                    if(user.getHighScore() != -1) {
                        System.out.printf("Here's your current high score: %d%n" +
                                "Try to beat it!%n", user.getHighScore());
                    } else {
                        System.out.println("You haven't played yet! Select 1 to make your first guess.");
                    }
                    break;
                case "3":
                    user = selectUser();
                    break;
                case "4":
                    System.out.println("I hope you had fun!");
                    usersWithHiScores().put(user.getName(),user.getHighScore());
                    break;
                default:
                    System.out.printf("Whoops! Looks like you chose an option that's not available." +
                            "Make sure you type the number of the choice you would like.%n%n");
            }
        } while (!choice.equals("4"));
    }

    public void setUpJar() {
        jar.setName();
        jar.setMaxAmount();
    }

    private Map<String,Integer> usersWithHiScores () {
        Map<String,Integer> usersAndHiScores = new HashMap<>();
        try (
                FileInputStream fis = new FileInputStream("Users");
                BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] args = line.split("\\|");
                usersAndHiScores.put(args[0],Integer.parseInt(args[1]));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return usersAndHiScores;
    }

    //HUGE shoutout to Carter Page on stackoverflow.com for posting the way to do this:
    //Check out his page at http://stackoverflow.com/users/309596/carter-page.
    private Map<String,Integer> leaderboard(Map<String,Integer> userList){
        List<Map.Entry<String,Integer>> listOfUsers = new LinkedList<>(userList.entrySet());
        Collections.sort(listOfUsers, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        Map<String,Integer> sortedLeaderboard = new LinkedHashMap<>();
        for(Map.Entry<String,Integer> entry: listOfUsers){
            sortedLeaderboard.put(entry.getKey(),entry.getValue());
        }
        return sortedLeaderboard;
    }

    public int startGuessing(User user) {
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
        System.out.printf("That's correct! There were %d %s in the jar. It only took you %d guess(es)!%n", number, jar.getName(), numberOfGuesses);
        if(numberOfGuesses<user.getHighScore()||user.getHighScore()==-1){
            System.out.println("You set a new high score!");
            return numberOfGuesses;
        } else {
            System.out.printf("Unfortunately this did not beat your old high score of %d. Try again!%n",user.getHighScore());
            return user.getHighScore();
        }
    }

    public User createUser(String name){
        return new User(name,-1);
    }
}
