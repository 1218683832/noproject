package com.mrrun.nbproject.ui.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mrrun.example.FloatWindowExampleActivity;
import com.mrrun.example.ShareExampleActivity;
import com.mrrun.nbproject.R;

/**
 * 主Activity
 * @author lipin
 * @date 2018/06/25
 * @version 1.0
 */
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        intView();
    }

    private void intView() {
        // View模块
        findViewById(R.id.btn_module_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        // 工具模块
        findViewById(R.id.btn_module_utils).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        // 分享模块
        findViewById(R.id.btn_module_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ShareExampleActivity.class));
            }
        });
        // 悬浮框模块
        findViewById(R.id.btn_module_floatview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, FloatWindowExampleActivity.class));
            }
        });
    }
}
