package com.mrrun.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mrrun.module_view.Debug;
import com.mrrun.module_view.R;
import com.mrrun.module_view.widget.MyScrollView;

public class ImmersiveStatusBar1Activity extends AppCompatActivity {

    private View adjustableView;
    private Toolbar toolbar;
    private int toolbarHeight;
    private int viewHeight;
    private MyScrollView scrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immersivestatusbar1example);
        initView();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setAlpha(0f);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.module_view);
        adjustableView = findViewById(R.id.adjustable_view);
        scrollView = findViewById(R.id.scrollview);
        scrollView.setScrollChangeListener(new MyScrollView.ScrollChangeListener() {
            @Override
            public void onScroll(int l, int t, int oldl, int oldt) {
                // Debug.D(String.format("l=%d, t=%d, oldl=%d, oldt=%d", l, t, oldl, oldt));
                float a = (float)t / (float)(viewHeight - toolbarHeight);
                if (a < 0) {
                    a = a;
                } else if (a > 1) {
                    a = 1;
                }
                Debug.D("a = " + a);
                toolbar.setAlpha(a);
            }
        });

        toolbar.post(new Runnable() {
            @Override
            public void run() {
                toolbarHeight = toolbar.getMeasuredHeight();
                Debug.D("toolbarHeight = " + toolbarHeight);
            }
        });
        adjustableView.post(new Runnable() {
            @Override
            public void run() {
                viewHeight = adjustableView.getMeasuredHeight();
                Debug.D("viewHeight = " + viewHeight);
            }
        });
    }
}
