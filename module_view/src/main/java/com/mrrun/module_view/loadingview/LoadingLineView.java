package com.mrrun.module_view.loadingview;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.mrrun.module_view.Debug;

/**
 * 从中心向两端扩散的动态加载进度动态View。
 * 需求1：水平方向动态加载；
 * 需求2：从中心向两端扩散；
 *
 * @author lipin
 * @version 1.0
 * @date 2018 /07/09
 */
public class LoadingLineView extends View {

    private Context mContext;
    private Paint mProgressPaint;
    private int mViewWidth, mViewHeight;
    /**
     * View中心点坐标
     */
    private int mViewCenterX,mViewCenterY;
    /**
     * 从中心点到左右两边最大可绘制的距离
     */
    private int mMaxDistanceX;
    /**
     * 从中心点到左右两边当前可绘制的距离
     */
    private int mCurDistanceX;
    private AnimatorSet animatorSet = new AnimatorSet();

    public LoadingLineView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public LoadingLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        initData();
        initPaint();
    }

    private void initPaint() {
        mProgressPaint = new Paint();
        mProgressPaint.setStyle(Paint.Style.FILL);
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setColor(Color.parseColor("#ff8000"));
    }

    private void initData() {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Debug.D("onSizeChanged");
        mViewWidth = w;
        mViewHeight = h;
        mViewCenterX = w / 2;
        mViewCenterY = h / 2;
        mMaxDistanceX = mViewWidth / 2;
        Debug.D("onSizeChanged mMaxDistanceX = " + mMaxDistanceX);
        startAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(mViewCenterX - mCurDistanceX, 0,mViewCenterX + mCurDistanceX, mViewHeight, mProgressPaint);
    }

    private ValueAnimator horizontalGrowthAnimation(){
        ValueAnimator animator = ValueAnimator.ofInt(0, mMaxDistanceX);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(1000);
        animator.setRepeatCount(-1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurDistanceX = (int) animation.getAnimatedValue();
                Debug.D(String.format("从中心点到左右两边当前可绘制的距离%d", mCurDistanceX));
                invalidate();
            }
        });
        return animator;
    }

    private ValueAnimator alphaAnimation(){
        ValueAnimator animator = ValueAnimator.ofInt(0,255,0);
        animator.setDuration(1000);
        animator.setRepeatCount(-1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int a = (int) animation.getAnimatedValue();
                mProgressPaint.setAlpha(a);
                Debug.D(String.format("当前alpha值%d", a));
            }
        });
        return animator;
    }

    private void startAnimation(){
        if (!animatorSet.isRunning()){
            animatorSet.play(horizontalGrowthAnimation()).with(alphaAnimation());
            animatorSet.setDuration(800);
            animatorSet.start();
        }
    }

    private void stopAnimation(){
        if (animatorSet.isRunning()){
            animatorSet.cancel();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimation();
    }


}