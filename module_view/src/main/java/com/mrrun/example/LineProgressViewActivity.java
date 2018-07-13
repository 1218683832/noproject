package com.mrrun.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mrrun.module_view.R;
import com.mrrun.module_view.progressbarview.LineNumberProgressView;

public class LineProgressViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lineprogressviewexample);
        getSupportActionBar().setTitle(R.string.module_view);
        initView();
    }

    private void initView() {
        LineNumberProgressView lineNumberProgressView = findViewById(R.id.linenumberprogressview);
        lineNumberProgressView.setTotal(0.88f);
        lineNumberProgressView.progressAnimation();
    }
}
