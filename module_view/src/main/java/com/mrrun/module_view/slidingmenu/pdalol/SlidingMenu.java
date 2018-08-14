package com.mrrun.module_view.slidingmenu.pdalol;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 仿掌上英雄联盟侧滑菜单栏
 *
 * 1、只允许向ViewGroup添加最多2个子View；
 *
 * @author lipin
 * @version 1.0
 * @date 2018/08/13
 */
public class SlidingMenu extends FrameLayout{

    private Context mContext;
    /**内容窗口**/
    private View mContentView;
    /**侧边栏**/
    private View mSidebar;
    /**最多子View个数**/
    private final int MAX_CHILDVIEW = 2;

    public SlidingMenu(@NonNull Context context) {
        super(context, null);
    }

    public SlidingMenu(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public SlidingMenu(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        initData(attrs);
    }

    private void initData(AttributeSet attrs) {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // 先判断ViewGroup中子View个数
        int count = this.getChildCount();
        if (count > MAX_CHILDVIEW){
            throw new IllegalStateException(String.format("子View不能大于%d个", MAX_CHILDVIEW));
        }
        if (count < MAX_CHILDVIEW) {
            throw new IllegalStateException(String.format("子View不能小于%d个", MAX_CHILDVIEW));
        }
        mSidebar = getChildAt(0);
        mContentView = getChildAt(1);
    }
}
