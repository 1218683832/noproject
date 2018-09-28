package com.mrrun.module_view.loadingview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
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
    private static final int R_DURATION = 3000;
    private static final int T_DURATION = 3000;

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
                        R.color.color_646464
                };
        mCircleCount = mCircleColorIds.length;
        mCirclePaints = new Paint[mCircleCount];
        mViewCenterRadius = dp2px(mViewCenterRadius);
        mCircleRadius = dp2px(mCircleRadius);
        mCircleDegrees = 360 / mCircleCount;

        post(new Runnable() {
            @Override
            public void run() {
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(rotateAnimation()).before(translateAnimation());
                animatorSet.start();
            }
        });
    }

    private Animator translateAnimation() {
        ValueAnimator translationAnimator = ValueAnimator.ofFloat(mViewCenterRadius, mViewCenterRadius * 0.5f);
        translationAnimator.setInterpolator(new AnticipateInterpolator(6.0f));
        translationAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float radius = (float) animation.getAnimatedValue();
//                updateFirstPointFByRadius(radius);
                mFirstCirclePointf = CalculateUtil.calculateCirclePoint(mViewCenterPointf, radius, mCircleDegreesAdd - mCircleDegrees);
                invalidate();
            }
        });
        translationAnimator.setDuration(T_DURATION);
        return translationAnimator;
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
        return rotateAnimator;
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
    }

    @Override
    protected void onDraw(Canvas canvas) {
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
