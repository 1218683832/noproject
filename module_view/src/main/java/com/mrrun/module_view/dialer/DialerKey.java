package com.mrrun.module_view.dialer;

import android.graphics.drawable.Drawable;

/**
 * 拨号键盘按键
 */
public class DialerKey {
    /**
     * key的类型
     */
    protected int mKeyType;
    /**
     * key的图标
     */
    protected Drawable mKeyIcon;
    /**
     * key的code
     */
    protected int mKeyCode;

    @Override
    public String toString() {
        return "DialerKey{" +
                "mKeyType=" + mKeyType +
                ", mKeyIcon=" + mKeyIcon +
                ", mKeyCode=" + mKeyCode +
                '}';
    }
}
