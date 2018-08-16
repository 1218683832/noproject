package com.mrrun.module_view.slidingmenu.tantan;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.mrrun.module_view.Debug;

/**
 * 仿探探侧滑菜单栏
 *
 * @author lipin
 * @version 1.0
 * @date 2018/08/15
 */
public class SlidingMenu extends HorizontalScrollView {

    private Context mContext;
    /**
     * 最多子View个数
     */
    private static final int MAX_CHILD_COUNT = 2;
    /**
     * 侧边栏
     **/
    private View mSidebar;
    /**
     * 内容窗口
     **/
    private View mContentView;
    /**
     * 屏幕宽高
     */
    private int mScreenWidth, mScreenHeight;
    /**
     * 侧边栏所占屏幕比例
     */
    private static final float SLIDERBAR_WIDTH_SCALE = 0.85f;
    /**
     * 侧边栏宽
     */
    private int mSlidebarWidth;
    /**
     * ViewGroup是否拦截事件
     */
    private boolean mHasIntercept = false;
    /**
     * 侧边栏是否打开了
     */
    private boolean mHasMenuOpened = false;
    private GestureDetector mGestureDetector;

    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        Debug.D("init");
        initData(attrs);
        mGestureDetector = new GestureDetector(new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                Debug.D("onDown");
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                Debug.D("onShowPress");
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Debug.D("onSingleTapUp");
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Debug.D("onScroll");
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Debug.D("onLongPress");
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Debug.D("onFling velocityX=" + velocityX);
                if (mHasMenuOpened){
                    if (velocityX < 0){
                        closeMenu();
                    }
                } else {
                    if (velocityX > 0){
                        openMenu();
                    }
                }
                return true;
            }
        });
    }

    private void initData(AttributeSet attrs) {
        Debug.D("initData");
        getScreen();
    }

    private void getScreen() {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        mScreenWidth = displayMetrics.widthPixels;
        mScreenHeight = displayMetrics.heightPixels;
        mSlidebarWidth = (int) (mScreenWidth * SLIDERBAR_WIDTH_SCALE);
        Debug.D(String.format("mScreenWidth,mScreenHeight=(%d, %d) ", mScreenWidth, mScreenHeight));
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        mHasIntercept = false;
        if (mHasMenuOpened){
            float currentX = ev.getX();
            if (currentX >= mSlidebarWidth){
                return mHasIntercept = true;
            }
        }
        return mHasIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mGestureDetector.onTouchEvent(ev)){
            return true;
        }
        switch (ev.getAction()){
            case MotionEvent.ACTION_UP:
                int currentScollX = getScrollX();  // 当前滚动距离
                if (currentScollX < mSlidebarWidth / 2){
                    closeMenu();
                } else {
                    openMenu();
                }
                break;
        }
        return true;
    }

    private void closeMenu() {
        Debug.D("closeMenu");
        mHasMenuOpened = false;
        smoothScrollTo(mSlidebarWidth, 0);
    }

    private void openMenu() {
        Debug.D("openMenu");
        mHasMenuOpened = true;
        smoothScrollTo(0, 0);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
//        // 右边内容的缩放
//        float scale = 1f * l / mSlidebarWidth;// [1,0]
//        float rightScale = 0.7f + 0.3f * scale;
//        ViewCompat.setPivotX(mContentView, 0);
//        ViewCompat.setPivotY(mContentView, mContentView.getMeasuredHeight() / 2);
//        ViewCompat.setScaleX(mContentView, rightScale);
//        ViewCompat.setScaleY(mContentView, rightScale);
//
//        float leftAlpha = 0.5f + (1 - scale) * 0.5f;
//        float leftScale = 0.7f + (1 - scale) * 0.3f;
//        ViewCompat.setAlpha(mSidebar, leftAlpha);
//        ViewCompat.setScaleX(mSidebar, leftScale);
//        ViewCompat.setScaleY(mSidebar, leftScale);
//        ViewCompat.setTranslationX(mSidebar, 0.25f * l);
    }

    @Override
    protected void onFinishInflate() {
        Debug.D("onFinishInflate");
        super.onFinishInflate();
        ViewGroup childView = (ViewGroup) getChildAt(0);
        if (null == childView){
            throw new NullPointerException("SlidingMenu子View不能为空且只有一个");
        }
        int count = childView.getChildCount();
        if (count != MAX_CHILD_COUNT){
            throw new IllegalStateException("布局有问题");
        }
        mSidebar = childView.getChildAt(0);
        mContentView = childView.getChildAt(1);

        ViewGroup.LayoutParams sidebarParams = mSidebar.getLayoutParams();
        sidebarParams.width = mSlidebarWidth;
        mSidebar.setLayoutParams(sidebarParams);

        ViewGroup.LayoutParams contentViewParams = mContentView.getLayoutParams();
        contentViewParams.width = mScreenWidth;
        mContentView.setLayoutParams(contentViewParams);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        // ViewGroup里面的内容滑动
        scrollTo(mSlidebarWidth, 0);
    }
}
