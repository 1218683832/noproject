package com.mrrun.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mrrun.module_view.R;

public class Bethel3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bethel3example);
        getSupportActionBar().setTitle(R.string.module_view);
        initView();
    }

    private void initView() {
    }
}
