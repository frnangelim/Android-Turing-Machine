package com.example.huawei.maquinadeturing.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.huawei.maquinadeturing.R;

public class ExecutingActivity extends AppCompatActivity {

    private String input;
    private final String USER_INPUT = "INPUT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executing);

        Bundle bundle = getIntent().getExtras();
        input = bundle.getString(USER_INPUT);
        Log.d("sddadas", input);
    }
}
