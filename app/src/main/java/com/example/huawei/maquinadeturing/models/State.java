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

    /**
     * Method that will add a new rule
     * @param rule Rule that will be added
     */
    public void addRule(Rule rule){
        if(rule.getCurrentState().equals(this.stateName)){
            myRules.add(rule);
            updateHashMaps(rule);
        }
    }

    /**
     * Method that will update the hashmaps
     * @param rule Rule that will be put on hashmaps
     */
    public void updateHashMaps(Rule rule){
        updateToGo(rule);
        updateDirections(rule);
        updateSymbolToWrite(rule);
    }

    /**
     * Method that will update next state hashmap
     * @param rule Rule that will be update
     */
    private void updateToGo(Rule rule) {
        String currentSymbol = rule.getCurrentSymbol();
        String newState = rule.getNewState();

        stateToGo.put(currentSymbol, newState);
    }

    /**
     * Method that will update next direction hashmap
     * @param rule Rule that will be update
     */
    private void updateDirections(Rule rule) {
        String currentSymbol = rule.getCurrentSymbol();
        String direction = rule.getDirection();

        directions.put(currentSymbol, direction);
    }

    /**
     * Method that will update the symbol that will be written
     * @param rule Rule that will be update
     */
    private void updateSymbolToWrite(Rule rule) {
        String currentSymbol = rule.getCurrentSymbol();
        String symbol = rule.getNewSymbol();

        symbolToWrite.put(currentSymbol, symbol);
    }

    /**
     * Method that will return next state
     * @param symbolRead String that will be received
     */
    public String nextStateReading(String symbolRead){
        if(stateToGo.containsKey(symbolRead)){
            String state = stateToGo.get(symbolRead);
            return state;
        }
        return "nothing";
    }

    /**
     * Method that returns the next direction according to a received symbol
     * @param symbolRead symbol read
     */
    public String directionReading(String symbolRead){
        if(directions.containsKey(symbolRead)){
            String direction = directions.get(symbolRead);
            return direction;
        }
        return "nothing";
    }

    /**
     * Method that returns the next symbol to write according to a received symbol
     * @param symbolRead symbol read
     */
    public String writeSymbolReading(String symbolRead){
        if(directions.containsKey(symbolRead)){
            String symbol = symbolToWrite.get(symbolRead);
            return symbol;
        }
        return "nothing";
    }

    /** Method that returns the map of statesToGo
     *
     * @return map of statesToGo
     */
    public Map<String, String> getStateToGo() {
        return stateToGo;
    }

    /** Method that returns the state name
     *
     * @return state name
     */
    public String getStateName() {
        return stateName;
    }

    /** Method that returns the arraylist of rules
     *
     * @return rules of the state
     */
    public ArrayList<Rule> getMyRules() {
        return myRules;
    }

    /** Method that sets the stateName
     *
     * @param stateName
     */
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
