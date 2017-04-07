package com.example.huawei.maquinadeturing.models;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

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


    /** Main method that execute the whole logic of the machine.
     *
     * @return status of the machine.
     */
    public int run(){
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

    /** Set the textViews when the machine accept
     *
     */
    private void accept(){
        mStatus.setVisibility(View.VISIBLE);
        mStatus.setText("Accepted!");
        mStatus.setTextColor(Color.parseColor("#008000"));

        setMachineStatus(1);
    }

    /** Set the textViews when the machine reject
     *
     */
    private void reject(){
        mStatus.setVisibility(View.VISIBLE);
        mStatus.setText("Rejected!");
        mStatus.setTextColor(Color.parseColor("#FF0000"));

        setMachineStatus(-1);
    }

    /** Return the current status of the machine
     *
     * @return -1,0,1
     */
    public int getMachineStatus(){
        return machineStatus;
    }

    /** Set the current status of the machine
     */
    private void setMachineStatus(int status){
        this.machineStatus = status;
    }

    /** Verify if any state has a rule that contains star symbol(*)
     *
     * @param rulesList
     * @return
     */
    private boolean containsStar(ArrayList<Rule> rulesList){
        for(int i = 0; i < rulesList.size(); i++){
            String symbol = rulesList.get(i).getCurrentSymbol();
            if(symbol.equals("*")){
                return true;
            }
        }
        return false;
    }

    /** Verify if any state contain a rule with tapeCurrentSymbol as key of its hashmaos
     *
     * @param rulesList
     * @param tapeCurrentSymbol
     * @return
     */
    private boolean containsRule(ArrayList<Rule> rulesList, String tapeCurrentSymbol){
        for(int i = 0; i < rulesList.size(); i++){
            String symbol = rulesList.get(i).getCurrentSymbol();
            if(symbol.equals(tapeCurrentSymbol)){
                return true;
            }
        }
        return false;
    }

    /** Move the tape to right or left
     *
     * @param direction
     */
    private void moveTape(String direction){
        if(direction.equalsIgnoreCase("R")){
            mTape.right();
        }else if(direction.equalsIgnoreCase("L")){
            mTape.left();
        }
    }

    /** Verify if a state exist with the param name.
     *
     * @param stateName
     * @return
     */
    private State findState(String stateName){
        for(int i = 0; i < mStates.size(); i ++){
            if(mStates.get(i).getStateName().equals(stateName)){
                return mStates.get(i);
            }
        }
        return null; // Próximo estado não existe.
    }


    /** Return the state q0.
     *
     * @return initial state
     */
    private State getInitialState(){
        for(int i = 0; i < mStates.size(); i++){
            if(mStates.get(i).getStateName().equalsIgnoreCase("q0")){
                return mStates.get(i);
            }
        }
        return null; // Isso nunca deve acontecer, deve-se garantir que existirá uma regra q0.
    }

}
