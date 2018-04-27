package com.mrrun.module_view.dialer;

import android.graphics.drawable.Drawable;

import java.util.Vector;

public class DkbTemplate implements Tag {
    private int mDkbTemplateId; // 模版的xml文件资源ID，也是模版在软键盘池中的ID
    /**
     * 标签的属性
     */
    private Drawable mDkbBg;
    private float mKeyXMargin = 0;
    private float mKeyYMargin = 0;
    /**
     * Key type list.
     */
    Vector<SoftKeyType> mKeyTypeList = new Vector<SoftKeyType>();
}

class SoftKeyType {
    public int mKeyTypeId;
    public Drawable mKeyBg;
}
