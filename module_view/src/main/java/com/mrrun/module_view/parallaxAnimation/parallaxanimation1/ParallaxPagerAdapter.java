package com.mrrun.module_view.parallaxAnimation.parallaxanimation1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mrrun.module_view.Debug;

import java.util.ArrayList;
import java.util.List;

public class ParallaxPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments = new ArrayList<>();

    public ParallaxPagerAdapter(FragmentManager fm, int[] layoutIds) {
        super(fm);
        mFragments.clear();
        for (int layoutId : layoutIds) {
            ParallaxFragment fragment = new ParallaxFragment();
            Bundle bundle = new Bundle() ;
            bundle.putInt(ParallaxFragment.LAYOUT_ID_KEY, layoutId);
            fragment.setArguments(bundle);
            mFragments.add(fragment);
            Debug.D("fragment = " + fragment.toString());
        }
        Debug.D("mFragments = " + mFragments.toString());
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
