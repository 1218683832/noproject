package com.mrrun.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mrrun.module_view.R;
import com.mrrun.module_view.dialog.notification.NotificationDialog;

public class NotificationDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificationdialogxample);
        getSupportActionBar().setTitle(R.string.module_view);
        initView();
    }

    private void initView() {
        final NotificationDialog notificationDialog = new NotificationDialog(this, "网络通信出现问题，请稍后再试。");
        findViewById(R.id.btn_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != notificationDialog){
                    notificationDialog.show();
                }
            }
        });
        findViewById(R.id.btn_hide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != notificationDialog && notificationDialog.isShowing()){
                    notificationDialog.dismiss();
                }
            }
        });
    }
}
