package com.teamtreehouse.jargame.engine;


import java.io.*;
import java.util.*;

public class UserList {
    //I chose a map so that the users and high scores could always be connected
    private Map<String, Integer> mUserList;

    public UserList() {
        mUserList = new HashMap<>();
    }

    //A map also allows for no two users to have the same name (case-sensitive however)
    public void addUser(User user) {
        mUserList.put(user.getName(), user.getHighScore());
    }

    //Imports users from previous game compilations
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
        } catch (FileNotFoundException e) {
            System.out.printf("Could not find file name \"%s\". A new file will be created after safely closing this game.%n",fileName);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    //Exports users to be used in future game compilations.
    //It warned of a MalformedFormatString, and I'm not sure why. So I suppressed it.
    @SuppressWarnings("MalformedFormatString")
    public void exportList(String fileName) {
        try (
                FileOutputStream fos = new FileOutputStream(fileName);
                PrintWriter writer = new PrintWriter(fos);
        ) {
            for (Map.Entry user : mUserList.entrySet()) {
                writer.printf("%s|%d%n",
                        user.getKey(),
                        user.getValue());

            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public Map<String, Integer> getUserList() {
        return mUserList;
    }

    //This method sorts the map into a leaderboard. (Future java versions HAVE to make this easier)
    public Map<String, Integer> leaderboard(Map<String, Integer> userList) {
        List<Map.Entry<String, Integer>> listOfUsers = new LinkedList<>(userList.entrySet());
        Collections.sort(listOfUsers, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        Map<String, Integer> sortedLeaderboard = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : listOfUsers) {
            sortedLeaderboard.put(entry.getKey(), entry.getValue());
        }
        return sortedLeaderboard;
    }

    public void removeUser(String name) {
        mUserList.remove(name);
    }

}
