package com.mrrun.module_view.dialer;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个软键盘的定义，包括按键的排列布局，宽度高度。
 *
 * @author lipin
 * @version 1.0
 * @date 2018/06/28
 */
public class DialerKeyBoard {
    /**
     * 软键盘的xml文件ID
     */
    private int mDkbTemplateId;
    /**
     * 软键盘按键的宽度
     */
    private float mKeyWidth;
    /**
     * 软键盘按键的高度
     */
    private float mKeyHeight;
    /**
     * 软键盘的模版
     */
    private DkbTemplate mDkbTemplate;
    /**
     * 按键排列的行的链表，每个元素都是一行。
     */
    private List<DialerKeyRow> mKeyRows;

    public DialerKeyBoard(int mDkbTemplateId, DkbTemplate mDkbTemplate) {
        this.mDkbTemplateId = mDkbTemplateId;
        this.mDkbTemplate = mDkbTemplate;
    }

    public void setKeyWidthAndHeight(float keyWidth, float keyHeight) {
        this.mKeyWidth = keyWidth;
        this.mKeyHeight = keyHeight;
    }

    /**
     * 开始新的一行
     *
     * @param rowId
     */
    public void beginNewRow(int rowId) {
        if (null == mKeyRows) {
            mKeyRows = new ArrayList<DialerKeyRow>();
        }
        DialerKeyRow keyRow = new DialerKeyRow();
        keyRow.mRowId = rowId;
        keyRow.mDialerKeys = new ArrayList<DialerKey>();
        mKeyRows.add(keyRow);
    }

    /**
     * @param dialerKey
     */
    public boolean addDialerKey(DialerKey dialerKey) {
        if (null == mKeyRows || mKeyRows.size() <= 0) {
            return false;
        }
        DialerKeyRow keyRow = mKeyRows.get(mKeyRows.size() - 1);
        return keyRow.addDialerKey(dialerKey);
    }

    @Override
    public String toString() {
        return "DialerKeyBoard{" +
                "mDkbTemplateId=" + mDkbTemplateId +
                ", mKeyWidth=" + mKeyWidth +
                ", mKeyHeight=" + mKeyHeight +
                ", mDkbTemplate=" + mDkbTemplate +
                ", mKeyRows=" + mKeyRows +
                '}';
    }
}

/**
 * 拨号盘的行
 */
class DialerKeyRow {
    static final int ALWAYS_SHOW_ROW_ID = -1;
    static final int DEFAULT_ROW_ID = 0;
    /**
     * If the row id is {@link #ALWAYS_SHOW_ROW_ID}, this row will always be
     * enabled.
     */
    int mRowId;
    List<DialerKey> mDialerKeys;

    public boolean addDialerKey(DialerKey dialerKey) {
        if (null == mDialerKeys) {
            mDialerKeys = new ArrayList<DialerKey>();
        }
        return mDialerKeys.add(dialerKey);
    }

    @Override
    public String toString() {
        return "DialerKeyRow{" +
                "mRowId=" + mRowId +
                ", mDialerKeys=" + mDialerKeys +
                '}';
    }
}