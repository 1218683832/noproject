package com.mrrun.module_view.progressbarview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.mrrun.module_view.Debug;
import com.mrrun.module_view.MeasureUtil;

/**
 * 仿QQ运动步数进度效果
 *
 * @author lipin
 * @version 1.0
 * @date 2018/07/12
 */
public class QQStepProgressView extends View {

    private Context mContext;
    private int mViewWidth = 150;
    private int mViewHeight = 150;
    /**
     * 外圈圆环的颜色
     */
    private int ovalBgColor = Color.parseColor("#003ae6");
    /**
     * 外圈圆环的画笔大小
     */
    private float mOvalStrokeWidth = 20;
    Annulus annulus;

    public QQStepProgressView(Context context) {
        this(context, null);
    }

    public QQStepProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQStepProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        initData(attrs);
        initPaint();
    }

    private void initPaint() {
    }

    private void initData(AttributeSet attrs) {
        // ovalBgColor
        mViewWidth = dp2px(mViewWidth);
        mViewHeight = dp2px(mViewHeight);
        mOvalStrokeWidth = dp2px(mOvalStrokeWidth);
        annulus = new Annulus(ovalBgColor, mOvalStrokeWidth, 135, 270);
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
        int r = (int) (Math.min(mViewWidth, mViewHeight) - mOvalStrokeWidth / 2);
        RectF oval = new RectF(mOvalStrokeWidth / 2, mOvalStrokeWidth / 2, r, r);
        annulus.setOval(oval);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas);
        Debug.D("" + annulus.toString());
        annulus.drawAnnulus(canvas);
    }

    private int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, mContext.getResources().getDisplayMetrics());
    }
}

/**
 * 圆环以及两个端点的园
 *
 * @author lipin
 * @version 1.0
 * @date 2018/07/12
 */
class Annulus {
    /**
     * 圆环颜色
     */
    private int bgColor = Color.parseColor("#ffaa80");
    /**
     * 外圈圆环的画笔大小
     */
    private float strokeWidth = 10;
    /**
     * 圆环位置
     */
    private RectF oval;
    /**
     * 圆环的起角度
     */
    float startAngle;
    /**
     * 圆环的终角度
     */
    float sweepAngle;
    /**
     * 圆环半径
     */
    private float r;
    /**
     * 总角度大小
     */
    private int angleLength;
    /**
     * 小圆圆心
     */
    private int x1, x2, y1, y2;

    Paint mPaint;

    public Annulus(int bgColor, float strokeWidth, int startAngle, int sweepAngle) {
        this.bgColor = bgColor;
        this.strokeWidth = strokeWidth;
        this.startAngle = startAngle;
        this.sweepAngle = sweepAngle;
        this.angleLength = Math.abs(sweepAngle);
        buildPaint();
    }

    public void setOval(RectF oval) {
        this.oval = oval;
        this.r = oval.right - oval.left;
        buildx1y1x2y2();
    }

    private void buildx1y1x2y2() {
        this.x1 = (int) (oval.centerX() + r / 2 * Math.cos(startAngle * 3.14 / 180));
        this.y1 = (int) (oval.centerX() + r / 2 * Math.sin(startAngle * 3.14 / 180));
        this.x2 = (int) (oval.centerX() + r / 2 * Math.cos((startAngle + sweepAngle) * 3.14 / 180));
        this.y2 = (int) (oval.centerX() + r / 2 * Math.sin((startAngle + sweepAngle) * 3.14 / 180));
    }

    private void buildPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(bgColor);
        mPaint.setStrokeWidth(strokeWidth);
    }

    public void drawAnnulus(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(strokeWidth);
        canvas.drawArc(oval, startAngle, sweepAngle, false, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(0);
        canvas.drawCircle(x1, y1, strokeWidth / 2, mPaint);
        canvas.drawCircle(x2, y2, strokeWidth / 2, mPaint);
    }

    @Override
    public String toString() {
        return "Annulus{" +
                "bgColor=" + bgColor +
                ", strokeWidth=" + strokeWidth +
                ", oval=" + oval +
                ", startAngle=" + startAngle +
                ", sweepAngle=" + sweepAngle +
                ", r=" + r +
                ", x1=" + x1 +
                ", x2=" + x2 +
                ", y1=" + y1 +
                ", y2=" + y2 +
                ", mPaint=" + mPaint +
                '}';
    }
}