package com.example.huawei.maquinadeturing.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huawei on 29/03/17.
 */

public class State implements Parcelable{

    private String stateName;
    private Map<String,String> stateToGo = new HashMap<String,String>();
    private Map<String,String> directions = new HashMap<String,String>();
    private Map<String,String> symbolToWrite = new HashMap<String,String>();
    private ArrayList<Rule> myRules;


    public State(String name){
        this.stateName = name;
        this.myRules = new ArrayList<>();
    }

    public void addRule(Rule rule){
        if(rule.getCurrentState().equals(this.stateName)){
            myRules.add(rule);
            updateHashMaps(rule);
        }
    }

    public void updateHashMaps(Rule rule){
        updateToGo(rule);
        updateDirections(rule);
        updateSymbolToWrite(rule);
    }

    private void updateToGo(Rule rule) {
        String currentSymbol = rule.getCurrentSymbol();
        String newState = rule.getNewState();

        stateToGo.put(currentSymbol, newState);
    }

    private void updateDirections(Rule rule) {
        String currentSymbol = rule.getCurrentSymbol();
        String direction = rule.getDirection();

        directions.put(currentSymbol, direction);
    }

    private void updateSymbolToWrite(Rule rule) {
        String currentSymbol = rule.getCurrentSymbol();
        String symbol = rule.getNewSymbol();

        symbolToWrite.put(currentSymbol, symbol);
    }

    public String nextStateReading(String symbolRead){
        if(stateToGo.containsKey(symbolRead)){
            String state = stateToGo.get(symbolRead);
            return state;
        }
        return "nothing";
    }

    public String directionReading(String symbolRead){
        if(directions.containsKey(symbolRead)){
            String direction = directions.get(symbolRead);
            return direction;
        }
        return "nothing";
    }

    public String writeSymbolReading(String symbolRead){
        if(directions.containsKey(symbolRead)){
            String symbol = symbolToWrite.get(symbolRead);
            return symbol;
        }
        return "nothing";
    }

    public Map<String, String> getStateToGo() {
        return stateToGo;
    }

    public String getStateName() {
        return stateName;
    }

    public ArrayList<Rule> getMyRules() {
        return myRules;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    protected State(Parcel in) {
        stateName = in.readString();
        if (in.readByte() == 0x01) {
            myRules = new ArrayList<Rule>();
            in.readList(myRules, Rule.class.getClassLoader());
        } else {
            myRules = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(stateName);
        if (myRules == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(myRules);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<State> CREATOR = new Parcelable.Creator<State>() {
        @Override
        public State createFromParcel(Parcel in) {
            return new State(in);
        }

        @Override
        public State[] newArray(int size) {
            return new State[size];
        }
    };

}
