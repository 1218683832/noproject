package com.mrrun.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mrrun.module_view.R;

/**
 * @author lipin
 * @version 1.0
 * @date 2016/06/25
 */
public class SearchViewExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchviewexample);
        getSupportActionBar().setTitle(R.string.module_view);
        initView();
    }

    private void initView() {
    }
}
