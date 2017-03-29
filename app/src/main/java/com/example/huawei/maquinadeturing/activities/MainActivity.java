package com.example.huawei.maquinadeturing.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.huawei.maquinadeturing.R;
import com.example.huawei.maquinadeturing.models.Profile;
import com.example.huawei.maquinadeturing.models.State;
import com.example.huawei.maquinadeturing.util.SessionManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String USER_INPUT = "INPUT";
    private final String MACHINE_STATES = "STATES";

    private Button btnRules;
    private Button btnInput;
    private EditText input;

    private ArrayList<State> mStates;

    private Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Teoria da Computação");

        input = (EditText) findViewById(R.id.input);
        btnRules = (Button) findViewById(R.id.rules);
        btnInput = (Button) findViewById(R.id.enter);

        onEnterInput();
        onClickRules();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(SessionManager.getInstance(getApplicationContext()).getUserSession() == null){
            SessionManager.getInstance(getApplicationContext()).startUserSession(new Profile());
            profile = SessionManager.getInstance(getApplicationContext()).getUserSession();
        }else{
            profile = SessionManager.getInstance(getApplicationContext()).getUserSession();
            mStates = profile.getStates();
        }

    }

    @Override
    public void onBackPressed() {
        SessionManager.getInstance(getApplicationContext()).stopUserSession();
        finish();
    }

    private void onEnterInput() {

        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mStates == null || mStates.size() == 0){
                    Toast.makeText(getApplicationContext(), "Antes de continuar, defina as regras da máquina.", Toast.LENGTH_LONG).show();
                }else{
                    redirectToExecute();
                }

            }
        });
    }

    public void onClickRules(){
        btnRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectToRules();
            }
        });
    }

    private void redirectToRules(){
        Intent intent = new Intent(this, RulesActivity.class);
        startActivityForResult(intent, 1);
    }

    private void redirectToExecute(){
        Intent intent = new Intent(this, ExecutingActivity.class);

        Bundle bundle = intent.getExtras();
        if(bundle == null){
            bundle = new Bundle();
        }
        bundle.putString(USER_INPUT, input.getText().toString());

        intent.putExtras(bundle);
        startActivity(intent);
    }
}
