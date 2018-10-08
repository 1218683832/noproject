package com.mrrun.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mrrun.module_view.R;
import com.mrrun.module_view.loadingview.LoadingViewUC;

public class LoadingViewUCActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadingviewucexample);
        getSupportActionBar().setTitle(R.string.module_view);
        initView();
    }

    private void initView() {
        final LoadingViewUC loadingViewUC = findViewById(R.id.loadingviewuc);
        loadingViewUC.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingViewUC.loading();
            }
        },1000);
    }
}
