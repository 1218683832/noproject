package com.mrrun.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mrrun.module_view.R;
import com.mrrun.module_view.bethel.MessageBubbleView;
import com.mrrun.module_view.bethel.OnMessageBubbleTouchListener;

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

        MessageBubbleView.bindView(textView, new OnMessageBubbleTouchListener.OnViewDragDisappearListener() {
            @Override
            public void onDisappear(View originalView) {
                Toast.makeText(Bethel2Activity.this, "消失了1", Toast.LENGTH_LONG).show();
            }
        });

        MessageBubbleView.bindView(textView2, new OnMessageBubbleTouchListener.OnViewDragDisappearListener() {
            @Override
            public void onDisappear(View originalView) {
                Toast.makeText(Bethel2Activity.this, "消失了2", Toast.LENGTH_LONG).show();
            }
        });
    }
}
