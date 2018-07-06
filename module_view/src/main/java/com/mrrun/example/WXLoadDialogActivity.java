package com.mrrun.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mrrun.module_view.R;
import com.mrrun.module_view.dialog.load.WXLoadDialog;

public class WXLoadDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxloaddialogexample);
        getSupportActionBar().setTitle(R.string.module_view);
        initView();
    }

    private void initView() {
        final WXLoadDialog wxLoadDialog = new WXLoadDialog(this);
        findViewById(R.id.btn_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != wxLoadDialog){
                    wxLoadDialog.show();
                }
            }
        });
        findViewById(R.id.btn_hide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != wxLoadDialog && wxLoadDialog.isShowing()){
                    wxLoadDialog.dismiss();
                }
            }
        });
    }
}
