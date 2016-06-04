package com.teamtreehouse.jargame;

import java.io.*;

public class Jar {
    private String mItemName;
    private int mItemMaxAmount;
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public void setName() {
        System.out.println("Please enter the item to be put in the jar:");
        try {
            mItemName = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMaxAmount() {
        System.out.printf("Please enter the max amount of %s to be put in the jar:%n", mItemName);
        try {
            mItemMaxAmount = Integer.parseInt(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                setMaxAmount(Integer.parseInt(args[1]));
            }
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
}

