package com.mrrun.module_view.progressbarview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.mrrun.module_view.MeasureUtil;

/**
 * 仿QQ运动步数进度效果
 *
 * @author lipin
 * @version 1.1
 * @date 2018/07/12
 */
public class QQStepView extends View {

    private Context mContext;
    private Paint mOutPaint;
    private int mViewWidth = 150;
    private int mViewHeight = 150;
    /**
     * 外圈圆环的颜色
     */
    private int mOutColor = Color.parseColor("#003ae6");
    /**
     * 内圈圆环的颜色
     */
    private int mInnerEndColor = Color.parseColor("#b32300");
    /**
     * 内圈圆环的颜色
     */
    private int mInnerStartColor = Color.parseColor("#ff8466");
    /**
     * 外内圈圆环的画笔大小
     */
    private float mOutStrokeWidth = 20;
    private RectF outRecf;
    private Paint mInnerPaint;
    private float mProgress;

    /**
     * 当前步数
     */
    private int mCurSteps = 6894;
    /**
     * 最大步数
     */
    private int MAX_STEPS = 20000;

    public QQStepView(Context context) {
        this(context, null);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        initData(attrs);
        initPaint();
    }

    private void initData(AttributeSet attrs) {
        MAX_STEPS = MAX_STEPS;
        mViewWidth = dp2px(mViewWidth);
        mViewHeight = dp2px(mViewHeight);
        mOutStrokeWidth = dp2px(mOutStrokeWidth);
    }

    private void initPaint() {
        mOutPaint = new Paint();
        mOutPaint.setStyle(Paint.Style.STROKE);
        mOutPaint.setColor(mOutColor);
        mOutPaint.setAntiAlias(true);
        mOutPaint.setDither(true);
        mOutPaint.setStrokeWidth(mOutStrokeWidth);
        mOutPaint.setStrokeCap(Paint.Cap.ROUND);

        mInnerPaint = new Paint();
        mInnerPaint.setStyle(Paint.Style.STROKE);
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setDither(true);
        mInnerPaint.setStrokeWidth(mOutStrokeWidth);
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int width = resolveSizeAndState(mViewWidth, widthMeasureSpec ,0);
//        int height = resolveSizeAndState(mViewHeight, heightMeasureSpec, 0);
        int width = MeasureUtil.commonMeasureWidth(mViewWidth, widthMeasureSpec, this);
        int height = MeasureUtil.commonMeasureHeight(mViewHeight, heightMeasureSpec, this);
        mViewWidth = width;
        mViewHeight = height;
        setMeasuredDimension(width, height);
        int r = (int) (Math.min(mViewWidth, mViewHeight) - mOutStrokeWidth / 2);
        outRecf = new RectF(mOutStrokeWidth / 2, mOutStrokeWidth / 2, r, r);
        mInnerPaint.setShader(new LinearGradient(outRecf.left, outRecf.top, outRecf.right, outRecf.bottom,
                mInnerStartColor, mInnerEndColor, Shader.TileMode.CLAMP));
        stepsAnimation();
    }

    public void stepsAnimation(){
        ValueAnimator animator = ValueAnimator.ofFloat(0, mCurSteps);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgress =  (float)animation.getAnimatedValue() / MAX_STEPS;
                invalidate();
            }
        });
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawArc(outRecf, 135, 270, false, mOutPaint);
        canvas.drawArc(outRecf, 135, 270 * mProgress, false, mInnerPaint);
    }

    private int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, mContext.getResources().getDisplayMetrics());
    }
}
