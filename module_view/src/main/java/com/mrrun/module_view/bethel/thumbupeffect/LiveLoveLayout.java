package com.mrrun.module_view.bethel.thumbupeffect;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mrrun.module_view.Debug;
import com.mrrun.module_view.R;

import java.util.Random;

/**
 * 仿花束直播点赞效果Layout
 *
 * @author lipin
 * @version 1.0
 * @date 2018/09/19
 */
public class LiveLoveLayout extends RelativeLayout {

    private Context mContext;
    /**
     * 点赞的图片集
     */
    private Drawable[] mLoveDrawables;
    /**
     * 图片数量
     */
    private int mDrawableNum;
    /**
     * 图片宽高
     */
    private int mLoveDrawableHeight, mLoveDrawableWidth;
    /**
     * View宽高
     */
    private int mViewWidth, mViewHeight;
    private Random mRandom;

    public LiveLoveLayout(Context context) {
        this(context, null);
    }

    public LiveLoveLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LiveLoveLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
    }

    private void init(AttributeSet attrs) {
        initData();
        iniAttrs(attrs);
        initDrawable();
    }

    private void initData() {
        mRandom = new Random();
    }

    private void iniAttrs(AttributeSet attrs) {
    }

    private void initDrawable() {
        mLoveDrawables = new Drawable[4];
        mLoveDrawables[0] = getResources().getDrawable(R.drawable.love1);
        mLoveDrawables[1] = getResources().getDrawable(R.drawable.love2);
        mLoveDrawables[2] = getResources().getDrawable(R.drawable.love3);
        mLoveDrawables[3] = getResources().getDrawable(R.drawable.love4);
        mDrawableNum = mLoveDrawables.length;

        mLoveDrawableWidth = mLoveDrawables[0].getIntrinsicWidth();
        mLoveDrawableHeight = mLoveDrawables[0].getIntrinsicHeight();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Debug.D("onTouchEvent--->event=" + event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                Debug.D("onTouchEvent--->MotionEvent.ACTION_UP:添加一个点赞图");
                // View靠右下角位置不对，减去图片一半宽高矫正View位置
                addLove(event.getX() - mLoveDrawableWidth / 2, event.getY() - mLoveDrawableHeight / 2);
                break;
        }
        return true;
    }

    /**
     * 添加点赞图(随机)
     *
     * @param x
     * @param y
     */
    private void addLove(float x, float y) {
        int num = mRandom.nextInt(mDrawableNum);// 产生的随机数为0-mDrawableNum的整数,不包括mDrawableNum
        final ImageView loveImgView = new ImageView(mContext);
        loveImgView.setImageDrawable(mLoveDrawables[num]);
        setLoveOriginLocation(x, y, loveImgView);
        addView(loveImgView);
        loveAppearAnimation(loveImgView);
        loveBezierAnimation(x, y, loveImgView);
    }

    private void loveBezierAnimation(float x, float y, final View view) {
        PointF pointFA = new PointF(x, y);// 起点
        float xx = (float) (Math.random() * mViewWidth);
        if (xx > mLoveDrawableWidth){
            // 这里减去mLoveDrawableWidth是为了修正终点位置，让图片在布局内部
            xx = xx - mLoveDrawableWidth;
        }
        PointF pointFB = new PointF(xx, 0);// 终点
        PointF controlPoint1 = getControlPointF(1, pointFA);// 控制点1
        PointF controlPoint2 = getControlPointF(2, pointFA);// 控制点2

        // 估值器Evaluator,来控制view的路径（不断的修改point.x,point.y）
        Bezier3Evaluator evaluator = new Bezier3Evaluator(controlPoint1, controlPoint2);
        ValueAnimator animator = ValueAnimator.ofObject(evaluator, pointFA, pointFB);
        animator.setDuration(2500);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                removeView(view);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                removeView(view);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                updateLoveLocation(pointF.x, pointF.y, view);
                updateLoveAlpha(view, 1 - animation.getAnimatedFraction() + 0.25f);// 得到百分比
            }
        });
        animator.start();
    }

    /**
     * 获取控制点
     * @param i
     * @return
     */
    private PointF getControlPointF(int i, PointF touchPointF) {
//        float x = (float) (Math.random() * mViewWidth);
//        float y = (float) (Math.random() * touchPointF.y / 2) + (i - 1) * touchPointF.y / 2;
//        return new PointF(x, y);
        return new PointF((float) (Math.random() * mViewWidth), (float) (Math.random() * touchPointF.y));
    }

    private void updateLoveAlpha(View view, float alpha) {
        view.setAlpha(alpha);
    }

    /**
     * 点赞图出现的动画效果
     *
     * @param view
     */
    private void loveAppearAnimation(View view) {
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(view, "alpha", 0.2f, 1.0f);
        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.2f, 1.0f);
        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(view, "ScaleY", 0.2f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorAlpha, animatorScaleX, animatorScaleY);
        animatorSet.setDuration(250);
        animatorSet.start();
    }

    /**
     * 设置点赞起始位置
     *
     * @param x
     * @param y
     * @param view
     */
    private void setLoveOriginLocation(float x, float y, View view) {
        view.setX(x);
        view.setY(y);
    }

    /**
     * 更新点赞位置
     *
     * @param x
     * @param y
     * @param view
     */
    private void updateLoveLocation(float x, float y, View view) {
        view.setX(x);
        view.setY(y);
    }
}
