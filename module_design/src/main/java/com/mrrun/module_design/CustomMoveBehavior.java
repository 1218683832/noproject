package com.mrrun.module_design;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * 自定义一个Behavior让View跟随另一个View一起移动
 *
 * @author lipin
 * @date 2018/08/31
 * @version 1.0
 */
public class CustomMoveBehavior extends CoordinatorLayout.Behavior<View> {// 这里的泛型是child的类型，也就是观察者View

    public CustomMoveBehavior() {
    }

    // 必须重写带双参的构造器，因为从xml反射需要调用
    public CustomMoveBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof View;// 告知监听的dependency 任意View
    }

    // 当dependency View变化的时候，可以对child(View)进行操作
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        child.setX(dependency.getX());
        child.setY(dependency.getY() + 100);
        if (child instanceof TextView){
            TextView textView = (TextView) child;
            textView.setText(dependency.getX()+","+dependency.getY());
        }
        return true;
    }
}
