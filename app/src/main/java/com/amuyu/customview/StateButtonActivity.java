package com.amuyu.customview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.amuyu.customview.view.button.SimpleStateButton;


public class StateButtonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_state_button);

        final SimpleStateButton button = (SimpleStateButton)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button.isIdleState()) {
                    button.completed();
                } else {
                    button.idled();
                }
            }
        });
    }
}
