package com.mrrun.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mrrun.module_view.R;
import com.mrrun.module_view.loadingview.LoadingLineView;

public class ProgressViewActivity extends AppCompatActivity {

    LoadingLineView loadingLineView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progressviewexample);
        getSupportActionBar().setTitle(R.string.module_view);
        initView();
    }

    private void initView() {
        loadingLineView = findViewById(R.id.loadingprogressview);
    }
}
