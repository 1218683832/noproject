package com.mrrun.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mrrun.module_design.R;

public class BehaviorUcFoldActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ucfoldbehaivor);
        if (null != getSupportActionBar())
            getSupportActionBar().setTitle(R.string.module_design);
        initView();
    }

    private void initView() {
    }
}
