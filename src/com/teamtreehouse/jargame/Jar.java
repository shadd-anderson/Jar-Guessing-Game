package com.teamtreehouse.jargame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

    public int getMaxAmount() {
        return mItemMaxAmount;
    }


}
