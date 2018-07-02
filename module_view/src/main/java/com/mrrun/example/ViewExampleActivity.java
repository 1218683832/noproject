package com.mrrun.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mrrun.module_view.R;

/**
 * @author lipin
 * @version 1.0
 * @date 2016/06/25
 */
public class ViewExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewexample);
        getSupportActionBar().setTitle(R.string.module_view);
        initView();
    }

    private void initView() {
        uiSearchView();
        uiDialer();
        uiBubbleFloatView();
    }

    /**
     * 气泡浮动View及动画
     */
    private void uiBubbleFloatView() {
        findViewById(R.id.btn_bubblefloatview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewExampleActivity.this, BubbleFloatViewActivitiy.class));
            }
        });
    }

    /**
     * 拨号盘View
     */
    private void uiDialer() {
        findViewById(R.id.btn_dialer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewExampleActivity.this, DialerExampleActivity.class));
            }
        });
    }

    /**
     * 搜索View
     */
    private void uiSearchView() {
        findViewById(R.id.btn_searchview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewExampleActivity.this, SearchViewExampleActivity.class));
            }
        });
    }
}
