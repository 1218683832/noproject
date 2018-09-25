package com.mrrun.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.mrrun.module_view.Debug;
import com.mrrun.module_view.R;
import com.mrrun.module_view.parallaxAnimation.parallaxanimation1.ParallaxPagerAdapter;

public class ParallaxAnimation1Activity extends AppCompatActivity {

    ViewPager parallaxVp;
    ParallaxPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parallaxanimation1example);
        getSupportActionBar().setTitle(R.string.module_view);
        initView();
    }

    private void initView() {
        parallaxVp = findViewById(R.id.parallax_vp);

        adapter = new ParallaxPagerAdapter(getSupportFragmentManager(),
                new int[]{R.layout.fragment_page_first, R.layout.fragment_page_first, R.layout.fragment_page_first});
        Debug.D("adapter = " + adapter.toString());
        parallaxVp.setAdapter(adapter);
        Debug.D("parallaxVp = " + parallaxVp.toString());
    }
}
