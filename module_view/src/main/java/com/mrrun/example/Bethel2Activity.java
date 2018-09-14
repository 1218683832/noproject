package com.mrrun.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mrrun.module_view.R;
import com.mrrun.module_view.bethel.BubbleViewUtls;

public class Bethel2Activity extends AppCompatActivity {

    private TextView textView;
    private Button textView2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bethel2example);
        getSupportActionBar().setTitle(R.string.module_view);
        initView();
    }

    private void initView() {
        textView = findViewById(R.id.tv);
        textView2 = findViewById(R.id.tv2);
        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                BubbleViewUtls.bindView(Bethel2Activity.this, textView);
                return false;
            }
        });
        textView2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                BubbleViewUtls.bindView(Bethel2Activity.this, textView2);
                return false;
            }
        });
    }
}
