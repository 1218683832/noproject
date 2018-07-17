package com.mrrun.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.mrrun.module_view.R;
import com.mrrun.module_view.widget.IndexSidebar;

public class IndexSidebarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indexsidebarexample);
        getSupportActionBar().setTitle(R.string.module_view);
        initView();
    }

    private void initView() {
        final TextView indexContent = findViewById(R.id.indexcontent);
        final IndexSidebar indexSidebar = findViewById(R.id.indexsidebar);
        indexSidebar.setOnTouchIndexSidebarListener(new IndexSidebar.OnTouchIndexSidebarListener() {
            @Override
            public void onSelecedIndex(String text, int position) {
                indexContent.setVisibility(View.VISIBLE);
                indexContent.setText(text);
            }

            @Override
            public void onFingerUp() {
                indexContent.setVisibility(View.GONE);
            }
        });
    }
}
