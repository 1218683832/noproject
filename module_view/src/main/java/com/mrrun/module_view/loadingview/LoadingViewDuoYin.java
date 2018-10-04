package com.mrrun.module_view.loadingview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

import com.mrrun.module_view.BaseView;

/**
 * 仿抖音加载View。
 * 思路：
 * 1、在View中点左右2边画2个小圆；
 * 2、实现动画，左到右再到左的小圆放大->还原->缩小->还原，右到左再到右的小圆缩小->还原->放大->还原；
 * 3、给Paint画笔设置PorterDuff模式，利用android.graphics.PorterDuff.Mode.MULTIPLY模式将2个小圆重叠的部分改变颜色原值显示；
 *
 * @author lipin
 * @date 2018/10/03
 * @version 1.0
 */
public class LoadingViewDuoYin extends BaseView {

    // 左边圆画笔
    private Paint mLPaint;
    // 右边圆画笔
    private Paint mRPaint;
    // 左边圆颜色
    private int mLColor;
    // 右边圆颜色
    private int mRColor;
    // 左右圆的初始半径
    private int INIT_RADIUS = 5;
    private int MAX_RADIUS = 7;
    private int MIN_RADIUS = 3;
    // View的宽高
    private int mViewWidth, mViewHeight;
    private PointF mViewCenterP = new PointF();
    private PointF mLeftP = new PointF();
    private PointF mRightP = new PointF();
    private float mLCicrleRadius;
    private float mRCicrleRadius;
    // 每个小圆距离中点的距离
    private float mDistance;

    public LoadingViewDuoYin(Context context) {
        this(context, null);
    }

    public LoadingViewDuoYin(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingViewDuoYin(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override
    protected void init(AttributeSet attrs) {
        initData(attrs);
        initPaint();
    }

    @Override
    protected void initData(AttributeSet attrs) {
        mLColor = Color.GREEN;
        mRColor = Color.RED;
        INIT_RADIUS = dp2px(INIT_RADIUS);
        MAX_RADIUS = dp2px(MAX_RADIUS);
        MIN_RADIUS = dp2px(MIN_RADIUS);
        mLCicrleRadius = INIT_RADIUS;
        mRCicrleRadius = INIT_RADIUS;
        mDistance = INIT_RADIUS;
    }

    @Override
    protected void initPaint() {
        mLPaint = createCommonPaint();
        mRPaint = createCommonPaint();

        mLPaint.setColor(mLColor);
        mLPaint.setStyle(Paint.Style.FILL);
        mRPaint.setColor(mRColor);
        mRPaint.setStyle(Paint.Style.FILL);
        mRPaint.setXfermode(new PorterDuffXfermode(
                PorterDuff.Mode.MULTIPLY));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;

        mViewCenterP.x = mViewWidth / 2;
        mViewCenterP.y = mViewHeight / 2;

        mLeftP.x = mViewCenterP.x - INIT_RADIUS;
        mLeftP.y = mViewCenterP.y;

        mRightP.x = mViewCenterP.x + INIT_RADIUS;
        mRightP.y = mViewCenterP.y;
    }

    public void startLoading(){
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(zoomInAnimation(), zoomOutAnimation());
        animatorSet.setDuration(1200);
        animatorSet.start();
    }

    private Animator zoomInAnimation(){
        ValueAnimator animator = ValueAnimator.ofFloat(mLCicrleRadius, MAX_RADIUS, mLCicrleRadius, MIN_RADIUS, mRCicrleRadius);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mLCicrleRadius = (float) animation.getAnimatedValue();
                if (animation.getAnimatedFraction() <= 0.5f){
                    // 左到右
                    mLeftP.x = mViewCenterP.x - mDistance + 4 * mDistance * animation.getAnimatedFraction();
                } else {
                    // 右到左
                    mLeftP.x = mViewCenterP.x + 3 * mDistance - 4 * mDistance * animation.getAnimatedFraction();
                }
                invalidate();
            }
        });
        animator.setRepeatCount(-1);
        return animator;
    }

    private Animator zoomOutAnimation(){
        ValueAnimator animator = ValueAnimator.ofFloat(mRCicrleRadius, MIN_RADIUS, mRCicrleRadius, MAX_RADIUS, mRCicrleRadius);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRCicrleRadius = (float) animation.getAnimatedValue();
                if (animation.getAnimatedFraction() <= 0.5f){
                    // 右到左
                    mRightP.x = mViewCenterP.x + mDistance - 4 * mDistance * animation.getAnimatedFraction();
                } else {
                    // 左到右
                    mRightP.x = mViewCenterP.x - 3 * mDistance + 4 * mDistance * animation.getAnimatedFraction();
                }
                invalidate();
            }
        });
        animator.setRepeatCount(-1);
        return animator;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas);
        canvas.drawCircle(mLeftP.x, mLeftP.y, mLCicrleRadius, mLPaint);
        canvas.drawCircle(mRightP.x, mRightP.y, mRCicrleRadius, mRPaint);
    }
}