package com.mrrun.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mrrun.module_view.R;
import com.mrrun.module_view.loadingview.FlowersLiveLoadingView;

public class FlowersLiveLoadingView1Activity extends AppCompatActivity {

    FlowersLiveLoadingView loadingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flowersliveloadingviewexample1);
        getSupportActionBar().setTitle(R.string.module_view);
        initView();
    }

    private void initView() {
        loadingView = findViewById(R.id.flowersliveloadingview);
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingView.startAnimation();
            }
        });
        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingView.stopAnimation();
            }
        });
        findViewById(R.id.pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingView.pauseAnimation();
            }
        });
        findViewById(R.id.resume).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingView.resumeAnimation();
            }
        });
    }
}
