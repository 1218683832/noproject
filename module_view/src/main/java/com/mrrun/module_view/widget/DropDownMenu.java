package com.mrrun.module_view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mrrun.module_view.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 常用多条件筛选菜单.
 * 分两个部分：顶部的菜单栏、其余部分为内容显示区；
 * 内容显示区包括三部分：点击菜单下拉弹出来的内容、真正显示的内容、以及之间的半透明背景。
 *
 * @author lipin
 * @version 1.0
 * @date 2018/07/19
 */
public class DropDownMenu extends LinearLayout {

    private Context mContext;
    /**
     * 顶部菜单选项栏目
     */
    private LinearLayout mMenuBar;
    /**
     * 底部内容包裹,包含popupMenuViews，maskView
     */
    private FrameLayout mContainerView;
    /**
     * 菜单选项下拉内容包裹，弹出菜单父布局
     */
    private FrameLayout mPopupContainerViews;
    /**
     * 遮罩半透明View，点击可关闭DropDownMenu
     */
    private View mMaskView;
    /**
     * 菜单选项数量
     */
    private static int MENU_COUNT = 0;
    //遮罩颜色
    private int maskColor = 0x88888888;
    /**
     * AttributeSet
     * 每个菜单高度
     */
    private int mMenuHeight = 40;
    /**
     * AttributeSet
     * 每个菜单文字大小
     */
    private int mMenuTextSize = 15;

    public static final int NOSCROLL = 0;
    public static final int SCROLL = 1;
    private int mMenuBarSile = NOSCROLL;

    /**
     * 当前选中菜单位置
     */
    private int mCurMenuPosition = -1;

    private List<String> mMenuTitles = new ArrayList<String>();

    public DropDownMenu(Context context) {
        this(context, null);
    }

    public DropDownMenu(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropDownMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        iniData(attrs);
        initView();
    }

    private void initView() {
        initRootView();
        switch (mMenuBarSile) {
            case NOSCROLL:
                createMenuBarToParent(this);
                break;
            case SCROLL:
                addMenuBarToParent(this);
                break;
        }
        createContainerViewToParent(this);
        createMaskView();
        createPopupContainerView();
    }

