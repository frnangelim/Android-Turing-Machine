package com.example.huawei.maquinadeturing.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

    private ImageView playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executing);

        inputText = (TextView) findViewById(R.id.selected);
        status = (TextView) findViewById(R.id.status);
        playButton = (ImageView) findViewById(R.id.next);

        Bundle bundle = getIntent().getExtras();
        input = bundle.getString(USER_INPUT);
        profile = SessionManager.getInstance(getApplicationContext()).getUserSession();
        mStates = profile.getStates();

        Tape mTape = new Tape(input, inputText);
        final TuringMachine machine = new TuringMachine(mStates, mTape, status);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(machine.getMachineStatus() == 0){
                    try {
                        machine.run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
