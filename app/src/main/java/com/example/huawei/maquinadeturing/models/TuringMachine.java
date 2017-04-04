package com.example.huawei.maquinadeturing.models;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by huawei on 30/03/17.
 */

public class TuringMachine {

    private ArrayList<State> mStates;
    private Tape mTape;
    private TextView mStatus;
    private ImageView mStatusFace;
    private State mCurrentState;
    private TextView mCurrentStateText;

    private final int ACCEPTED = 1;
    private final int NOT_ACCEPTED = -1;
    private final int NOTHING_YET = 0;

    private int machineStatus = NOTHING_YET;

    private Context mContext;

    public TuringMachine(ArrayList<State> states, Tape tape, TextView status, Context ctx, TextView currentStateText){
        this.mStates = states;
        this.mTape = tape;
        this.mStatus = status;
        this.mContext = ctx;

        this.mCurrentStateText = currentStateText;

        mStatus.setVisibility(View.INVISIBLE);

        this.mCurrentState = getInitialState();
    }


    public int run(){ // Revisar!
        mCurrentStateText.setText("Estado atual: " + mCurrentState.getStateName());
        String tapeCurrentSymbol = mTape.currentCharacter();
        ArrayList<Rule> rulesList = mCurrentState.getMyRules();

        String direction;
        String symbolToWrite;
        String stateToGo;

        if(containsRule(rulesList, tapeCurrentSymbol)){
            direction = mCurrentState.directionReading(tapeCurrentSymbol);
            symbolToWrite = mCurrentState.writeSymbolReading(tapeCurrentSymbol);
            stateToGo = mCurrentState.nextStateReading(tapeCurrentSymbol);
        }else if(containsStar(rulesList)){
            direction = mCurrentState.directionReading("*");
            symbolToWrite = mCurrentState.writeSymbolReading("*");
            stateToGo = mCurrentState.nextStateReading("*");
        }else{
            reject();
            return NOT_ACCEPTED; // Nao existem regras para o simbolo lido.
        }

        mTape.write(symbolToWrite);
        moveTape(direction);
        tapeCurrentSymbol = mTape.currentCharacter();

        mCurrentState = findState(stateToGo);
        if (mCurrentState == null) {
            reject();
            return NOT_ACCEPTED; // Não reconhece a linguagem
        }

        if(mCurrentState.nextStateReading(tapeCurrentSymbol).equals("accept")){
            accept();
            return ACCEPTED;
        }else if(mCurrentState.nextStateReading(tapeCurrentSymbol).equals("rejected")){
            reject();
            return NOT_ACCEPTED;
        }else{
            setMachineStatus(0);
            return NOTHING_YET;
        }
    }

    private void accept(){
        mStatus.setVisibility(View.VISIBLE);
        mStatus.setText("Accepted!");
        mStatus.setTextColor(Color.parseColor("#008000"));

        setMachineStatus(1);
    }

    private void reject(){
        mStatus.setVisibility(View.VISIBLE);
        mStatus.setText("Rejected!");
        mStatus.setTextColor(Color.parseColor("#FF0000"));

        setMachineStatus(-1);
    }

//    private setTextViews(){
//        String first = "";
//        String selected = "";
//        String last = "";
//
//        for(int i = 0; i < position; i++){
//            first += input.get(i);
//        }
//
//        selected = "<font color='#008000'>" + input.get(position) +"</font>";
//
//        for(int i = position+1; i < input.size(); i++){
//            last += input.get(i);
//        }
//        inputText.setText(Html.fromHtml(first + selected + last));
//    }


    public int getMachineStatus(){
        return machineStatus;
    }

    private void setMachineStatus(int status){
        this.machineStatus = status;
    }

    private boolean containsStar(ArrayList<Rule> rulesList){
        for(int i = 0; i < rulesList.size(); i++){
            String symbol = rulesList.get(i).getCurrentSymbol();
            if(symbol.equals("*")){
                return true;
            }
        }
        return false;
    }

    private boolean containsRule(ArrayList<Rule> rulesList, String tapeCurrentSymbol){
        for(int i = 0; i < rulesList.size(); i++){
            String symbol = rulesList.get(i).getCurrentSymbol();
            if(symbol.equals(tapeCurrentSymbol)){
                return true;
            }
        }
        return false;
    }

    private void moveTape(String direction){
        if(direction.equalsIgnoreCase("R")){
            mTape.right();
        }else if(direction.equalsIgnoreCase("L")){
            mTape.left();
        }
    }

    private boolean nothingToDo(State currentState){
        ArrayList<Rule> myRules = currentState.getMyRules();
        for(int i = 0; i < myRules.size(); i++){
            Rule rule = myRules.get(i);
            if(rule.getCurrentSymbol().equals("*") || rule.getNewSymbol().equals("*")){ // Não lê nem escreve nada
                return true;
            }
        }
        return false;
    }

    private State findState(String stateName){
        for(int i = 0; i < mStates.size(); i ++){
            if(mStates.get(i).getStateName().equals(stateName)){
                return mStates.get(i);
            }
        }
        return null; // Próximo estado não existe.
    }

    private ArrayList<String> makeInput(String input){
        String[] array = input.split("");
        ArrayList<String> arrayInput = new ArrayList<>();

        for(int i = 0; i < array.length; i++){
            arrayInput.add(array[i]);
        }

        return arrayInput;
    }

    private State getInitialState(){
        for(int i = 0; i < mStates.size(); i++){
            if(mStates.get(i).getStateName().equalsIgnoreCase("q0")){
                return mStates.get(i);
            }
        }
        return null; // Isso nunca deve acontecer, deve-se garantir que existirá uma regra q0.
    }


    public ArrayList<State> getmStates() {
        return mStates;
    }

    public void setmStates(ArrayList<State> mStates) {
        this.mStates = mStates;
    }

    public Tape getmTape() {
        return mTape;
    }

    public void setmTape(Tape mTape) {
        this.mTape = mTape;
    }
}
