package com.teamtreehouse.jargame;

//Huge shoutout to Carter Page and many others on stackoverflow.com

import com.teamtreehouse.jargame.engine.GameEngine;
import com.teamtreehouse.jargame.engine.UserList;

public class Game {

    //My main class
    public static void main(String[] args) {
        UserList users = new UserList();
        users.importList("Users");
        GameEngine game = new GameEngine(users);
        game.run();
        System.out.println("Saving users and high scores...");
        users.exportList("Users");
        System.out.println("Saving jar...");
    }
}