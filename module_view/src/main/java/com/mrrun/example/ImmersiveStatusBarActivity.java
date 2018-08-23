package com.mrrun.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mrrun.module_view.R;

public class ImmersiveStatusBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immersivestatusbarexample);
        getSupportActionBar().setTitle(R.string.module_view);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn_imm_t).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImmersiveStatusBarActivity.this, ImmersiveStatusBar1Activity.class));
            }
        });
    }
}
