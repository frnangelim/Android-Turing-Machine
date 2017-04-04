package com.example.huawei.maquinadeturing.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by huawei on 28/03/17.
 */

public class Rule implements Parcelable {

    private String currentState;
    private String currentSymbol;
    private String newSymbol;
    private String direction;
    private String newState;

    public Rule(){

    }

    public Rule(String currentState, String currentSymbol, String newSymbol, String direction, String newState){
        this.currentState = currentState;
        this.currentSymbol = currentSymbol;
        this.newSymbol = newSymbol;
        this.direction = direction;
        this.newState = newState;
    }

    /**
     * Method that returns the current state
     * @return Current State
     */
    public String getCurrentState() {
        return currentState;
    }

    /**
     * Method that returns the new rule symbol
     * @return New Symbol
     */
    public String getNewSymbol() {
        return newSymbol;
    }

    /**
     * Method modifying the new rule symbol
     * @param newSymbol New symbol that will replace the current one
     */
    public void setNewSymbol(String newSymbol) {
        this.newSymbol = newSymbol;
    }

    /**
     * Method that returns the direction the rule should follow
     * @return Direction
     */
    public String getDirection() {
        return direction;
    }

    /**
     * A method that modifies the direction the rule should follow
     * @param direction New direction that will replace the current one
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * Returns the new rule state
     * @return New State
     */
    public String getNewState() {
        return newState;
    }

    /**
     * Modify the new current state
     * @param newState New state that will replace the current one
     */
    public void setNewState(String newState) {
        this.newState = newState;
    }

    /**
     * Metodo que retorna o simbolo atual
     * @return Current Symbol
     */
    public String getCurrentSymbol() {
        return currentSymbol;
    }

    /**
     * Method modifying the current symbol
     * @param currentSymbol Symbol that will replace the current symbol
     */
    public void setCurrentSymbol(String currentSymbol) {
        this.currentSymbol = currentSymbol;
    }

    /**
     * A method that modifies the current state
     * @param currentState State that will replace the current state
     */
    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    protected Rule(Parcel in) {
        currentState = in.readString();
        currentSymbol = in.readString();
        newSymbol = in.readString();
        direction = in.readString();
        newState = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(currentState);
        dest.writeString(currentSymbol);
        dest.writeString(newSymbol);
        dest.writeString(direction);
        dest.writeString(newState);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Rule> CREATOR = new Parcelable.Creator<Rule>() {
        @Override
        public Rule createFromParcel(Parcel in) {
            return new Rule(in);
        }

        @Override
        public Rule[] newArray(int size) {
            return new Rule[size];
        }
    };
}
