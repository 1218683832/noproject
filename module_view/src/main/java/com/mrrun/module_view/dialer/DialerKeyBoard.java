package com.mrrun.module_view.dialer;

import java.util.List;

/**
 * 一个软键盘的定义，包括按键的排列布局，宽度高度。
 */
public class DialerKeyBoard {
    /**
     * 软键盘的xml文件ID
     */
    private int mDkbTemplateId;
    /**
     * 软键盘按键的宽度
     */
    private int mDkbKeyWidth;
    /**
     * 软键盘按键的高度
     */
    private int mDkbKeyHeight;
    /**
     * 软键盘的模版
     */
    private DkbTemplate mDkbTemplate;
    /**
     * 按键排列的行的链表，每个元素都是一行。
     */
    private List<DialerKeyRow> mKeyRows;
}

class DialerKeyRow {
    List<DialerKey> mDialerKeys;
}