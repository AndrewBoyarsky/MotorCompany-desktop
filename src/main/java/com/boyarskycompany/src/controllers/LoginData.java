package com.boyarskycompany.src.controllers;

/**
 * Created by zandr on 16.09.2016.
 */
public class LoginData {
    private boolean isValidated;
    private int number;

    public LoginData(boolean isValidated, int number) {
        this.isValidated = isValidated;
        this.number = number;
    }

    public boolean isValidated() {
        return isValidated;
    }

    public void setValidated(boolean validated) {
        isValidated = validated;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
