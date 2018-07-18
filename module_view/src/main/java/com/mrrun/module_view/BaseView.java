package com.mrrun.module_view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义View继承的类
 *
 * @author lipin
 * @version 1.0
 * @date 2018/07/18
 */
public abstract class BaseView extends View implements IBaseView {

    protected Context mContext;

    public BaseView(Context context) {
        this(context, null);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }

    @Override
    public final void init(AttributeSet attrs) {
        initData(attrs);
        initPaint();
    }
}
