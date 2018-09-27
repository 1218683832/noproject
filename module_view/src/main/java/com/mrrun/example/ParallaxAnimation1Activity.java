package com.mrrun.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mrrun.module_view.Debug;
import com.mrrun.module_view.R;
import com.mrrun.module_view.parallax.animation.ParallaxViewPager;

public class ParallaxAnimation1Activity extends AppCompatActivity {

    ParallaxViewPager mParallaxVp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parallaxanimation1example);
        getSupportActionBar().setTitle(R.string.module_view);
        initView();
    }

    private void initView() {
        mParallaxVp = findViewById(R.id.parallax_vp);
        mParallaxVp.setLayoutIds(this.getSupportFragmentManager(), new int[]{R.layout.fragment_page_first, R.layout.fragment_page_second,
                R.layout.fragment_page_third});
        Debug.D("ParallaxViewPager = " + mParallaxVp.toString());
    }
}
