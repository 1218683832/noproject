package com.mrrun.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mrrun.module_view.R;

public class VerticalDragListViewActivity extends AppCompatActivity {

    private ListView listView;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verticaldraglistviewexample);
        getSupportActionBar().setTitle(R.string.module_view);
        //列表项的数据
        String[] strs = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, strs);
        initView();
    }

    private void initView() {
        listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
}
