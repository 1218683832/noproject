package com.mrrun.module_view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {

    private ScrollChangeListener scrollChangeListener;

    public void setScrollChangeListener(ScrollChangeListener scrollChangeListener) {
        this.scrollChangeListener = scrollChangeListener;
    }

    public interface ScrollChangeListener{
        void onScroll(int l, int t, int oldl, int oldt);
    }

    public MyScrollView(Context context) {
        this(context, null);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (scrollChangeListener != null){
            scrollChangeListener.onScroll(l, t, oldl, oldt);
        }
    }
}
