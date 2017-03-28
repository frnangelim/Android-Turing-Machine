package com.example.huawei.maquinadeturing.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.huawei.maquinadeturing.R;

public class MainActivity extends AppCompatActivity {

    Button btnRules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Teoria da Computação");
        btnRules = (Button) findViewById(R.id.rules);
        onClickRules();
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
        startActivity(intent);
    }
}
