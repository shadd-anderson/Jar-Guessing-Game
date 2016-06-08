package com.teamtreehouse.jargame.engine;

import java.io.*;
import java.util.Random;

public class Jar {
    private String mItemName;
    private int mItemMaxAmount;

    public void setName() {
        System.out.println("Please enter the item to be put in the jar:");
        mItemName = GameEngine.readLine();
    }

    public void setMaxAmount() {
        System.out.printf("Please enter the max amount of %s to be put in the jar:%n", mItemName);
        mItemMaxAmount = GameEngine.choiceToInt();
    }

    public String getName() {
        return mItemName;
    }

    public int getMaxAmount() {
        return mItemMaxAmount;
    }

    /*These two private voids are separate from the public ones about strictly for the
    purpose of importing/exporting the Jar for continued use.*/
    private void setName(String name) {
        mItemName = name;
    }
    private void setMaxAmount(int amount) {
        mItemMaxAmount = amount;
    }

    //Imports information from a file to supply the name and max amount from a previous jar
    public void importJar(String fileName) {
        try (
                FileInputStream fis = new FileInputStream(fileName);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] args = line.split("\\|");
                setName(args[0]);
                setMaxAmount(Integer.parseInt(args[1].replaceAll("[\\D]", "")));
            }
        } catch (FileNotFoundException e) {
            System.out.printf("Could not find file name \"%s\". A new file will be created after safely closing this game.%n",fileName);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    //Allows the jar info from the game to be exported and used for a later compilation of the game
    public void exportJar(String fileName) {
        try (
                FileOutputStream fos = new FileOutputStream(fileName);
                PrintWriter writer = new PrintWriter(fos);
        ) {
            writer.printf("%s|%d", getName(), getMaxAmount());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    //Gets the random number to be used for guessing
    public int fill() {
        Random random = new Random();
        return random.nextInt(getMaxAmount() - 1) + 1; //This causes the number to never be 0
    }
}

