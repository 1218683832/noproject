package com.mrrun.module_design;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

/**
 * 仿UC折叠Behavior
 *
 * @author lipiin
 * @date 2018/09/04
 * @version 1.0
 */
public class CustomUcFoldBehavior extends CoordinatorLayout.Behavior<View>{

    private int mStartY;

    public CustomUcFoldBehavior() {
    }

    // 必须重写带双参的构造器，因为从xml反射需要调用
    public CustomUcFoldBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        // 记录开始的Y坐标  也就是toolbar起始Y坐标
        if(mStartY == 0) {
            mStartY = (int) dependency.getY();
            Debug.D(String.format("记录开始的Y坐标,也就是toolbar起始Y坐标:%d", mStartY));
        }
        // 计算toolbar从开始移动到最后的百分比
        float percent = dependency.getY() / mStartY;
        Debug.D(String.format("计算toolbar从开始移动到最后的百分比:%f", percent));
        // percent [1, 0] child消失到可见
        // percent [0, 1] child可见到消失
        // 改变child的坐标(消失/可见)
        child.setY(child.getHeight() * (1 - percent) - child.getHeight());
        return true;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof Toolbar;
    }
}
