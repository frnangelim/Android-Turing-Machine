package com.example.huawei.maquinadeturing.activities;

import android.os.Bundle;
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

import com.example.huawei.maquinadeturing.R;
import com.example.huawei.maquinadeturing.adapters.RulesAdapter;
import com.example.huawei.maquinadeturing.interfaces.OnEmptyRulesListener;
import com.example.huawei.maquinadeturing.models.Rule;

import java.util.ArrayList;

public class RulesActivity extends AppCompatActivity implements OnEmptyRulesListener, View.OnKeyListener {

    private RulesAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ImageView sadFace;
    private TextView sadMessage;
    private ArrayList<Rule> mRules = new ArrayList<>();

    private EditText setRules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Defina as regras");

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
            sadFace.setVisibility(View.VISIBLE);
            sadMessage.setVisibility(View.VISIBLE);
        }else{
            sadFace.setVisibility(View.INVISIBLE);
            sadMessage.setVisibility(View.INVISIBLE);
        }
    }

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
                        setRules.getText().clear(); // Limpa edit text
                        break;
                }
                return true;
            }

        }
        return false;

    }
}
