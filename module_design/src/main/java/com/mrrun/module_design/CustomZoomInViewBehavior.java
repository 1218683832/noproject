package com.mrrun.module_design;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 放大Viewehavior
 *
 * @author lipiin
 * @date 2018/09/04
 * @version 1.0
 */
public class CustomZoomInViewBehavior extends CoordinatorLayout.Behavior<View>{

    private NestedScrollView mDependency;
    private boolean isFristScroll = true;
    private boolean canOverScroll = true;
    float preMoveY = 0;
    float nowMoveY = 0;

    public CustomZoomInViewBehavior() {
    }

    public CustomZoomInViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        mDependency = (NestedScrollView) dependency;
        mDependency.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int height = mDependency.getHeight();
                Debug.D(String.format("onScrollChange--->(%d, %d, %d, %d)", scrollX, scrollY, oldScrollX, oldScrollY));
                if (height == scrollY){// 滚动到底了
                    canOverScroll = false;
                } else if (0 == scrollY){// 滚动到顶了
                    canOverScroll = false;
                } else {
                    canOverScroll = true;
                }
                Debug.D("onScrollChange--->canOverScroll=" + canOverScroll);
            }
        });
        return dependency instanceof NestedScrollView;// 告知监听的dependency
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, final View child, View dependency) {
        if (null != mDependency){
            mDependency.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Debug.D("event " + event.getAction());
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:{
                            preMoveY = event.getY();
                            break;
                        }
                        case MotionEvent.ACTION_MOVE:{
                            Debug.D("ACTION_MOVE--->canOverScroll=" + canOverScroll);
                            nowMoveY = event.getY();
                            float d = nowMoveY - preMoveY;
                            if (d > 0){// 向下滑动
                                if (isFristScroll){
                                    nowMoveY = event.getY();
                                    Debug.D("preMoveY " + preMoveY);
                                    Debug.D("nowMoveY " + nowMoveY);
                                    float scale = 1 +   Math.abs((nowMoveY - preMoveY) / 150);
                                    Debug.D("scale " + scale);
                                    if (scale >= 1.5) {
                                        scale = 1.5f;
                                    }
                                    child.setScaleX(scale);
                                    child.setScaleY(scale);
                                } else {
                                    if (canOverScroll){
                                        preMoveY = event.getY();
                                        isFristScroll = false;
                                    } else {
                                        nowMoveY = event.getY();
                                        Debug.D("preMoveY " + preMoveY);
                                        Debug.D("nowMoveY " + nowMoveY);
                                        float scale = 1 +   Math.abs((nowMoveY - preMoveY) / 150);
                                        Debug.D("scale " + scale);
                                        if (scale >= 1.5) {
                                            scale = 1.5f;
                                        }
                                        child.setScaleX(scale);
                                        child.setScaleY(scale);
                                    }
                                }
                            } else {// 向上滑动
                                if (isFristScroll){
                                    nowMoveY = event.getY();
                                    isFristScroll = false;
                                } else {
                                    if (canOverScroll){
                                        preMoveY = event.getY();
                                    } else {
                                        nowMoveY = event.getY();
                                        Debug.D("preMoveY " + preMoveY);
                                        Debug.D("nowMoveY " + nowMoveY);
                                        float scale = 1 +   Math.abs((nowMoveY - preMoveY) / 150);
                                        Debug.D("scale " + scale);
                                        if (scale >= 1.5) {
                                            scale = 1.5f;
                                        }
                                        child.setScaleX(scale);
                                        child.setScaleY(scale);
                                    }
                                }
                            }
                            break;
                        }
                        case MotionEvent.ACTION_UP:{
                            child.setScaleX(1.0f);
                            child.setScaleY(1.0f);
                            break;
                        }
                    }
                    return false;
                }
            });
        }
        return true;
    }
}
