package com.mrrun.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.mrrun.module_view.R;
import com.mrrun.module_view.inputview.PayPsdInputView;

public class PayPsdInputViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypsdinputviewexample);
        getSupportActionBar().setTitle(R.string.module_view);
        initView();
    }

    private void initView() {
        PayPsdInputView inputView = findViewById(R.id.paypsdinputview);
        inputView.setOnPasswordInputListener(new PayPsdInputView.OnPasswordInputListener() {
            @Override
            public void finished(String psd) {
                Toast.makeText(PayPsdInputViewActivity.this, psd, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
