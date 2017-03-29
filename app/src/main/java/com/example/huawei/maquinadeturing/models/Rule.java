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
