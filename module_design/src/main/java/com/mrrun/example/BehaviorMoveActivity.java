package com.mrrun.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.mrrun.module_design.R;

public class BehaviorMoveActivity extends AppCompatActivity{

    private View moveView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movebehaivor);
        if (null != getSupportActionBar())
            getSupportActionBar().setTitle(R.string.module_design);
        initView();
    }

    private void initView() {
        moveView = findViewById(R.id.movebtn);
        moveView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_MOVE:
                        v.setX(event.getRawX() - moveView.getWidth() / 2);
                        v.setY(event.getRawY() - moveView.getHeight() / 2);
                        break;
                }
                return true;
            }
        });
    }
}
