package com.mrrun.module_view.affirmbutton;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.mrrun.module_view.Debug;

/**
 * 自定义确认按钮带动画效果
 *
 * @author lipin
 * @version 1.0
 * @date 2018/07/02
 */
public class AffirmButtonView extends View {

    private Context mContext;
    /**
     * view的宽度
     */
    private int mWidth;
    /**
     * view的高度
     */
    private int mHeight;
    /**
     * 背景颜色
     */
    private int bg_color = 0xffbc7d53;
    /**
     * 动画执行时间
     */
    private int duration = 1000;
    /**
     * 圆角半径
     */
    private float circleAngle;
    /**
     * 圆角矩形画笔
     */
    private Paint paint;
    /**
     * 文字画笔
     */
    private Paint textPaint;
    /**
     * 对勾（√）画笔
     */
    private Paint okPaint;

    private RectFBean rectFBean;

    private TextBean textBean;

    /**
     * 是否开始绘制对勾
     */
    private boolean startDrawOk = false;
    /**
     * 路径--用来获取对勾的路径
     */
    private Path path = new Path();
    /**
     * 取路径的长度
     */
    private PathMeasure pathMeasure;
    /**
     * 对路径处理实现绘制动画效果
     */
    private PathEffect effect;

    public AffirmButtonView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public AffirmButtonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public AffirmButtonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mWidth = getMeasuredWidth();
        this.mHeight = getMeasuredHeight();
        Debug.D("onMeasure");
        Debug.D("mWidth = " + mWidth);
        Debug.D("mHeight = " + mHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Debug.D("onLayout");
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    private void init() {
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(bg_color);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(40);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);

        okPaint = new Paint();
        okPaint.setStrokeWidth(10);
        okPaint.setStyle(Paint.Style.STROKE);
        okPaint.setAntiAlias(true);
        okPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Debug.D("onDraw");
        super.onDraw(canvas);
        drawRoundRect(canvas);
        drawText(canvas);
        if (startDrawOk) {
            drawOK(canvas);
        }
    }

    /**
     * 绘制OK
     *
     * @param canvas
     */
    private void drawOK(Canvas canvas) {
        canvas.drawPath(path, okPaint);
    }

    /**
     * 绘制文字
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        if (textBean == null) {
            textBean = new TextBean("确认完成", 0, mWidth, 0, mHeight, textPaint);
        } else {
            textBean.setRight(mWidth);
            textBean.setPaint(textPaint);
        }
        canvas.drawText(textBean.getContentStr(), textBean.getStartX(), textBean.getBaseline(), textBean.getPaint());
    }

    /**
     * 绘制圆角矩形
     *
     * @param canvas
     */
    private void drawRoundRect(Canvas canvas) {
        if (rectFBean == null) {
            rectFBean = new RectFBean(0, mWidth, 0, mHeight, circleAngle, paint);
        } else {
            rectFBean.setRight(mWidth);
            rectFBean.setCircleAngle(circleAngle);
        }
        canvas.drawRoundRect(rectFBean.getRectF(), circleAngle, circleAngle, rectFBean.getPaint());
    }

    public void startAnimation() {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animatorRecf()).before(animatorOK());
        animatorSet.start();
    }

    private ValueAnimator animatorRecf() {
        // mWidth >= mHeight
        ValueAnimator valueAnimator = ValueAnimator.ofInt(mWidth, mHeight);
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                float factor = animation.getAnimatedFraction();
                circleAngle = factor * mHeight;
                textPaint.setAlpha((int) (255 - 255 * factor));
                Debug.D(String.format("animation factor is %f", factor));
                Debug.D(String.format("animation value is %d", value));
                Debug.D(String.format("圆角半径 circleAngle is %f", circleAngle));
                getLayoutParams().width = value;
                requestLayout();
            }
        });
        return valueAnimator;
    }

    private ValueAnimator animatorOK() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 0);
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                if (!startDrawOk) {
                    //对勾的路径
                    path.moveTo(mHeight / 8 * 3, mHeight / 2);
                    path.lineTo(mHeight / 2, mHeight / 5 * 3);
                    path.lineTo(mHeight / 3 * 2, mHeight / 5 * 2);
                    pathMeasure = new PathMeasure(path, true);
                }
                startDrawOk = true;
                // 路径效果
                DiscretePathEffect effect = new DiscretePathEffect(1.5f, 10 * value);
                // PathEffect effect = new DashPathEffect(new float[]{pathMeasure.getLength(), pathMeasure.getLength()}, value * pathMeasure.getLength());
                okPaint.setPathEffect(effect);
                invalidate();
            }
        });
        return valueAnimator;
    }
}
