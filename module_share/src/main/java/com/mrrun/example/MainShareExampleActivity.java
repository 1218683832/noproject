package com.mrrun.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mrrun.module_share.R;
import com.mrrun.module_share.wx.WXShareManager;
import com.mrrun.module_share.wx.WXScene;

/**
 * @author lipin
 * @version 1.0
 * @date 2016/06/25
 */
public class MainShareExampleActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shareexample);
        getSupportActionBar().setTitle(R.string.module_share);
        initView();
    }

    private void initView() {
        // 微信分享到朋友圈
        findViewById(R.id.btn_wxscenetimeline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WXShareManager.getInstance(MainShareExampleActivity.this).share(WXScene.WXSceneSession);
            }
        });
    }
}
