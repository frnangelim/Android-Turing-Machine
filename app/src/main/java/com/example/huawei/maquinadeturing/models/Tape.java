package com.example.huawei.maquinadeturing.models;

import android.text.Html;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by huawei on 30/03/17.
 */

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

    public void write(String symbol){
        if(position != 0 && position != input.size()){
            if(symbol.equals(BLANK)){
                input.remove(position);
            }
        }
        if(!symbol.equals("*")) {
            input.add(position, symbol);
        }
    }

    public String currentCharacter() {
        return input.get(position);
    }

    public void left() throws InterruptedException {
        position--;
        setTextViews();
    }

    public void right() throws InterruptedException {
        position++;
        setTextViews();
    }

    private void setTextViews() throws InterruptedException {
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

    public ArrayList<String> getInput() {
        return input;
    }

    public void setInput(ArrayList<String> input) {
        this.input = input;
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
