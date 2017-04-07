package com.example.huawei.maquinadeturing.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huawei.maquinadeturing.R;
import com.example.huawei.maquinadeturing.adapters.RulesAdapter;
import com.example.huawei.maquinadeturing.interfaces.OnEmptyRulesListener;
import com.example.huawei.maquinadeturing.models.Profile;
import com.example.huawei.maquinadeturing.models.Rule;
import com.example.huawei.maquinadeturing.models.State;
import com.example.huawei.maquinadeturing.util.SessionManager;

import java.util.ArrayList;

public class RulesActivity extends AppCompatActivity implements OnEmptyRulesListener, View.OnKeyListener {

    private RulesAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ImageView sadFace;
    private TextView sadMessage;
    private ArrayList<Rule> mRules = new ArrayList<>();
    private ArrayList<State> mStates = new ArrayList<>();

    private EditText setRules;

    private final String MACHINE_STATES = "STATES";

    private Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Set your rules");

        if(SessionManager.getInstance(getApplicationContext()).getUserSession() == null){
            SessionManager.getInstance(getApplicationContext()).startUserSession(new Profile());
        }else{
            profile = SessionManager.getInstance(getApplicationContext()).getUserSession();
            mStates = profile.getStates();
            mRules = profile.getRules();
        }

        createRecyclerView();

        sadFace = (ImageView) findViewById(R.id.sad);
        sadMessage = (TextView) findViewById(R.id.sad_text);
        setRules = (EditText) findViewById(R.id.set_rule);

        setRules.setOnKeyListener(this);

        createAdapter();
    }

    private void createRecyclerView(){
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
    }


    private void createAdapter() {
        checkRulesSize();
        if(mAdapter == null) {
            mAdapter = new RulesAdapter(this, mRules, this);
        } else {
            mAdapter.notifyDataSetChanged();
        }

        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void checkRulesSize() {
        if(mRules.size() == 0){
            mRecyclerView.setVisibility(View.INVISIBLE);
            sadFace.setVisibility(View.VISIBLE);
            sadMessage.setVisibility(View.VISIBLE);
        }else{
            sadFace.setVisibility(View.INVISIBLE);
            sadMessage.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    // Metodo para quando apertar 'enter' no editText realizar ação.
    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {
        if (keyCode == EditorInfo.IME_ACTION_SEARCH ||
                keyCode == EditorInfo.IME_ACTION_DONE ||
                event.getAction() == KeyEvent.ACTION_DOWN &&
                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

            if (!event.isShiftPressed()) {
                Log.v("AndroidEnterKeyActivity","Enter Key Pressed!");
                switch (view.getId()) {
                    case R.id.set_rule:
                        validateRule(setRules.getText().toString());
                        setRules.getText().clear(); // Limpa edit text
                        break;
                }
                return true;
            }

        }
        return false;

    }

    private void validateRule(String input) {
        String rule = input;
        String[] array = rule.split("%");

        for (int i = 0; i < array.length; i++) {
            String[] currentRule = array[i].split(",");
            if (currentRule.length == 5) {
                if (currentRule[3].equalsIgnoreCase("L") || currentRule[3].equalsIgnoreCase("R") || currentRule[3].equals("*")) {
                    Rule newRule = new Rule();
                    newRule.setCurrentState(currentRule[0]);
                    newRule.setCurrentSymbol(currentRule[1]);
                    newRule.setNewSymbol(currentRule[2]);
                    newRule.setDirection(currentRule[3]);
                    newRule.setNewState(currentRule[4]);

                    mRules.add(newRule);
                    mAdapter.notifyDataSetChanged();
                    checkRulesSize(); // Verify if the rules are empty to change UI.

                    createState(newRule); // Important !
                } else {
                    Toast.makeText(this, "Digite uma regra válida!", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(this, "Digite uma regra válida!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void createState(Rule newRule) {
        String currentStateName = newRule.getCurrentState();
        boolean stateExist = false;
        for(int i = 0; i < mStates.size(); i++){
            if(mStates.get(i).getStateName().equals(currentStateName)){
                mStates.get(i).addRule(newRule);
                stateExist = true;
            }
        }
        if(!stateExist){
            State newState = new State(newRule.getCurrentState());
            newState.addRule(newRule);

            mStates.add(newState);
        }

        this.profile.setStates(mStates);
        this.profile.setRules(mRules);
        SessionManager.getInstance(getApplicationContext()).setUser(profile);

        mAdapter = new RulesAdapter(this, mRules, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        this.profile.setStates(mStates);
        this.profile.setRules(mRules);
        SessionManager.getInstance(getApplicationContext()).setUser(profile);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rules, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.delete_rules:
                alertDelete();
                return true;
            case R.id.joker:
                alertAddingRule();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void alertDelete(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Atenção!")
                .setMessage("Tem certeza que deseja apagar todas as regras?")
                .setPositiveButton("Apagar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteAll();
                    }
                })
                .setNegativeButton("Cancelar", null);

        builder.show();
    }

    private void deleteAll(){
        mRules = new ArrayList<>();
        mStates = new ArrayList<>();
        profile.setRules(mRules);
        profile.setStates(mStates);
        SessionManager.getInstance(getApplicationContext()).setUser(profile);
        createAdapter();
    }

    private void alertAddingRule(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Coringa!")
                .setMessage("Deseja adicionar as regras para que sua máquina reconheça" +
                        " a linguagem de palíndromos binários?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addFixedRules();
                    }
                })
                .setNegativeButton("Não", null);

        builder.show();
    }

    private void addFixedRules(){
        deleteAll();
        // This example program checks if the input string is a binary palindrome.
        // Input: a string of 0's and 1's, eg '1001001'

        validateRule("q0,0,_,r,1o");
        validateRule("q0,1,_,r,1i");
        validateRule("q0,_,_,*,accept");

        validateRule("1o,_,_,l,2o");
        validateRule("1o,*,*,r,1o");
        validateRule("1i,_,_,l,2i");
        validateRule("1i,*,*,r,1i");

        validateRule("2o,0,_,l,3");
        validateRule("2o,_,_,*,accept");
        validateRule("2o,*,*,*,reject");
        validateRule("2i,1,_,l,3");
        validateRule("2i,_,_,*,accept");
        validateRule("2i,*,*,*,reject");

        validateRule("3,_,_,*,accept");
        validateRule("3,*,*,l,4");
        validateRule("4,*,*,l,4");
        validateRule("4,_,_,r,q0");

        mAdapter = new RulesAdapter(this, mRules, this);
        mRecyclerView.setAdapter(mAdapter);

    }
}
