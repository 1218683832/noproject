package com.mrrun.module_view.loadingview;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.mrrun.module_view.IBaseView;

/**
 * 仿58同城LoadingView
 *
 * @author lipin
 * @version 1.0
 * @date 2018/07/18
 */
public class LoadingView58 extends LinearLayout implements IBaseView{

    private Context mContext;

    public LoadingView58(Context context) {
        this(context,null);
    }

    public LoadingView58(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadingView58(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }

    @Override
    public void init(AttributeSet attrs) {
        initData(attrs);
        initPaint();
    }

    @Override
    public void initData(AttributeSet attrs) {

    }

    @Override
    public void initPaint() {

    }

    @Override
    public Paint createPaint() {
        return null;
    }
}
