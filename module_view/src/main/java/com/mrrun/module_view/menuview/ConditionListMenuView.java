package com.mrrun.module_view.menuview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * 多条件筛选菜单.
 * 条件列表菜单视图.
 * Adapter模式适应自定义布局
 *
 * @author lipin
 * @version 1.0
 * @date 2018/07/23
 */
public class ConditionListMenuView extends LinearLayout {

    /**
     * 动画执行时间
     */
    private static final long DURATION = 500;
    private Context mContext;
    private BaseListMenuViewAdapter mAdapter;
    /**
     * 承接Menu视图的顶部MenuBar
     */
    private LinearLayout mMenuBarView;
    /**
     * 顶部MenuBar之下的内容包裹视图，包裹着筛选结果内容、遮盖层、菜单内容
     */
    private FrameLayout mContentView;
    /**
     * 遮盖层
     */
    private View mMask;
    /**
     * 遮罩颜色
     */
    private int maskColor = 0x88888888;
    /**
     * 菜单内容
     */
    private FrameLayout mMenuContentView;
    /**
     * 当前选中位置
     */
    private int mCurPosition = BaseListMenuViewAdapter.INVALID_POSITION;

    public ConditionListMenuView(Context context) {
        this(context, null);
    }

    public ConditionListMenuView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ConditionListMenuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        initData(attrs);
        iniView();
    }

    private void iniView() {
        // 整个布局设置为垂直方向
        setOrientation(LinearLayout.VERTICAL);

        /**
         * 顶部MenuBar
         */
        mMenuBarView = new LinearLayout(mContext);
        // 宽度填满，高度随子布局
        mMenuBarView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mMenuBarView.setOrientation(LinearLayout.HORIZONTAL);

        /**
         * 顶部MenuBar之下的内容包裹视图
         */
        mContentView = new FrameLayout(mContext);
        // 宽高填满
        mContentView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(mMenuBarView);
        addView(mContentView);

        /**
         * 遮盖层
         */
        mMask = new View(mContext);// 在后面setAdapter的时候再添加进包裹视图
        mMask.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mMask.setBackgroundColor(maskColor);
        mMask.setAlpha(0);
        mMask.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurPosition == BaseListMenuViewAdapter.INVALID_POSITION){
                    return;
                } else {
                    closeMenu(mMenuBarView.getChildAt(mCurPosition), mCurPosition);
                }
            }
        });

        /**
         * 菜单内容
         */
        mMenuContentView = new FrameLayout(mContext);// 在后面setAdapter的时候再添加进包裹视图
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mMenuContentView.setLayoutParams(params);
    }

    private void initData(AttributeSet attrs) {
    }

    public void setAdapter(BaseListMenuViewAdapter adapter) {
        if (null == adapter) {
            throw new IllegalArgumentException("adapter can not be null!");
        }
        mAdapter = adapter;
        for (int i = 0; i < mAdapter.getMenuCount(); i++) {
            /**
             * 将所有的Menu加入MenuBar
             */
            View menuView = mAdapter.getMenuView(i, mMenuBarView);
            int height = menuView.getLayoutParams().height;
            // 按权重平分宽度
            menuView.setLayoutParams(new LayoutParams(0, height, 1));
            mMenuBarView.addView(menuView, i);
            setMenuClick(menuView, i);

            /**
             * 将所有的MenuContent加入
             */
            View menuContent = mAdapter.getMenuContentView(i, mMenuContentView);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            menuContent.setLayoutParams(params);
            menuContent.setVisibility(GONE);
            mMenuContentView.addView(menuContent, i);
        }
        mContentView.addView(mMask, 0);
        mContentView.addView(mMenuContentView, 1);
    }

    /**
     * 设置菜单点击事件
     *
     * @param menuView
     * @param position
     */
    private void setMenuClick(final View menuView, final int position) {
        menuView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurPosition == BaseListMenuViewAdapter.INVALID_POSITION) {// 当前未选中任何Menu,打开菜单
                    openMenu(menuView, position);
                } else if (mCurPosition == position){// 当前点击的是相同的Menu,关闭菜单
                    closeMenu(menuView, position);
                } else {// 当前点击不同的Menu，切换菜单
                    changeMenu(menuView, position);
                }
            }
        });
    }

    /**
     * 切换菜单
     *
     * @param menuView
     * @param position
     */
    private void changeMenu(View menuView, int position) {
        closeMenu(mMenuBarView.getChildAt(mCurPosition), mCurPosition);
        openMenu(menuView, position);
    }

    /**
     * 关闭菜单
     *
     * @param menuView
     * @param position
     */
    private void closeMenu(View menuView, int position) {
        mCurPosition = BaseListMenuViewAdapter.INVALID_POSITION;
        mAdapter.closeMenu(menuView);
        mMenuContentView.getChildAt(position).setVisibility(GONE);
        mMask.setAlpha(0.0f);
//        maskHideAnimation().start();
    }

    /**
     * 打开菜单
     *
     * @param menuView
     * @param position
     */
    private void openMenu(View menuView, int position) {
        mCurPosition = position;
        mAdapter.openMenu(menuView);
        mMenuContentView.getChildAt(position).setVisibility(VISIBLE);
        mMask.setAlpha(1.0f);
//        maskShowAnimation().start();
    }

    private Animator maskHideAnimation(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(mMask, "alpha",1.0f, .0f);
        animator.setDuration(DURATION);
        return animator;
    }

    private Animator maskShowAnimation(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(mMask, "alpha",0.0f, 1.0f);
        animator.setDuration(DURATION);
        return animator;
    }

    protected final int sp2px(float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, mContext.getResources().getDisplayMetrics());
    }

    protected final int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, mContext.getResources().getDisplayMetrics());
    }
}
