package com.mrrun.module_design;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;

import java.lang.reflect.Field;

public class MyNestedScrollView extends NestedScrollView {

    private boolean canOverscroll = false;

    public MyNestedScrollView(@NonNull Context context) {
        super(context);
    }

    public MyNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int getScrollRange() {
        int scrollRange = 0;
        if (getChildCount() > 0) {
            View child = getChildAt(0);
            scrollRange = Math.max(0,
                    child.getHeight() - (getHeight() - getPaddingBottom() - getPaddingTop()));
        }
        return scrollRange;
    }


    /**
     * 是否可以在滚动
     *
     * @return
     */
    public boolean canOverscroll() {
        final int range = getScrollRange();
        Debug.D("range = " + range);
        final int mode = getOverScrollMode();
        canOverscroll = mode == OVER_SCROLL_ALWAYS
                || (mode == OVER_SCROLL_IF_CONTENT_SCROLLS && range > 0);

        try {
            Field field = this.getClass().getField("mScroller");
            field.setAccessible(true);
            Debug.D("mScroller = " + field);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return canOverscroll;
    }
}