    private void createPopupContainerView() {
        mPopupContainerViews = new FrameLayout(mContext);
        mPopupContainerViews.setVisibility(GONE);
//        mPopupContainerViews.setBackgroundColor(getResources().getColor(R.color.color_00ffff));
        mPopupContainerViews.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private void createMaskView() {
        mMaskView = new View(mContext);
        mMaskView.setVisibility(GONE);
        mMaskView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        mMaskView.setBackgroundColor(maskColor);
        mMaskView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
            }
        });
    }

    /**
     * 关闭菜单
     */
    private void closeMenu() {
        if (mCurMenuPosition != -1){
            mPopupContainerViews.getChildAt(mCurMenuPosition).setVisibility(GONE);
//            mPopupContainerViews.setVisibility(View.GONE);
            mPopupContainerViews.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.popmenu_out));
            mMaskView.setVisibility(GONE);
            mMaskView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.mask_out));
            mCurMenuPosition = -1;
        }
    }

    private void createContainerViewToParent(ViewGroup parent) {
        mContainerView = new FrameLayout(mContext);
//        mContainerView.setBackgroundColor(getResources().getColor(R.color.color_77b300));
        mContainerView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        parent.addView(mContainerView);
    }

    public void setMenus(List<String> menuTitles, List<View> popupViews, View contentView) {
        if (menuTitles.size() != popupViews.size()) {
            throw new IllegalArgumentException("params not match, menuTitles.size() should be equal popupViews.size()!");
        }
        mMenuTitles = menuTitles;
        MENU_COUNT = mMenuTitles.size();
        for (int i = 0; i < MENU_COUNT; i++) {
            createMenus(menuTitles.get(i));
            popupViews.get(i).setVisibility(GONE);
            popupViews.get(i).setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mPopupContainerViews.addView(popupViews.get(i));
        }
        mContainerView.addView(contentView,0);
        mContainerView.addView(mMaskView,1);
        mContainerView.addView(mPopupContainerViews, 2);
    }

    /**
     * 生成菜单栏将菜单栏添加到内容中
     *
     * @param parent
     */
    private void createMenuBarToParent(ViewGroup parent) {
        /**
         * 生成菜单栏
         */
        mMenuBar = new LinearLayout(mContext);
//        mMenuBar.setBackgroundColor(getResources().getColor(R.color.color_00ff99));
        // MenuBar高度取决于R.layout.dropdownmenu_menu布局里面给Menu设置的高度
        LayoutParams menuBarParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        menuBarParams.setLayoutDirection(LinearLayout.HORIZONTAL);
        mMenuBar.setLayoutParams(menuBarParams);
        parent.addView(mMenuBar);
    }

    /**
     * 生成菜单
     */
    private void createMenus(final String meneTitle) {
        final TextView menu = new TextView(mContext);
        menu.setText(meneTitle);
        menu.setBackgroundColor(Color.GRAY);
        menu.setGravity(Gravity.CENTER);
        menu.setLayoutParams(new LayoutParams(0, mMenuHeight, 1.0f));
        menu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, meneTitle, Toast.LENGTH_SHORT).show();
                switchMenu(menu);
            }
        });
        mMenuBar.addView(menu);
    }

    /**
     * 切换菜单
     *
     * @param target
     */
    private void switchMenu(View target) {
        if (mCurMenuPosition == -1){// 初次点击菜单,将菜单选项下拉内容视图置为可见
            mPopupContainerViews.setVisibility(View.VISIBLE);
            mMaskView.setVisibility(VISIBLE);
        } else {// 切换菜单之前将之前显示的菜单选项下拉内容视图隐藏
            mPopupContainerViews.getChildAt(mCurMenuPosition).setVisibility(GONE);
        }
        // 判断是哪个菜单被选中了,显示选中的菜单选项下拉内容视图
        for (int i = 0; i < mMenuBar.getChildCount(); i++) {
            if (target == mMenuBar.getChildAt(i)){
                mCurMenuPosition = i;
                mPopupContainerViews.getChildAt(i).setVisibility(VISIBLE);
            }
        }
    }

    private void addMenuBarToParent(ViewGroup parent) {
        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(mContext);
        horizontalScrollView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mMenuHeight));

        mMenuBar = new LinearLayout(mContext);
        mMenuBar.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        // 水平
        mMenuBar.setOrientation(LinearLayout.HORIZONTAL);
        mMenuBar.setGravity(Gravity.CENTER_VERTICAL);
//        mMenuBar.setBackgroundColor(getResources().getColor(R.color.color_00ff99));
        for (int i = 0; i < MENU_COUNT; i++) {
            TextView menu = new TextView(mContext);
            menu.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            menu.setText("城市");
            menu.setTextSize(TypedValue.COMPLEX_UNIT_PX, mMenuTextSize);
            menu.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_search, 0, 0, 0);
            menu.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "menu", Toast.LENGTH_SHORT).show();
                }
            });
            mMenuBar.addView(menu);
        }
        horizontalScrollView.addView(mMenuBar);
        parent.addView(horizontalScrollView);
    }

    private void initRootView() {
        // 垂直
        setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(params);
    }

    private void iniData(AttributeSet attrs) {
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.DropDownMenu);
        mMenuHeight = (int) array.getDimension(R.styleable.DropDownMenu_menuHeight, dp2px(mMenuHeight));
        mMenuTextSize = array.getDimensionPixelOffset(R.styleable.DropDownMenu_menuTextSize, sp2px(mMenuTextSize));
        array.recycle();
    }

    protected final int sp2px(float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, mContext.getResources().getDisplayMetrics());
    }

    protected final int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, mContext.getResources().getDisplayMetrics());
    }
}
