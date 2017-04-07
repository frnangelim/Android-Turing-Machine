package com.example.huawei.maquinadeturing.models;

import java.util.ArrayList;

public class Profile {

    private ArrayList<State> mStates = new ArrayList<>();
    private ArrayList<Rule> mRules = new ArrayList<>();

    public Profile(){

    }

    public ArrayList<State> getStates() {
        return mStates;
    }

    public void setStates(ArrayList<State> mStates) {
        this.mStates = mStates;
    }

    public ArrayList<Rule> getRules() {
        return mRules;
    }

    public void setRules(ArrayList<Rule> mRules) {
        this.mRules = mRules;
    }
}
