package com.mrrun.module_view.loadingview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

/**
 * 仿UC网页加载View动画.
 * 思路：
 * 1、继承自LinearLayout，向其中添加四个圆view
 * 2、确定每个园的旋转角度和旋转中心点；
 * 3、写个动画；
 * 4、对外提供执行动画接口；
 *
 * @author lipin
 * @version 1.0
 * @date 2018/10/08
 */
public class LoadingViewUC extends LinearLayout {

    protected Context mContext;

    /**
     * View宽高
     */
    protected int mViewWidth, mViewHeight;

    // View中心点
    private PointF mViewCenterP = new PointF();

    // 2个旋转点
    private PointF mPointF1 = new PointF(), mPointF2 = new PointF();

    // 点与点之间的距离（不算点半径）
    private float mCircleDistance = 5;

    // 每个点的半径
    private float mCircleRadius = 3;

    private Paint mPaint;

    CircleImgView cicrleView1, cicrleView2, cicrleView3, cicrleView4;

    public LoadingViewUC(Context context) {
        this(context, null);
    }

    public LoadingViewUC(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingViewUC(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }

    protected void init(AttributeSet attrs) {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        initData(attrs);
        initPaint();

        cicrleView1 = CircleImgView.create(mContext, mCircleRadius, mPaint);
        cicrleView2 =CircleImgView.create(mContext, mCircleRadius, mPaint);
        cicrleView3 = CircleImgView.create(mContext, mCircleRadius, mPaint);
        cicrleView4 = CircleImgView.create(mContext, mCircleRadius, mPaint);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (2 * mCircleRadius), (int)(2 * mCircleRadius));
        params.leftMargin = (int) mCircleDistance;
        cicrleView1.setLayoutParams(params);
        cicrleView2.setLayoutParams(params);
        cicrleView3.setLayoutParams(params);
        LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams((int) (2 * mCircleRadius), (int)(2 * mCircleRadius));
        params4.leftMargin = (int) (-2 * mCircleRadius);
        cicrleView4.setLayoutParams(params4);
        cicrleView4.setVisibility(INVISIBLE);

        addView(cicrleView1);
        addView(cicrleView2);
        addView(cicrleView4);
        addView(cicrleView3);
    }

    protected void initData(AttributeSet attrs) {
        mCircleRadius = dp2px(mCircleRadius);
        mCircleDistance = dp2px(mCircleDistance);
    }

    protected void initPaint() {
        mPaint = createCommonPaint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#ff8533"));
    }

    protected final Paint createCommonPaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        return paint;
    }

    protected final int dp2px(float dpValue) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, mContext.getResources().getDisplayMetrics()) + 0.5f);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mViewWidth = w;
        this.mViewHeight = h;
        mViewCenterP.x = mViewWidth / 2;
        mViewCenterP.y = mViewHeight / 2;
        mPointF1.x = mCircleDistance / 2 + mCircleRadius;
    }

    Animator animator1;

    AnimatorSet animatorSet = new AnimatorSet();

    public void loading() {
        animator1 = status1(cicrleView1);
        animatorSet.playTogether(animator1, status2(cicrleView2), status3(cicrleView3));
        postDelayed(new Runnable() {
            @Override
            public void run() {
                animatorSet.start();
            }
        }, 650);

        // status1(cicrleView1);
        // status2(cicrleView2);
        // status3(cicrleView3);
        // status4(cicrleView4);
    }

    /**
     * 左到右从上面划过
     */
    private Animator status1(final CircleImgView view) {
        // 在以下的旋转操作，默认都是View的中心坐标为旋转中心的，可通过setPivotX()和setPivotY()来修改该旋转中心的坐标。
        view.setPivotX(mPointF1.x * 1.5f);
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0, 180);
        animator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                cicrleView1.setVisibility(INVISIBLE);
                cicrleView4.setVisibility(VISIBLE);
                status4(cicrleView4).start();
            }
        });
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(500);
        return animator;
    }

    /**
     * 右到左从下面划过
     */
    private Animator status2(CircleImgView view) {
        // 在以下的旋转操作，默认都是View的中心坐标为旋转中心的，可通过setPivotX()和setPivotY()来修改该旋转中心的坐标。
        view.setPivotX(-mPointF1.x / 2);
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0, 180);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                cicrleView1.setVisibility(VISIBLE);
                animator1.start();
            }

            @Override
            public void onAnimationStart(Animator animation) {
            }
        });
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(1000);
        animator.setRepeatCount(-1);
        return animator;
    }

    /**
     * 右到左从上面划过
     * @param view
     */
    private Animator status3(CircleImgView view) {
        // 在以下的旋转操作，默认都是View的中心坐标为旋转中心的，可通过setPivotX()和setPivotY()来修改该旋转中心的坐标。
        view.setPivotX(-mPointF1.x / 2);
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0, -180);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                cicrleView4.setVisibility(INVISIBLE);
            }

            @Override
            public void onAnimationStart(Animator animation) {
            }
        });
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(1000);
        animator.setRepeatCount(-1);
        return animator;
    }

    /**
     * 右到左从下面划过
     */
    private Animator status4(CircleImgView view) {
        // 在以下的旋转操作，默认都是View的中心坐标为旋转中心的，可通过setPivotX()和setPivotY()来修改该旋转中心的坐标。
        view.setPivotX(mPointF1.x * 1.5f);
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0, -180);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(500);
        return animator;
    }

    static class CircleImgView extends AppCompatTextView {

        private float radius;

        private Paint paint;

        public static CircleImgView create(Context context, float radius, Paint paint) {
            return new CircleImgView(context, radius, paint);
        }

        private CircleImgView(Context context, float radius, Paint paint) {
            super(context);
            this.radius = radius;
            this.paint = paint;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // super.onDraw(canvas);
            canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, radius, paint);
        }
    }
}