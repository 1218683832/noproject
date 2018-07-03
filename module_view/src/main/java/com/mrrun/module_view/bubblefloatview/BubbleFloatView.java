package com.mrrun.module_view.bubblefloatview;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.mrrun.module_view.Debug;

import java.util.ArrayList;
import java.util.List;

/**
 * 气泡浮动View及动画
 *
 * @author lipin
 * @version 1.0
 * @date 2018/07/02
 */
public class BubbleFloatView extends View {

    private Context mContext;
    private List<CircleBean> mCircleBeans = new ArrayList<>();
    private AnimatorSet animatorSet;

    public BubbleFloatView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public BubbleFloatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public BubbleFloatView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        animatorSet = new AnimatorSet();
        initData();
        initShader();
    }

    private void initShader() {
    }

    private void initData() {
        int width = getDisplayWidth(mContext);
        int height = getDisplayHight(mContext);
        int centerX = width / 2;
        int centerY = height / 2;
        Debug.D(String.format("屏幕宽度%d,屏幕高度%d", width, height));
        Debug.D(String.format("屏幕中心点(%d,%d)", centerX, centerY));

        Paint paint1 = createCommonPaint();
        paint1.setColor(Color.parseColor("#303F9F"));
        paint1.setAlpha(60);
        CircleBean circleBean1 = CircleBean.builder()
                .buildAmplitude(20)
                .buildP0(new PointF(width / 3, height / 3), (float) (width / 11))
                .buildPaint(paint1)
                .create();
        mCircleBeans.add(circleBean1);

        Paint paint2 = createCommonPaint();
        paint2.setColor(Color.parseColor("#fa7832"));
        paint2.setAlpha(60);
        CircleBean circleBean2 = CircleBean.builder()
                .buildAmplitude(20)
                .buildP0(new PointF(width / 2, height / 2), (float) (width / 9))
                .buildPaint(paint2)
                .create();
        mCircleBeans.add(circleBean2);

        Paint paint3 = createCommonPaint();
        paint3.setColor(Color.parseColor("#ccFF4081"));
        paint3.setAlpha(60);
        CircleBean circleBean3 = CircleBean.builder()
                .buildAmplitude(20)
                .buildP0(new PointF(width / 4, height / 5), (float) (width / 13))
                .buildPaint(paint3)
                .create();
        mCircleBeans.add(circleBean3);
    }

    /**
     * 创建共有属性的画笔
     *
     * @return
     */
    private Paint createCommonPaint() {
        Paint paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        return paint;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    private int getDisplayWidth(Context context) {
        if (context != null) {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            int w_screen = dm.widthPixels;
            return w_screen;
        }
        return 720;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    private int getDisplayHight(Context context) {
        if (context != null) {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            int h_screen = dm.heightPixels;
            return h_screen;
        }
        return 1280;
    }

    //onDraw直接在画板Canvas对象上绘制
    //(1)invalidate方法会执行onDraw过程，只能在UI线程调用
    //(2)postInvalidate 可以在非UI线程调用，省去了Handler消息调用
    //(3)RequestLayout 会执行onMeasure，onLayout ，onDraw
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (CircleBean circleBean : mCircleBeans) {
            canvas.drawCircle(circleBean.getP(-1).x, circleBean.getP(-1).y, circleBean.getRadius(), circleBean.getPaint());
        }
    }

    public void startAnimation() {
        if (!animatorSet.isRunning()) {
            animatorSet.play(floatAnimation());
            animatorSet.start();
        }
    }

    /**
     * 浮动动画
     *
     * @return
     */
    private ValueAnimator floatAnimation() {
        ValueAnimator floatAnimator = ValueAnimator.ofFloat(0, 1);
        floatAnimator.setDuration(3000);
        floatAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                Debug.D(String.format("value %f", value));
                for (CircleBean circleBean : mCircleBeans) {
                    Debug.D(String.format("circleBean.p0.x %f", circleBean.getP0().x));
                    circleBean.getP(value);
                    Debug.D(String.format("circleBean %f,circleBean %f", circleBean.getP(value).x, circleBean.getP(value).y));
                }
                invalidate();
            }
        });
        return floatAnimator;
    }
}

/**
 * 小球的属性
 */
class CircleBean {
    /**
     * 真实轨迹坐标点
     */
    private PointF p;

    /**
     * 小球到达中心点圆点坐标
     */
    private PointF p0;

    /**
     * 小球半径
     */
    private float radius;

    private Paint paint;

    /**
     * 振幅
     */
    private int amplitude = 80;

    final float[] pos = new float[2];
    final float[] tan = new float[2];
    private PathMeasure pathMeasure;

    public static CircleBean builder() {
        return new CircleBean();
    }

    public CircleBean create() {
        pathMeasure();
        return this;
    }

    public CircleBean buildP0(PointF p0, float radius) {
        this.p0 = p0;
        this.radius = radius;
        return this;
    }

    private void pathMeasure() {
        Path path = new Path();
        path.addCircle(this.p0.x - amplitude, this.p0.x, amplitude, Path.Direction.CCW);
        pathMeasure.setPath(path, true);
    }

    public CircleBean buildPaint(Paint paint) {
        this.paint = paint;
        return this;
    }

    public CircleBean buildAmplitude(int amplitude) {
        this.amplitude = amplitude;
        return this;
    }

    private CircleBean() {
        this.amplitude = 80;
        this.pathMeasure = new PathMeasure();
        this.p0 = new PointF(0, 0);
    }

    public PointF getP(float value) {
        if (value < 0) {
        } else {
            pathMeasure.getPosTan(pathMeasure.getLength() * value, pos, tan);
            this.setP(new PointF(pos[0], pos[1]));
        }
        return p;
    }

    private void setP(PointF p) {
        this.p = p;
    }

    public PointF getP0() {
        return p0;
    }

    public float getRadius() {
        return radius;
    }

    public Paint getPaint() {
        return paint;
    }
}