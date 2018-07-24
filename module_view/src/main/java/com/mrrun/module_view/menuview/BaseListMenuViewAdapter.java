package com.mrrun.module_view.menuview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 多条件筛选菜单的抽象Adapter
 *
 * @author lipin
 * @version 1.0
 * @date 2018/07/23
 */
public abstract class BaseListMenuViewAdapter {

    public static final int INVALID_POSITION = -1;

    protected Context mContext;

    protected LayoutInflater mInflater;

    public BaseListMenuViewAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    /**
     * 获得Menu个数
     *
     * @return
     */
    public abstract int getMenuCount();

    /**
     * 获得Menu视图
     *
     * @param position
     * @param parent
     * @return
     */
    public abstract View getMenuView(int position, ViewGroup parent);

    /**
     * 获得Menu内容视图
     *
     * @param position
     * @param parent
     * @return
     */
    public abstract View getMenuContentView(int position, ViewGroup parent);

    public abstract void openMenu(View menu);

    public abstract void closeMenu(View menu);
}
