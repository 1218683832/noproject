package com.mrrun.module_view.parallax.parallaxanimation1;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

public class LevelScrollingPageTransformer implements ViewPager.PageTransformer {
    /**
     * 这种方式是基于ViewPager的，通过自定义其中的类，或者调整其中的方法来达到显示的效果。 
     * 在ViewPager中，有一个接口叫做PageTransformer，其中有一个方法transformPage,我们通过在这个方法中编写逻辑就可以实现视差显示。
     * 而之所以叫做视差显示，是因为实现这种方式是对不同的控件设置不同的速度来达到的。
     * 例如，ViewPager移动了10dp,　图片A移动了15dp, 图片B移动了7.5dp，这就产生了一个视差。
     * 本文来自 技术从未如此性感 的CSDN 博客 ，全文地址请点击：https://blog.csdn.net/u013749540/article/details/51282677?utm_source=copy
     * @param page
     * @param position
     */
    @Override
    public void transformPage(@NonNull View page, float position) {

    }
}
