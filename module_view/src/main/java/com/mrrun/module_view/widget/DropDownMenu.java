package com.mrrun.module_view.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * 常用多条件筛选菜单
 *
 * @author lipin
 * @version 1.0
 * @date 2018/07/19
 */
public class DropDownMenu extends LinearLayout {

    private Context mContext;

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

    }

    private void initRootView() {
        // 垂直
        setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(params);
    }

    private void iniData(AttributeSet attrs) {
    }
}
