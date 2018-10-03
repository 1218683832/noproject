package com.mrrun.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mrrun.module_view.R;
import com.mrrun.module_view.loadingview.LoadingViewDuoYin;

public class LoadingViewDuoYinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadingviewduoyinexample);
        getSupportActionBar().setTitle(R.string.module_view);
        initView();
    }

    private void initView() {
        final LoadingViewDuoYin loadingViewDuoYin = findViewById(R.id.loadingviewduoyin);
        loadingViewDuoYin.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingViewDuoYin.startLoading();
            }
        }, 2000);
    }
}
