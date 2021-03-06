package com.mrrun.nbproject.ui.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.mrrun.example.MainDesignExampleActivity;
import com.mrrun.example.MainHybridAppExampleActivity;
import com.mrrun.example.MainShareExampleActivity;
import com.mrrun.example.MainViewExampleActivity;
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
        // 混合开发模块(H5与App)
        findViewById(R.id.btn_module_hybridapp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, MainHybridAppExampleActivity.class));
            }
        });
        // View模块
        findViewById(R.id.btn_module_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, MainViewExampleActivity.class));
            }
        });
        // Design模块
        findViewById(R.id.btn_module_design).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, MainDesignExampleActivity.class));
            }
        });
        // 工具模块
        findViewById(R.id.btn_module_utils).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "未加入", Toast.LENGTH_SHORT).show();
            }
        });
        // 分享模块
        findViewById(R.id.btn_module_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, MainShareExampleActivity.class));
            }
        });
        // 悬浮框模块
        findViewById(R.id.btn_module_floatview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, MainDesignExampleActivity.class));
            }
        });
    }
}
