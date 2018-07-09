package com.mrrun.module_view.loadingview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.mrrun.module_view.Debug;

/**
 * 仿花束直播加载动画View1
 *
 * @author lipin
 * @version 1.0
 * @date 2018/07/09
 */
public class FlowersLiveLoadingView extends View {

    /**
     * View的宽高
     */
    private int mViewWidth, mViewHeight;
    /**
     * 分别是左边右边中间小圆的画笔
     */
    private Paint mLeftPaint, mRightPaint, mCenterPaint, mTempPaint;
    /**
     * 左边小圆的中心点坐标
     */
    private int mLeftX, mLeftY;
    /**
     * 右边小圆的中心点坐标
     */
    private int mRightX, mRightY;
    /**
     * 中间小圆的中心点坐标
     */
    private int mCenterX, mCenterY;
    /**
     * 小圆移动距离
     */
    private int MAX_TRANSLATION_DISTANCE = 30;
    /**
     * 动画持续时间
     */
    private final long ANIMATION_DRUATION = 700;
    /**
     * 小圆半径
     */
    private int RADIUS = 6;

    private AnimatorSet animatorSet;

    public FlowersLiveLoadingView(Context context) {
        super(context);
        init();
    }

    public FlowersLiveLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        initData();
        initPaint();
        startAnimation();
    }

    private void initPaint() {
        mLeftPaint = createPaint(Color.BLUE);
        mRightPaint = createPaint(Color.GREEN);
        mCenterPaint = createPaint(Color.RED);
    }

    private void initData() {
        MAX_TRANSLATION_DISTANCE = dp2x(MAX_TRANSLATION_DISTANCE);
        RADIUS = dp2x(RADIUS);
        animatorSet = new AnimatorSet();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mViewWidth = MeasureSpec.getSize(widthMeasureSpec);
        this.mViewHeight = MeasureSpec.getSize(heightMeasureSpec);
        resetCoordinate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 画左边小圆
        canvas.drawCircle(mCenterX - d, mCenterY, RADIUS, mLeftPaint);
        // 画右边小圆
        canvas.drawCircle(mCenterX + d, mCenterY, RADIUS, mRightPaint);
        // 画中间小圆
        canvas.drawCircle(mCenterX, mCenterY, RADIUS, mCenterPaint);

    }

    private void startAnimation() {
        if (!animatorSet.isRunning()) {
            animatorSet.play(outTranslationAnimation());
            animatorSet.setDuration(ANIMATION_DRUATION);
            post(new Runnable() {
                @Override
                public void run() {
                    animatorSet.start();
                }
            });
        }
    }

    private void resetCoordinate() {
        this.mCenterX = mViewWidth / 2;
        this.mCenterY = mViewHeight / 2;
        this.mLeftX = this.mRightX = mCenterX;
        this.mLeftY = this.mRightY = mCenterY;
    }

    private int dp2x(int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }

    private Paint createPaint(int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        paint.setDither(true);
        paint.setAntiAlias(true);
        return paint;
    }

    int d = 0;

    /**
     *
     */
    private ValueAnimator outTranslationAnimation() {
        ValueAnimator animator = ValueAnimator.ofInt(0, MAX_TRANSLATION_DISTANCE, 0);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(-1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                d = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                Debug.D("onAnimationRepeat");
                mTempPaint = mLeftPaint;
                mLeftPaint = mRightPaint;
                mRightPaint = mCenterPaint;
                mCenterPaint = mTempPaint;
                invalidate();
            }
        });
        return animator;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Debug.D("onDetachedFromWindow");
        stopAnimation();
    }

    private void stopAnimation() {
        if (animatorSet.isRunning() || animatorSet.isPaused()){
            animatorSet.cancel();
        }
    }
}
