package com.mrrun.module_view.dialer;

import android.graphics.drawable.Drawable;

import java.util.Vector;

/**
 * 拨号器键盘XML模板类
 */
public class DkbTemplate {
    private int mDkbTemplateId; // 模版的xml文件资源ID，也是模版在软键盘池中的ID
    private Drawable mDkbBg;
    private float mKeyXMargin = 0;
    private float mKeyYMargin = 0;
    /**
     * Key type list.
     */
    Vector<SoftKeyType> mKeyTypeList = new Vector<SoftKeyType>();

    public DkbTemplate(int mDkbTemplateId) {
        this.mDkbTemplateId = mDkbTemplateId;
    }

    public void setBackgrounds(Drawable dkbBg) {
        this.mDkbBg = dkbBg;
    }

    public void setMargins(float keyXMargin, float keyYMargin) {
        this.mKeyXMargin = keyXMargin;
        this.mKeyYMargin = keyYMargin;
    }

    public static SoftKeyType createKeyType(int d) {
        return new SoftKeyType(d);
    }

    public boolean addKeyType(SoftKeyType keyType) {
        if (!mKeyTypeList.isEmpty()) {
            for (SoftKeyType o : mKeyTypeList) {
                // 保证List中同类keyTypeIdmKeyTypeList
                if (o.mKeyTypeId == keyType.mKeyTypeId) {
                    return false;
                }
            }
        }
        mKeyTypeList.add(keyType);
        return true;
    }

    public int getDkbTemplateId() {
        return mDkbTemplateId;
    }

    @Override
    public String toString() {
        return "DkbTemplate{" +
                "mDkbTemplateId=" + mDkbTemplateId +
                ", mDkbBg=" + mDkbBg +
                ", mKeyXMargin=" + mKeyXMargin +
                ", mKeyYMargin=" + mKeyYMargin +
                ", mKeyTypeList=" + mKeyTypeList +
                '}';
    }
}

class SoftKeyType {
    public int mKeyTypeId;
    public Drawable mKeyBg;

    SoftKeyType(int Id) {
        this.mKeyTypeId = Id;
    }

    public void setBackgrounds(Drawable bg) {
        this.mKeyBg = bg;
    }

    @Override
    public String toString() {
        return "SoftKeyType{" +
                "mKeyTypeId=" + mKeyTypeId +
                ", mKeyBg=" + mKeyBg +
                '}';
    }
}
