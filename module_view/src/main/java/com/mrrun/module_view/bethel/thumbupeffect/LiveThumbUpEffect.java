package com.mrrun.module_view.bethel.thumbupeffect;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.mrrun.module_view.BaseView;

/**
 * 仿花束直播点赞效果
 *
 * @author lipin
 * @date 2018/09/19
 * @version 1.0
 */
public class LiveThumbUpEffect extends BaseView{

    public LiveThumbUpEffect(Context context) {
        this(context, null);
    }

    public LiveThumbUpEffect(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LiveThumbUpEffect(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override
    protected void init(AttributeSet attrs) {
        initData(attrs);
        initPaint();
    }

    @Override
    protected void initData(AttributeSet attrs) {

    }

    @Override
    protected void initPaint() {

    }
}
