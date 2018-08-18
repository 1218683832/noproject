package com.mrrun.module_view.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import com.mrrun.module_view.Debug;

/**
 * 汽车之家折叠效果
 *
 * @author lipin
 * @version 1.0
 * @date 2018/08/16
 */
public class VerticalDragListView extends FrameLayout {

    private Context mContext;
    private View mMenuView;
    private View mDragListView;
    private int mMenuBottom, mMenuTop;
    private boolean mMenuIsOpen = false;
    private boolean mHasIntercepted = false;
    private ViewDragHelper viewDragHelper;

    public VerticalDragListView(@NonNull Context context) {
        this(context, null);
    }

    public VerticalDragListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalDragListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        initData(attrs);
        viewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {

            /**
             * 此方法用于判断是否捕获当前child的触摸事件，可以指定ViewDragHelper移动哪一个子View
             * @param child
             * @param pointerId
             * @return
             */
            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                Debug.D(String.format("ViewDragHelper-tryCaptureView--->当前childView=%s, pointerId=%d", child.toString(), pointerId));
                return child == mDragListView;// 判断是否是列表view，否则不可以拖动
            }

            /**
             * clampViewPositionHorizontal() 和 clampViewPositionVertical()
             * 滑动方法，分别对应水平和垂直方向上的移动。要想子View移动，此方法必须重写实现！
             * @param child
             * @param top ViewDragHelper会将当前child的top值改变成返回的值(指定View移动到的位置,这是计算好了的，相当于left = child.getLeft() + dx)
             * @param dy 代表相较于上一次位置的增量
             * @return 指定View在水平（left）或垂直（top）方向上变成的值
             */
            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                Debug.D(String.format("ViewDragHelper-clampViewPositionVertical--->当前childView=%s, top=%d, dy=%d", child.toString(), top, dy));
                if (top <= mMenuTop) {
                    top = mMenuTop;
                } else if (top >= mMenuBottom) {
                    top = mMenuBottom;
                }
                return top;
            }

            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                Debug.D(String.format("ViewDragHelper-onViewReleased--->当前childView=%s, xvel=%f, yvel=%f", releasedChild.toString(), xvel, yvel));
                // 手指松开的时候两者选择之一，要么打开要么关闭
                if (releasedChild.getTop() >= mMenuBottom / 2){// 如果滑动超过一半则收手后自动定位到最底部
                    viewDragHelper.smoothSlideViewTo(releasedChild, releasedChild.getLeft(), mMenuBottom);
                    mMenuIsOpen = true;
                } else {// 如果滑动未超过一半则收手后自动定位到最顶部
                    viewDragHelper.smoothSlideViewTo(releasedChild, releasedChild.getLeft(), 0);
                    mMenuIsOpen = false;
                }
                // 仍需要刷新！
                ViewCompat.postInvalidateOnAnimation(VerticalDragListView.this);
            }
        });
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(viewDragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(VerticalDragListView.this);
        }
    }

    private void initData(AttributeSet attrs) {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mMenuTop = mMenuView.getTop();
        mMenuBottom = mMenuView.getBottom();
        Debug.D("mMenuTop = " + mMenuTop);
        Debug.D("mMenuBottom = " + mMenuBottom);
    }

    private float mDownY, mMoveY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 需要处理滑动冲突
        mHasIntercepted = false;
        if (mMenuIsOpen){
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mDownY = ev.getY();
                    if (mDownY <= mMenuBottom && mDownY >= mMenuTop){
                        mHasIntercepted = false;
                    } else {
                        mHasIntercepted = true;
                    }
                    break;
            }
        } else {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mDownY = ev.getY();
                    // 让ViewDragHelper处理一个完整事件
                    viewDragHelper.processTouchEvent(ev);
                    break;
                case MotionEvent.ACTION_MOVE:
                    mMoveY = ev.getY();
                    if ((mMoveY - mDownY) > 0 && !canChildScrollUp()) {// 向下滑动
                        // 向下滑动 && 滚动到了顶部，拦截不让ListView做处理
                        mHasIntercepted = true;//当move的时候才走向自己的触摸事件，则此时mDragHelper是没有down事件的
                    }
                    break;
            }
        }
        Debug.D("onInterceptTouchEvent--->mHasIntercepted=" + mHasIntercepted);
        return mHasIntercepted;
    }

    /**
     * 判断View是否滚动到了最顶部,还能不能向上滚
     * @return
     */
    public boolean canChildScrollUp() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mDragListView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mDragListView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(mDragListView, -1) || mDragListView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mDragListView, -1);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        Debug.D("onTouchEvent--->MotionEvent=" + event.getAction());
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int count = getChildCount();
        if (count != 2) {
            throw new IllegalStateException("子View只能是2个!");
        }
        mMenuView = getChildAt(0);
        mDragListView = getChildAt(1);
    }
}