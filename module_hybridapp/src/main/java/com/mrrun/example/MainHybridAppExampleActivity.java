package com.mrrun.example;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mrrun.module_hybridapp.R;

public class MainHybridAppExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hybridappexample);
        getSupportActionBar().setTitle(R.string.module_hybridapp);
        intView();
    }

    private void intView() {
        uiWebviewLoadUrl();
        uiH5_Android_Interactive();
    }

    /**
     * H5JS与Android代码交互
     */
    private void uiH5_Android_Interactive() {
        findViewById(R.id.btn_uih5_android_interactive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainHybridAppExampleActivity.this, H5AndroidInteractiveActivity.class));
            }
        });
    }

    /**
     * 在WebView中加载网页
     */
    private void uiWebviewLoadUrl() {
        findViewById(R.id.btn_webview_loadurl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainHybridAppExampleActivity.this, WebviewLoadUrlActivity.class));
            }
        });
    }
}
