package com.teamtreehouse.jargame;


import java.io.*;
import java.util.*;

public class UserList {
    private List<User> mUserList;

    public UserList() {
        mUserList = new ArrayList<>();
    }

    public void addUser(User user) {
        mUserList.add(user);
    }

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
        try (
                FileOutputStream fos = new FileOutputStream(fileName);
                PrintWriter writer = new PrintWriter(fos);
        ) {
            for (Map.Entry user : usersWithHiScores(mUserList).entrySet()) {
                writer.printf("%s|%d%n",
                        user.getKey(),
                        user.getValue());

            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public List<User> getUserList() {
        return mUserList;
    }

    public Map<String, Integer> usersWithHiScores(List<User> userList) {
        Map<String, Integer> usersAndHiScores = new HashMap<>();
        for (User user : userList) {
            usersAndHiScores.put(user.getName(), user.getHighScore());
        }
        return usersAndHiScores;
    }

    //HUGE shoutout to Carter Page on stackoverflow.com for posting the way to do this:
    //Check out his page at http://stackoverflow.com/users/309596/carter-page.
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

}
