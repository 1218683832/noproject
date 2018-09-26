package com.mrrun.module_view.parallax.animation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.mrrun.module_view.Debug;
import com.mrrun.module_view.parallax.parallaxanimation1.ParallaxPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 视差ViewPager
 *
 * @author lipin
 * @version 1.0
 * @date 2018/09/26
 */
public class ParallaxViewPager extends ViewPager {

    private Context mContext;
    private ParallaxPagerAdapter mAdapter;

    public ParallaxViewPager(@NonNull Context context) {
        this(context, null);
    }

    public ParallaxViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public void setLayoutIds(FragmentManager fm, int[] layoutIds){
        mAdapter = new ParallaxPagerAdapter(fm, layoutIds);
        // 设置Adapter
        setAdapter(mAdapter);
    }

    /**
     * 视差Fragment Adapter
     */
    private class ParallaxPagerAdapter extends FragmentPagerAdapter{

        private List<Fragment> mFragments = new ArrayList<>();

        public ParallaxPagerAdapter(FragmentManager fm, int[] layoutIds) {
            super(fm);
            mFragments.clear();
            // 实例化Fragment
            for (int layoutId : layoutIds) {
                ParallaxFragment fragment = new ParallaxFragment();
                Bundle bundle = new Bundle() ;
                bundle.putInt(ParallaxFragment.LAYOUT_ID_KEY, layoutId);
                fragment.setArguments(bundle);
                mFragments.add(fragment);
                Debug.D("实例化Fragment = " + fragment.toString());
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
}
