package com.mrrun.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.mrrun.module_view.R;
import com.mrrun.module_view.tagview.TagLayout;
import com.mrrun.module_view.tagview.TagView;

public class TagActivity extends AppCompatActivity{

    private TagLayout tagLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagexample);
        initView();
    }

    private void initView() {
        tagLayout = findViewById(R.id.taglayout);
        tagLayout.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(String text) {
                Toast.makeText(TagActivity.this, "点击了Tag,内容是" + text, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTagLongClick(String text) {
                Toast.makeText(TagActivity.this, "长按Tag,内容是" + text, Toast.LENGTH_SHORT).show();
                tagLayout.removeAllTags();
            }
        });
        // 添加标签
        findViewById(R.id.btn_addtag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagLayout.addTag("TAGTAGTAG");
            }
        });
        // 移除/删除标签
        findViewById(R.id.btn_rmtag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
