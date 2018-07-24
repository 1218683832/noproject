package com.mrrun.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mrrun.module_view.R;
import com.mrrun.module_view.menuview.ConditionListMenuView;
import com.mrrun.module_view.menuview.MenuViewAdapter;

public class ConditionListMenuViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conditionlistmenuviewexample);
        getSupportActionBar().setTitle(R.string.module_view);
        initView();
    }

    private void initView() {
        ConditionListMenuView menuView = findViewById(R.id.conditionlistmenuview);
        menuView.setAdapter(new MenuViewAdapter(this));
    }
}
