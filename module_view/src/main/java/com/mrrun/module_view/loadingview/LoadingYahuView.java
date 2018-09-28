package com.mrrun.module_view.loadingview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.LinearInterpolator;

import com.mrrun.module_view.BaseView;
import com.mrrun.module_view.CalculateUtil;
import com.mrrun.module_view.Debug;
import com.mrrun.module_view.R;

public class LoadingYahuView extends BaseView {

    // 圆的颜色集合
    private int[] mCircleColorIds;
    // 圆的画笔集合
    private Paint[] mCirclePaints;
    // 第一个点圆的位置
    private PointF mFirstCirclePointf;
    // View中心点
    private PointF mViewCenterPointf;
    // 每个圆距离View中心的距离
    private int mViewCenterRadius = 80;
    // 每个圆固定的相隔角度
    private int mCircleDegrees = 30;
    // 球的半径
    private int mCircleRadius = 7;
    private int mViewWidth, mViewHeight;
    private int mCircleCount = 0;
    private int mCircleDegreesAdd = 0;
    //为屏幕对角线的一半
    private float mDiagonalDist;
    // 空心圆的初始半径
    private float mHoleRadius;
    private static final int R_DURATION = 3000;
    private static final int T_DURATION = 3000;
    private static final int E_DURATION = 1500;

    private LoadingState mLoadingState;
    private Paint mPaint;
    //整体颜色背景
    private int mSplashColor = Color.WHITE;

    public LoadingYahuView(Context context) {
        this(context, null);
    }

