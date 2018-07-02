package com.mrrun.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mrrun.module_view.R;
import com.mrrun.module_view.bubblefloatview.BubbleFloatView;

public class BubbleFloatViewActivitiy extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bubblefloatviewexample);
        getSupportActionBar().setTitle(R.string.module_view);
        initView();
    }

    private void initView() {
        BubbleFloatView bubbleFloatView = findViewById(R.id.bubblefloatview);
        bubbleFloatView.startAnimation();
    }
}
