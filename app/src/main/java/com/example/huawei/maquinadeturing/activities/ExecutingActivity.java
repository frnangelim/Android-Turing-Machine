package com.example.huawei.maquinadeturing.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huawei.maquinadeturing.R;
import com.example.huawei.maquinadeturing.models.Profile;
import com.example.huawei.maquinadeturing.models.State;
import com.example.huawei.maquinadeturing.models.Tape;
import com.example.huawei.maquinadeturing.models.TuringMachine;
import com.example.huawei.maquinadeturing.util.SessionManager;

import java.util.ArrayList;

public class ExecutingActivity extends AppCompatActivity{

    private String input;
    private ArrayList<State> mStates;
    private final String USER_INPUT = "INPUT";
    private Profile profile;
    private TextView status;

    private TextView inputText;

    private TextView currentStateText;

    private ImageView playButton;
    private ImageView stepButton;

    private final int NOTHING_YET = 0;

    private TuringMachine machine;
    private Tape mTape;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executing);

        inputText = (TextView) findViewById(R.id.selected);
        status = (TextView) findViewById(R.id.status);
        playButton = (ImageView) findViewById(R.id.play);
        stepButton = (ImageView) findViewById(R.id.next);

        currentStateText =  (TextView) findViewById(R.id.text_current_state);

        Bundle bundle = getIntent().getExtras();
        input = bundle.getString(USER_INPUT);
        profile = SessionManager.getInstance(getApplicationContext()).getUserSession();
        mStates = profile.getStates();

        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);


        mTape = new Tape(input, inputText);
        machine = new TuringMachine(mStates, mTape, status, this, currentStateText);

        onClickPlay();
        onClickStep();
    }

    private void onClickPlay() {
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                while(machine.getMachineStatus() == NOTHING_YET){
                    machine.run();
                }
            }
        });
    }

    private void onClickStep() {
        stepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(machine.getMachineStatus() == NOTHING_YET){
                    machine.run();
                }
            }
        });
    }


}