    public LoadingYahuView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingYahuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        mCircleColorIds = new int[]
                {
                        R.color.color_00ff99, R.color.color_2e2eb8, R.color.color_77b300,
                        R.color.color_646464, R.color.color_bf4080, R.color.color_99ebff
                };
        mCircleCount = mCircleColorIds.length;
        mCirclePaints = new Paint[mCircleCount];
        mViewCenterRadius = dp2px(mViewCenterRadius);
        mCircleRadius = dp2px(mCircleRadius);
        mCircleDegrees = 360 / mCircleCount;
    }

    /**
     * 根据角度更新第一个点的位置
     * @param degrees
     */
    private void updateFirstPointFByRateDegrees(int degrees) {
        // 在自定义view中，计算某个点在圆上的位置.
        mFirstCirclePointf.x = (float) (mViewCenterPointf.x +  mViewCenterRadius * Math.cos((degrees) * 3.14 / 180));
        mFirstCirclePointf.y = (float) (mViewCenterPointf.y +  mViewCenterRadius * Math.sin((degrees) * 3.14 / 180));
    }

    /**
     * 根据半径更新第一个点的位置
     * @param radius
     */
    private void updateFirstPointFByRadius(float radius) {
        // 在自定义view中，计算某个点在圆上的位置.
        // mCircleDegreesAdd已转过的角度
        mFirstCirclePointf.x = (float) (mViewCenterPointf.x +  radius * Math.cos((mCircleDegreesAdd) * 3.14 / 180));
        mFirstCirclePointf.y = (float) (mViewCenterPointf.y +  radius * Math.sin((mCircleDegreesAdd) * 3.14 / 180));
    }

    private void initPointF() {
        mViewCenterPointf = new PointF(mViewWidth / 2, mViewHeight / 2);
        float cx = mViewWidth / 2;// View宽度的一半
        float cy = mViewHeight / 2 - mViewCenterRadius;// View高度的一半减每个点距离View中心的距离
        mFirstCirclePointf = new PointF(cx, cy);
    }

    @Override
    protected void initPaint() {
        mPaint = createCommonPaint();
        mPaint.setColor(mSplashColor);
        mPaint.setStyle(Paint.Style.STROKE);
        for (int i = 0; i < mCircleCount; i++) {
            mCirclePaints[i] = createCommonPaint();
            mCirclePaints[i].setColor(getResources().getColor(mCircleColorIds[i]));
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        initPointF();
        mDiagonalDist= (float) Math.sqrt(mViewCenterPointf.x * mViewCenterPointf.x + mViewCenterPointf.y * mViewCenterPointf.y);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (null == mLoadingState){
            // 开启旋转动画
            mLoadingState = new RotateState();
        }
        mLoadingState.draw(canvas);
    }

    // 策略模式实现三种动画状态
    private abstract class LoadingState{
        public abstract void draw(Canvas canvas);
    }

    /**
     * 旋转动画
     */
    private class RotateState extends LoadingState {

        private Animator animator;

        public RotateState() {
            if (null == animator) {
                animator = rotateAnimation();
            }
            animator.start();
        }

        private Animator rotateAnimation() {
            ValueAnimator rotateAnimator = ValueAnimator.ofInt(0, 360);
            rotateAnimator.setInterpolator(new LinearInterpolator());
            rotateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mCircleDegreesAdd = (int) animation.getAnimatedValue();
                    // 有2中方法保证每个圆的位置正确：
                    // 1、计算每个角度后的第一个圆的位置，然后onDraw旋转坐标系；
                    // 2、onDraw旋转坐标系，要计算好。
                    // updateFirstPointF(mCircleDegreesAdd);
                    invalidate();
                    Debug.D("rotateAnimation--->onAnimationUpdate--->mCircleDegreesAdd:" + mCircleDegreesAdd);
                }
            });
            rotateAnimator.setDuration(R_DURATION);
            rotateAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    // 开启聚合动画
                    mLoadingState = new MergeState();
                }
            });
            return rotateAnimator;
        }

        @Override
        public void draw(Canvas canvas) {
            // 绘制白色背景
            canvas.drawColor(mSplashColor);
            // 只需要知道第一个点的位置，其他点位置通过计算坐标系旋转的角度即可确定
            for (int i = 0; i < mCircleCount; i++) {
                canvas.save();
                canvas.rotate(mCircleDegrees * i + mCircleDegreesAdd, mViewCenterPointf.x, mViewCenterPointf.y);
                // View中心点旋转某角度
                canvas.drawCircle(mFirstCirclePointf.x, mFirstCirclePointf.y, mCircleRadius, mCirclePaints[i]);
                canvas.restore();
            }
        }
    }

    /**
     * 聚合动画
     */
    private class MergeState extends LoadingState {

        private Animator animator;

        public MergeState() {
            if (null == animator) {
                animator = mergeAnimation();
            }
            animator.start();
        }

        private Animator mergeAnimation() {
            ValueAnimator mergeAnimation = ValueAnimator.ofFloat(mViewCenterRadius, mViewCenterRadius * 0.2f);
            mergeAnimation.setInterpolator(new AnticipateInterpolator(6.0f));
            mergeAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float radius = (float) animation.getAnimatedValue();
                    // updateFirstPointFByRadius(radius);
                    mFirstCirclePointf = CalculateUtil.calculateCirclePoint(mViewCenterPointf, radius, mCircleDegreesAdd - mCircleDegrees);
                    invalidate();
                }
            });
            mergeAnimation.setDuration(T_DURATION);
            mergeAnimation.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    // 开启扩散动画
                    mLoadingState = new ExpandState();
                }
            });
            return mergeAnimation;
        }

        @Override
        public void draw(Canvas canvas) {
            // 绘制白色背景
            canvas.drawColor(mSplashColor);
            // 只需要知道第一个点的位置，其他点位置通过计算坐标系旋转的角度即可确定
            for (int i = 0; i < mCircleCount; i++) {
                canvas.save();
                canvas.rotate(mCircleDegrees * i + mCircleDegreesAdd, mViewCenterPointf.x, mViewCenterPointf.y);
                // View中心点旋转某角度
                canvas.drawCircle(mFirstCirclePointf.x, mFirstCirclePointf.y, mCircleRadius, mCirclePaints[i]);
                canvas.restore();
            }
        }
    }

    /**
     * 水波纹扩散动画
     * 画一个空心圆，让它的画笔的粗细变成很大---不断地减少画笔的粗细
     */
    private class ExpandState extends LoadingState {

        private Animator animator;

        public ExpandState() {
            if (null == animator) {
                animator = expandAnimation();
            }
            animator.start();
        }

        private Animator expandAnimation() {
            // 1200ms，计算某个时刻当前的大圆半径是多少？r~对角线一半的某个值
            ValueAnimator expandAnimation = ValueAnimator.ofFloat(mCircleRadius, mDiagonalDist);
            expandAnimation.setDuration(E_DURATION);
            expandAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mHoleRadius = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            expandAnimation.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    setVisibility(GONE);
                }
            });
            expandAnimation.start();
            return expandAnimation;
        }

        @Override
        public void draw(Canvas canvas) {
            // 这里不画背景色了，那么背景就是透明的，会把下层的视图显示出来，
            // 让画笔大小填充到整个屏幕的对角线，然后再慢慢减小画笔的粗细就实现了水波纹的效果

            // 画笔的宽度
            float strokeWidth = mDiagonalDist - mHoleRadius;
            mPaint.setStrokeWidth(strokeWidth);
            // float radius = strokeWidth / 2;//向内
            // 整个圆的半径
            float radius = strokeWidth / 2 + mHoleRadius;
            canvas.drawCircle(mViewCenterPointf.x, mViewCenterPointf.y, radius, mPaint);
        }
    }
}
