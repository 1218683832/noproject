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
import android.view.View;

import com.mrrun.module_view.Debug;
import com.mrrun.module_view.R;
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
    private List<ParallaxFragment> mFragments;

    public ParallaxViewPager(@NonNull Context context) {
        this(context, null);
    }

    public ParallaxViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        mFragments = new ArrayList<>();
    }

    public void setLayoutIds(FragmentManager fm, int[] layoutIds){
        mFragments.clear();
        // 1.例化Fragment
        for (int layoutId : layoutIds) {
            ParallaxFragment fragment = new ParallaxFragment();
            Bundle bundle = new Bundle() ;
            bundle.putInt(ParallaxFragment.LAYOUT_ID_KEY, layoutId);
            fragment.setArguments(bundle);
            mFragments.add(fragment);
            Debug.D("实例化Fragment = " + fragment.toString());
        }
        Debug.D("mFragments = " + mFragments.toString());
        // 2.设置Adapter
        setAdapter(new ParallaxPagerAdapter(fm));
        // 3.监听滑动改变位移
        addOnPageChangeListener(new OnPageChangeListener() {
            /**
             * 从第一张图 滑动到 第二张图 滑动的过程
             * @param position 滚动时代表当前页数角标，滚动结束表示滚动停止时的页数角标
             * @param positionOffset 0 – 1 变化
             * @param positionOffsetPixels 从0到屏幕的宽度数值
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Debug.D("onPageScrolled--->positionOffset:" + positionOffset);
                Debug.D("onPageScrolled--->positionOffsetPixels:" + positionOffsetPixels);

                // 获取左边出去的fragment右边进来的fragment
                ParallaxFragment outFragment = mFragments.get(position);
                List<View> parallaxViews = outFragment.getParallaxViews();
                Debug.D("onPageScrolled--->outFragment:" + outFragment.toString());
                for (View parallaxView : parallaxViews) {
                    ParallaxTag tag = (ParallaxTag) parallaxView.getTag(R.id.parallax_tag);
                    Debug.D("onPageScrolled--->ParallaxTag:" + tag.toString());
                    Debug.D("onPageScrolled--->parallaxView:" + parallaxView.toString());

                    // 不管是scrollTo()还是scrollBy()其移动的本质都是View/ViewGroup中的内容。
                    // parallaxView.scrollTo((int) ((positionOffsetPixels) * tag.translationXOut), (int) ((positionOffsetPixels) * tag.translationYOut));

                    // 不同于TranslationX/TranslationY，移动的是View本身。
                    parallaxView.setTranslationX((-positionOffsetPixels) * tag.translationXOut);
                    parallaxView.setTranslationY((-positionOffsetPixels) * tag.translationYOut);
                    Debug.D("onPageScrolled--->parallaxView x translationXOut:" + (-positionOffsetPixels) * tag.translationXOut);
                    Debug.D("onPageScrolled--->parallaxView y translationYOut:" + (-positionOffsetPixels) * tag.translationYOut);
                    Debug.D("--->--->--->--->");
                }
                Debug.D("--->--->--->--->--->--->--->--->--->--->--->--->--->--->--->--->--->--->--->--->--->--->--->--->--->--->");
                try {
                    ParallaxFragment inFragment = mFragments.get(position + 1);
                    parallaxViews = inFragment.getParallaxViews();
                    for (View parallaxView : parallaxViews) {
                        ParallaxTag tag = (ParallaxTag) parallaxView.getTag(R.id.parallax_tag);
                        Debug.D("onPageScrolled--->ParallaxTag:" + tag.toString());

                        // parallaxView.scrollTo((int) ((getMeasuredWidth() - positionOffsetPixels) * tag.translationXIn), (int) ((getMeasuredWidth() - positionOffsetPixels) * tag.translationYIn));

                        parallaxView.setTranslationX((getMeasuredWidth() - positionOffsetPixels) * tag.translationXIn);
                        parallaxView.setTranslationY((getMeasuredWidth() - positionOffsetPixels) * tag.translationYIn);
                        Debug.D("onPageScrolled--->parallaxView x translationXIn:" + (-positionOffsetPixels) * tag.translationXIn);
                        Debug.D("onPageScrolled--->parallaxView y translationYIn:" + (-positionOffsetPixels) * tag.translationYIn);
                        Debug.D("--->--->--->--->");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // 已经切换完毕
            // 已经滑动到具体某一页，比如滑动到第一页、滑动到第二页、滑动到第三页
            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 视差Fragment Adapter
     */
    private class ParallaxPagerAdapter extends FragmentPagerAdapter{

        public ParallaxPagerAdapter(FragmentManager fm) {
            super(fm);
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
