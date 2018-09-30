package com.mrrun.module_view.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.mrrun.module_view.Debug;
import com.mrrun.module_view.MeasureUtil;

/**
 * 玩转文字变色View
 * 需求1：文字居中;
 * 需求2：文字从左到右变色显示;
 * 大家可以在此基础上拓展，比如
 * 1、加入自定义属性；
 * 2、根据上下左右方向变色；
 * 3、放入ViewPager中滑动变色；
 * 4、跟手势变色等等
 *
 * @author lipin
 * @version 1.0
 * @date 2018/07/11
 */
public class ColorTrackView extends View {

    private final Context mContext;
    /**
     * 文字内容
     */
    private String mTextContent = "";
    /**
     * 文字长度
     */
    private float mTextWidth;
    /**
     * 文字大小
     */
    private int mTextSize = 30;
    private int mTextOriginColor = Color.BLACK;
    private int mTextChangeColor = Color.RED;
    private Rect mTextRect = new Rect();
    private float mTextStartX;
    /**
     * 文字改变进度
     */
    private float mProgress;
    /**
     * 动画时长
     */
    private static int ANIMATION_DURATION = 3000;

    public ColorTrackView(Context context) {
        this(context, null);
    }

    public ColorTrackView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorTrackView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        initData(attrs);
        initPaint();
        measureText();
    }

    /**
     * 测文字
     */
    private void measureText() {
        mTextWidth = MeasureUtil.measureText(mTextPaint, mTextContent);
        mTextRect = MeasureUtil.measureTextRect(mTextPaint, mTextContent);
    }

    private TextPaint mTextPaint;

    private void initPaint() {
        mTextPaint = new TextPaint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextOriginColor);
    }

    private void initData(AttributeSet attrs) {
        mTextSize = sp2px(mTextSize);
        // 字体大小
        // 原文字颜色
        // 改变后文字颜色
        // 文字内容
        mTextContent = "玩转字体变色";
    }

    private int sp2px(int spValue) {
        float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public float getProgress() {
        return mProgress;
    }

    public void setProgress(float progress) {
        this.mProgress = progress;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        mTextStartX = width / 2 - mTextWidth / 2;
        Debug.D(String.format("width,height=(%d,%d)", width, height));
        setMeasuredDimension(width, height);
        post(new Runnable() {
            @Override
            public void run() {
                colorChangeAnimation();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float r = mTextStartX + mTextWidth * mProgress;
        drawOriginLift(canvas, r);
        drawChangeLift(canvas, r);
    }

    /**
     * 绘制左到右的原色
     *
     * @param canvas
     *         the canvas
     * @param r
     *         the r
     */
    protected void drawOriginLift(Canvas canvas, float r) {
        drawText(canvas, mTextOriginColor, r, mTextStartX + mTextWidth);
    }

    /**
     * 绘制左到右的原色
     *
     * @param canvas
     *         the canvas
     * @param r
     *         the r
     */
    protected void drawChangeLift(Canvas canvas, float r) {
        drawText(canvas, mTextChangeColor, mTextStartX, r);
    }

    private void drawText(Canvas canvas, int color, float l, float r) {
        canvas.save();
        mTextPaint.setColor(color);
        canvas.clipRect(new RectF(l, 0, r, getHeight()));
        canvas.drawText(mTextContent, mTextStartX, getHeight() / 2 + MeasureUtil.textBaseLine(mTextPaint), mTextPaint);
        canvas.restore();
    }

    public void colorChangeAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "progress", 0, 1);
        animator.setDuration(ANIMATION_DURATION);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }

    /**
     * Measure width int.
     *
     * @param widthMeasureSpec
     *         the width measure spec
     * @return the int
     */
    protected int measureWidth(int widthMeasureSpec) {
        int val = MeasureSpec.getSize(widthMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = val;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result = (int) mTextWidth;
        }
        result = mode == MeasureSpec.AT_MOST ? Math.min(result, val) : result;
        result = result + getPaddingLeft() + getPaddingRight();
        return result;
    }

    /**
     * Measure height int.
     *
     * @param heightMeasureSpec
     *         the height measure spec
     * @return the int
     */
    protected int measureHeight(int heightMeasureSpec) {
        int val = MeasureSpec.getSize(heightMeasureSpec);
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = val;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result = mTextRect.height();
        }
        result = mode == MeasureSpec.AT_MOST ? Math.min(result, val) : result;
        result = result + getPaddingTop() + getPaddingBottom();
        return result;
    }
}