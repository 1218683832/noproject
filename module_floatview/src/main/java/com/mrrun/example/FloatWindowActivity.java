package com.mrrun.example;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mrrun.module_floatview.R;
import com.mrrun.module_floatview.nopermission.FloatViewWindowManager;
import com.mrrun.module_floatview.permission.CallFloatViewWindowManager;

/**
 * @author lipin
 * @version 1.0
 * @date 2016/06/25
 */
public class FloatWindowActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floatwindow);
        intView();
    }

    private void intView() {
        // 需要申请权限的
        findViewById(R.id.btn_show_or_apply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallFloatViewWindowManager.getInstance().applyOrShowFloatWindow(FloatWindowActivity.this);
            }
        });
        findViewById(R.id.btn_dismiss1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallFloatViewWindowManager.getInstance().dismissWindow();
            }
        });

        // 不需要申请权限的
        findViewById(R.id.btn_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatViewWindowManager.getInstance().showFloatView(FloatWindowActivity.this);
            }
        });
        findViewById(R.id.btn_dismiss2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatViewWindowManager.getInstance().dismissFloatView();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("onDestroy", "onDestroy");
    }
}
