package com.mrrun.module_view.progressbarview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.mrrun.module_view.AnimatorUtil;
import com.mrrun.module_view.Debug;
import com.mrrun.module_view.DrawUtil;
import com.mrrun.module_view.MeasureUtil;

/**
 * 直线型带文本进度View.
 * 可以拓展进度文本样式；
 * 进度文本上下位置等。
 *
 * @author 1ipin
 * @version 1.0
 * @date 2018/07/13
 */
public class LineNumberProgressView extends View {

    private Context mContext;
    private int mViewWidth = 250;
    private int mViewHeight = 100;
    /**
     * 画笔大小
     */
    private int mPaintWidth = 10;
    /**
     * 未覆盖的进度条颜色
     */
    private int mFistColor = Color.parseColor("#ffc34d");
    /**
     * 未覆盖的进度条画笔
     */
    private Paint mFirstPaint;
    /**
     * 已覆盖的进度条颜色
     */
    private int mSecondColor = Color.parseColor("#ff5c33");
    /**
     * 已覆盖的进度条画笔
     */
    private Paint mSecondPaint;
    /**
     * 进度文本画笔
     */
    private Paint mTextPaint;
    /**
     * 进度总百分比
     */
    private float mTotalRate = 1.0f;
    /**
     * 当前进度百分比
     */
    private float mCurRate = 0.0f;
    private RectF mTextRecf;
    /**
     * 进度条文本框宽度
     */
    private int recfWidth = 65;
    /**
     * 进度条文本框高度
     */
    private int recfHeight = 40;
    private int textSize = 16;

    public LineNumberProgressView(Context context) {
        this(context, null);
    }

    public LineNumberProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineNumberProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        initData(attrs);
        iniPaint();
    }

    private void initData(AttributeSet attrs) {
        mViewWidth = dp2px(mViewWidth);
        mViewHeight = dp2px(mViewHeight);
        mPaintWidth = dp2px(mPaintWidth);
        recfWidth = dp2px(recfWidth);
        recfHeight = dp2px(recfHeight);
        textSize = sp2px(textSize);
        // mFistColor、mSecondColor
        mTextRecf = new RectF();
    }

    private void iniPaint() {
        mFirstPaint = new Paint();
        mFirstPaint.setAntiAlias(true);
        mFirstPaint.setDither(true);
        mFirstPaint.setStyle(Paint.Style.FILL);
        mFirstPaint.setStrokeWidth(mPaintWidth);
        mFirstPaint.setColor(mFistColor);
        // 设置线头的形状
        mFirstPaint.setStrokeCap(Paint.Cap.ROUND);
        // 设置拐角的形状
        mFirstPaint.setStrokeJoin(Paint.Join.MITER);

        mSecondPaint = new Paint();
        mSecondPaint.setAntiAlias(true);
        mSecondPaint.setDither(true);
        mSecondPaint.setStyle(Paint.Style.FILL);
        mSecondPaint.setStrokeWidth(mPaintWidth);
        mSecondPaint.setColor(mSecondColor);
        // 设置线头的形状
        mSecondPaint.setStrokeCap(Paint.Cap.ROUND);
        // 设置拐角的形状
        mSecondPaint.setStrokeJoin(Paint.Join.MITER);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(mSecondColor);
        mTextPaint.setTextSize(textSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewWidth = MeasureUtil.commonMeasureWidth(mViewWidth, widthMeasureSpec);
        mViewHeight = MeasureUtil.commonMeasureHeight(mViewHeight, heightMeasureSpec);
        setMeasuredDimension(mViewWidth, mViewHeight);
        // 让进度文本在进度条上方
        mTextRecf.top = mViewHeight / 2 - mPaintWidth / 2 - recfHeight;
        mTextRecf.bottom = mTextRecf.top + recfHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas);
        drawFistLine(canvas);
        drawSecondLine(canvas);
        drawProgressText(canvas);
    }

    private void drawProgressText(Canvas canvas) {
        String text = String.valueOf(String.format("%.0f%s", mCurRate * 100 , "%"));
        mTextRecf.left = (mViewWidth - getPaddingLeft() - getPaddingRight()) * mCurRate + getPaddingLeft() - mPaintWidth / 2;
        mTextRecf.right = mTextRecf.left + recfWidth;
        if (mTextRecf.right >= mViewWidth - getPaddingRight() + mPaintWidth / 2) {
            mTextRecf.right = mViewWidth - getPaddingRight() + mPaintWidth / 2;
            mTextRecf.left = mTextRecf.right - recfWidth;
        }
        mTextPaint.setColor(mSecondColor);
        canvas.drawRoundRect(mTextRecf, 0, 0, mTextPaint);
        mTextPaint.setColor(Color.WHITE);
        canvas.drawText(text, mTextRecf.centerX() - DrawUtil.measureText(mTextPaint, text) / 2,
                mTextRecf.centerY() + DrawUtil.textBaseLine2(mTextPaint), mTextPaint);
    }

    /**
     * 画未覆盖的进度条线
     *
     * @param canvas
     */
    private void drawFistLine(Canvas canvas) {
        canvas.drawLine(getPaddingLeft(), mViewHeight / 2, mViewWidth - getPaddingRight(), mViewHeight / 2, mFirstPaint);
    }

    /**
     * 画已覆盖的进度条线
     *
     * @param canvas
     */
    private void drawSecondLine(Canvas canvas) {
        Debug.D(String.format("mCurRate %f", mCurRate));
        canvas.drawLine(getPaddingLeft(), mViewHeight / 2, (mViewWidth - getPaddingLeft() - getPaddingRight()) * mCurRate + getPaddingLeft(), mViewHeight / 2, mSecondPaint);
    }

    public void progressAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "curRate", 0, mTotalRate);
        animator.setDuration(2000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setStartDelay(AnimatorUtil.DELAY_START_TIME);
        animator.start();
    }

    public void setCurRate(float curRate) {
        this.mCurRate = curRate;
        invalidate();
    }

    private int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, mContext.getResources().getDisplayMetrics());
    }

    private int sp2px(float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, mContext.getResources().getDisplayMetrics());
    }

    public void setTotal(float totalRate) {
        this.mTotalRate = totalRate;
    }
}
