package com.example.huawei.maquinadeturing.models;

/**
 * Created by huawei on 28/03/17.
 */

public class Rule {

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

    public String getCurrentState() {
        return currentState;
    }

    public String getNewSymbol() {
        return newSymbol;
    }

    public void setNewSymbol(String newSymbol) {
        this.newSymbol = newSymbol;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getNewState() {
        return newState;
    }

    public void setNewState(String newState) {
        this.newState = newState;
    }

    public String getCurrentSymbol() {
        return currentSymbol;
    }

    public void setCurrentSymbol(String currentSymbol) {
        this.currentSymbol = currentSymbol;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }
}
