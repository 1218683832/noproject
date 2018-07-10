package com.mrrun.module_view.loadingview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import com.mrrun.module_view.Debug;

/**
 * 仿花束直播加载动画View2。网上的写法
 *
 * @author lipin
 * @version 1.0
 * @date 2018/07/10
 */
public class FlowersLiveLoadingLayout extends RelativeLayout {

    private Context mContext;
    /**
     * 左右中小圆View
     */
    private CicrleView mLeftCicrleView, mRightCicrleView, mCenterCicrleView;
    /**
     * 小圆移动距离
     */
    private int MAX_TRANSLATION_DISTANCE = 30;
    /**
     * 动画持续时间
     */
    private final long ANIMATION_DRUATION = 350;
    /**
     * 动画开始时延迟时间
     */
    private final int START_DELAY = 220;

    public FlowersLiveLoadingLayout(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public FlowersLiveLoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    private void init() {
        initData();
        initView();
        startAnimation();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Debug.D("onDetachedFromWindow");
    }

    private void startAnimation() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                expendAnimation();
            }
        }, START_DELAY);
    }

    private void initView() {
        mLeftCicrleView = createCicrleView();
        mLeftCicrleView.changeColor(Color.BLUE);
        mRightCicrleView = createCicrleView();
        mRightCicrleView.changeColor(Color.GREEN);
        mCenterCicrleView = createCicrleView();
        mCenterCicrleView.changeColor(Color.RED);

        addView(mLeftCicrleView);
        addView(mRightCicrleView);
        addView(mCenterCicrleView);
    }

    public void expendAnimation() {
        // 向左边位移动画
        ObjectAnimator leftTranslationAnimator = ObjectAnimator.ofFloat(mLeftCicrleView, "translationX", 0, -MAX_TRANSLATION_DISTANCE);
        // 向右边位移动画
        ObjectAnimator rightTranslatioAnimation = ObjectAnimator.ofFloat(mRightCicrleView, "translationX", 0, MAX_TRANSLATION_DISTANCE);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIMATION_DRUATION);
        set.playTogether(leftTranslationAnimator, rightTranslatioAnimation);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // 往里面跑
                innerAnimation();
            }
        });
        set.start();
    }

    private void innerAnimation() {
        //开启左边位移动画
        ObjectAnimator leftTranslationAnimator = ObjectAnimator.ofFloat(mLeftCicrleView, "translationX", -MAX_TRANSLATION_DISTANCE, 0);
        //开启右边位移动画
        ObjectAnimator rightTranslatioAnimation = ObjectAnimator.ofFloat(mRightCicrleView, "translationX", MAX_TRANSLATION_DISTANCE, 0);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIMATION_DRUATION);
        set.playTogether(leftTranslationAnimator, rightTranslatioAnimation);
        set.setInterpolator(new AccelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // 往外面跑
                // 切换颜色顺序  左边的给中间 中间的给右边  右边的给左边
                int mLeftViewColor = mLeftCicrleView.getColor();
                int mRightViewColor = mRightCicrleView.getColor();
                int mMiddleViewColor = mCenterCicrleView.getColor();
                //切换三者颜色
                mLeftCicrleView.changeColor(mRightViewColor);
                mRightCicrleView.changeColor(mMiddleViewColor);
                mCenterCicrleView.changeColor(mLeftViewColor);
                expendAnimation();
            }
        });
        set.start();
    }

    private CicrleView createCicrleView() {
        CicrleView cicrleView = new CicrleView(mContext);
        LayoutParams params = new LayoutParams(dp2x(10), dp2x(10));
        params.addRule(CENTER_IN_PARENT);
        cicrleView.setLayoutParams(params);
        return cicrleView;
    }

    private void initData() {
        MAX_TRANSLATION_DISTANCE = dp2x(MAX_TRANSLATION_DISTANCE);
    }

    private int dp2x(int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }
}

class CicrleView extends View {

    private Paint mPaint;

    private int mColor;

    public int getColor() {
        return mColor;
    }

    public CicrleView(Context context) {
        super(context);
        init();
    }

    public CicrleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    /**
     * Change color.
     *
     * @param color
     *         the color
     */
    public void changeColor(int color) {
        mColor = color;
        mPaint.setColor(mColor);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float cx = getWidth() / 2;
        float cy = getHeight() / 2;
        canvas.drawCircle(cx, cy, cx, mPaint);
    }
}
