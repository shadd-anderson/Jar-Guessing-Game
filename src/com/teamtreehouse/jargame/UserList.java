package com.teamtreehouse.jargame;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserList {
    private List<User> userList;

    public UserList() {
        userList = new ArrayList<>();
    }

    public void addUser(User user){userList.add(user);}

    public void importList(String fileName) {
        try (
                FileInputStream fis = new FileInputStream(fileName);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] args = line.split("\\|");
                addUser(new User(args[0], Integer.parseInt(args[1])));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void exportList(String fileName) {
        System.out.println("Saving users and high scores...");
        try (
                FileOutputStream fos = new FileOutputStream(fileName);
                PrintWriter writer = new PrintWriter(fos);
        ) {
            for (User user : userList) {
                writer.printf("%s|%d%n",
                        user.getName(),
                        user.getHighScore());

            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
