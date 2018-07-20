package com.mrrun.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mrrun.module_view.R;
import com.mrrun.module_view.widget.DropDownMenu;

import java.util.ArrayList;
import java.util.List;

public class DropDownMenuActivity extends AppCompatActivity {

    List<String> menuTitles;
    List<View> popupViews;
    TextView contentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropdownmenuexample);
        getSupportActionBar().setTitle(R.string.module_view);
        menuTitles = new ArrayList<String>();
        menuTitles.add("城市");
        menuTitles.add("年龄");
        menuTitles.add("星座");
        popupViews = new ArrayList<View>();
        popupViews.add(createView("popupView1"));
        popupViews.add(createView("popupView2"));
        popupViews.add(createView("popupView3"));
        contentView = (TextView) createView("Content");
        initView();
    }

    private View createView(String string) {
        TextView view = new TextView(this);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setGravity(Gravity.CENTER);
        view.setText(string);
        return view;
    }

    private void initView() {
        DropDownMenu dropdownmenu = findViewById(R.id.dropdownmenu);
        dropdownmenu.setMenus(menuTitles, popupViews, contentView);
    }
}
