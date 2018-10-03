package com.mrrun.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mrrun.module_view.R;
import com.mrrun.module_view.widget.PrinterEffectTextView;

public class PrinterTextViewActivity extends AppCompatActivity {

    private PrinterEffectTextView printerEffectTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printertextviewexample);
        getSupportActionBar().setTitle(R.string.module_view);
        initView();
    }

    private void initView() {
        printerEffectTextView = findViewById(R.id.printertextview);
        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printerEffectTextView.text("打印你好打aaaa印你好打印你好打印你好打wwwbbb印你好打印你好打印你好打印你好打印你好")
                .printText();
            }
        });
    }
}
