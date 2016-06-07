package com.teamtreehouse.jargame;

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

    private void setName(String name) {
        mItemName = name;
    }

    public int getMaxAmount() {
        return mItemMaxAmount;
    }

    private void setMaxAmount(int amount) {
        mItemMaxAmount = amount;
    }

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

    public int fill() {
        Random random = new Random();
        int number = random.nextInt(getMaxAmount() - 1) + 1; //This causes the number to never be 0
        return number;
    }
}

