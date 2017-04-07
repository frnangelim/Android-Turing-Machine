package com.example.huawei.maquinadeturing.models;

import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;


public class Tape {

    private ArrayList<String> input;
    private int position;
    public final static String BLANK = "_";

    private TextView inputText;

    public Tape(String input, TextView inputText) {
        this.input = new ArrayList<>();
        this.position = 1;
        this.input.add(BLANK);
        String[] splitted = input.split("");
        for (int i = 1; i < splitted.length; i++) {
            this.input.add(splitted[i]);
        }
        this.input.add(BLANK);

        this.inputText = inputText;

        setInitialText();
    }

    /** This method will set the initial text of the textview, based on the input of the user.
     */
    private void setInitialText(){

        String first = "";
        String selected = "";
        String last = "";

        for(int i = 0; i < position; i++){
            first += input.get(i);
        }

        selected = "<font color='#008000'>" + input.get(position) +"</font>";

        for(int i = position+1; i < input.size(); i++){
            last += input.get(i);
        }

        inputText.setText(Html.fromHtml(first + selected + last));
    }

    /** Method that writes a symbol on tape.
     *
     * @param symbol
     */
    public void write(String symbol){
        if(position != 0 && position != input.size()-1){
            if(!symbol.equals("*")){
                input.remove(position);
                input.add(position, symbol);
            }
        }else{
            if(position == input.size()-1){
                input.remove(position);
                input.add(position, symbol);
                input.add(BLANK);
            }else{
                input.remove(position);
                input.add(position, symbol);
                input.add(0,BLANK);
            }

        }
    }

    /** Method to get the current symbol on tape.
     *
     * @return currentSymbol
     */
    public String currentCharacter() {
        return input.get(position);
    }

    /** Method to update the position of the tape
     *
     */
    public void left(){
        if(position != 0){
            position--;
        }
        setTextViews();
    }

    /** Method to update the position of the tape
     *
     */
    public void right(){
        position++;
        setTextViews();
    }

    /** This method will set the text of the textview, based on the current data.
     */
    private void setTextViews(){
        String first = "";
        String selected = "";
        String last = "";

        for(int i = 0; i < position; i++){
            first += input.get(i);
        }

        selected = "<font color='#008000'>" + input.get(position) +"</font>";

        for(int i = position+1; i < input.size(); i++){
            last += input.get(i);
        }
        inputText.setText(Html.fromHtml(first + selected + last));
    }

    /** This method return the input.
     *
     * @return
     */
    public ArrayList<String> getInput() {
        return input;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((input == null) ? 0 : input.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tape other = (Tape) obj;
        if (input == null) {
            if (other.input != null)
                return false;
        } else if (!input.equals(other.input))
            return false;
        return true;
    }

    @Override
    public String toString() {
        String tape = "";
        for(int i = 0; i < input.size(); i++){
            tape += input.get(i);
        }
        return tape;
    }
}
