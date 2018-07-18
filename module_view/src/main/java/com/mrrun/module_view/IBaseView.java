package com.mrrun.module_view;

import android.util.AttributeSet;

/**
 * 自定义View实现的接口
 *
 * @author lipin
 * @version 1.0
 * @date 2018/07/18
 */
public interface IBaseView {
    public void init(AttributeSet attrs);
    public void initData(AttributeSet attrs);
    public void initPaint();
}
