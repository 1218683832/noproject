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
public class MainViewExampleActivity extends AppCompatActivity {

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
        uiAffirmButtonView();
        uiTag();
        uiWXloadDialog();
        uiNotificationDialog();
        uiPayPsdInputView();
        uiProgressView();
        uiFlowersLiveLoadingView();
    }

    /**
     * 仿花束直播加载动画View
     */
    private void uiFlowersLiveLoadingView() {
        findViewById(R.id.btn_flowersliveloadingview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, FlowersLiveLoadingVIewActivity.class));
            }
        });
    }

    /**
     * 进度View
     */
    private void uiProgressView() {
        findViewById(R.id.btn_progressview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, ProgressViewActivity.class));
            }
        });
    }

    /**
     * 仿微信自定义支付密码输入框
     */
    private void uiPayPsdInputView() {
        findViewById(R.id.btn_paypsdinputview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, PayPsdInputViewActivity.class));
            }
        });
    }

    /**
     * 仿仿微信信息通知视图对话框
     */
    private void uiNotificationDialog() {
        findViewById(R.id.btn_notificationdialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, NotificationDialogActivity.class));
            }
        });
    }

    /**
     * 仿微信支付的加载视图对话框
     */
    private void uiWXloadDialog() {
        findViewById(R.id.btn_wxloaddialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, WXLoadDialogActivity.class));
            }
        });
    }

    /**
     * 自定义标签布局
     */
    private void uiTag() {
        findViewById(R.id.btn_taglayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, TagExampleActivity.class));
            }
        });
    }

    /**
     * 自定义确认按钮带动画效果
     */
    private void uiAffirmButtonView() {
        findViewById(R.id.btn_affirmbuttonview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, AffirmButtonViewActivity.class));
            }
        });
    }

    /**
     * 气泡浮动View及动画
     */
    private void uiBubbleFloatView() {
        findViewById(R.id.btn_bubblefloatview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, BubbleFloatViewActivitiy.class));
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
                startActivity(new Intent(MainViewExampleActivity.this, DialerExampleActivity.class));
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
                startActivity(new Intent(MainViewExampleActivity.this, SearchViewExampleActivity.class));
            }
        });
    }
}
