package com.mrrun.module_view.bethel.messagebubbleview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.drawable.AnimationDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.mrrun.module_view.Debug;
import com.mrrun.module_view.R;

/**
 * 拖拽控件View的触摸监听 (处理拖拽爆炸效果)
 * @author lipin
 * @date 2018/09/19
 * @version 1.0
 */
public class OnMessageBubbleTouchListener implements View.OnTouchListener, MessageBubbleView.OnMessageBubbleViewDragListener {

    private Context mContext;
    private MessageBubbleView mBubbleView; // 我们实现贝塞尔的View
    private final MessageBubbleManager buWindowManager;
    private FrameLayout mBombLayout;// 爆炸效果的View
    private ImageView mBombView;
    private final View mOriginalView; // 原来的,真实的那个View控件
    private final OnViewDragDisappearListener mDisappearListener;

    /**
     * 拖拽的View消失时的监听方法
     *
     * @param pointF
     */
    @Override
    public void onViewDragDisappear(PointF pointF) {
        // 移除消息气泡贝塞尔View
        buWindowManager.removeBubbleView(mBubbleView);
        // 添加一个爆炸的View动画(帧动画)
        buWindowManager.addBombView(mBombLayout);
        mBombView.setBackgroundResource(R.drawable.anim_bubble_bomb);
        AnimationDrawable bombDrawable = (AnimationDrawable) mBombView.getBackground();
        // 矫正爆炸时,位置错误问题
        mBombView.setX(pointF.x - bombDrawable.getIntrinsicWidth() / 2);
        mBombView.setY(pointF.y - bombDrawable.getIntrinsicHeight() / 2);
        bombDrawable.start();
        mBombView.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 动画执行完毕,把爆炸布局及时从WindowManager移除
                buWindowManager.removeBombView(mBombLayout);
                if (mDisappearListener != null) {
                    mDisappearListener.onDisappear(mOriginalView);
                }
            }
        }, getAnimationTotalTime(bombDrawable));
    }

    private long getAnimationTotalTime(AnimationDrawable animationDrawable) {
        long time = 0;
        int framesNum = animationDrawable.getNumberOfFrames();
        for (int i = 0; i < framesNum; i++) {
            time += animationDrawable.getDuration(i);
        }
        return time;
    }

    /**
     * 松手后,拖拽View消失,原来的View重新显示的监听方法
     */
    @Override
    public void onViewDragRestore() {
        mOriginalView.setVisibility(View.VISIBLE);
        // 移除消息气泡贝塞尔View
        buWindowManager.removeBubbleView(mBubbleView);
    }

    /**
     * 真正的处理View的消失的监听
     */
    public interface OnViewDragDisappearListener {
        /**
         * 原始View消失的监听
         *
         * @param originalView 原始的View
         */
        void onDisappear(View originalView);
    }

    public OnMessageBubbleTouchListener(View view, Context context, OnViewDragDisappearListener disappearListener) {
        this.mContext = context;
        this.mOriginalView = view;
        this.mDisappearListener = disappearListener;
        this.mBubbleView = new MessageBubbleView(context);
        this.buWindowManager = MessageBubbleManager.getInstance(context);
        this.mBubbleView.setOnMessageBubbleViewDragListener(this);
        this.mBombLayout = new FrameLayout(mContext);
        this.mBombView = new ImageView(mContext);
        this.mBombView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.mBombLayout.addView(mBombView);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Debug.D("OnBubbleTouchListener--->onTouchEvent--->event=" + event.getAction());
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (null != mOriginalView && mOriginalView.isShown()){
                    // 初始化贝塞尔View上边的点
                    int[] location = new int[2];
                    mOriginalView.getLocationOnScreen(location);// 默认获取的是View左上角在屏幕上的坐标(y坐标包含状态栏的高度)
                    mBubbleView.initPoint(location[0] + mOriginalView.getWidth() / 2, location[1] + mOriginalView.getHeight() / 2 - BubbleUtils.getStatusBarHeight(mContext));
                    // 给贝塞尔View一张原始View的快照
                    Bitmap dragBitmap = getBitmapByView(mOriginalView);
                    // 给拖拽的消息设置一张原始View的快照
                    mBubbleView.setDragBitmap(dragBitmap);
                    // 添加WindowManger中
                    buWindowManager.addBubbleView(mBubbleView);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                // 解决一点击View出现闪动的bug(一旦拖动在隐藏)
                if (mOriginalView.getVisibility() == View.VISIBLE) {
                    mOriginalView.setVisibility(View.INVISIBLE);// 放到下面,一拖动就隐藏
                }
                mBubbleView.updateDragPoint(event.getRawX(), event.getRawY() - BubbleUtils.getStatusBarHeight(mContext));
                break;
            case MotionEvent.ACTION_UP:
                mBubbleView.handleActionUP();
                break;
        }
        return true;
    }

    /**
     * 从一个View中获取 Bitmap
     * @param view
     * @return
     */
    private Bitmap getBitmapByView(View view) {
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }
}
