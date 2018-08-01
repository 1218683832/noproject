package com.mrrun.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mrrun.module_view.Debug;
import com.mrrun.module_view.R;
import com.mrrun.module_view.widget.LockPatternView;

public class LockPatternViewActivity extends AppCompatActivity {

    private LockPatternView lockPatternView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lockpatternviewexample);
        getSupportActionBar().setTitle(R.string.module_view);
        initView();
    }

    private void initView() {
        lockPatternView = findViewById(R.id.lockpatternview);
        lockPatternView.setOnLockListener(new LockPatternView.OnLockListener() {
            @Override
            public void onLockedPassword(String pwd) {
                Debug.D(String.format("pwd=%s", pwd));
            }
        });
    }
}
