package com.mrrun.module_view.loadingview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mrrun.module_view.AnimatorUtil;
import com.mrrun.module_view.BaseView;
import com.mrrun.module_view.Debug;
import com.mrrun.module_view.IBaseView;
import com.mrrun.module_view.R;

/**
 * 仿58同城LoadingView
 *
 * @author lipin
 * @version 1.0
 * @date 2018/07/18
 */
public class LoadingView58 extends LinearLayout implements IBaseView {

    private static final long DURATION = 400;
    private Context mContext;

    private int mCircleColor = Color.parseColor("#ff1a75");

    private int mTriangleColor = Color.YELLOW;

    private int mSquareColor = Color.BLUE;
    /**
     * 阴影部分的宽高
     */
    private int mShadowWidth = 35, mShadowHeight = 10;
    private Drawable mShadowDraable = null;

    private String mLoadingText = "玩命加载中...";

    private int mLoadingTextSize = 14;

    private int mLoadingTextColor = Color.BLACK;

    public LoadingView58(Context context) {
        this(context, null);
    }

    public LoadingView58(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView58(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }

    AnimatorSet animatorSet = new AnimatorSet();

    @Override
    public void init(AttributeSet attrs) {
        initData(attrs);
        initPaint();
        initView();
        post(new Runnable() {
            @Override
            public void run() {
                animatorSet.setStartDelay(AnimatorUtil.DELAY_START_TIME);
                animatorSet.play(riseInAnimation()).with(rotateAnimation()).before(fallInAnimation());
                animatorSet.start();
                animatorSet.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Debug.D("onAnimationEnd");
                        mShapeView.changeShape();
                        animatorSet.setStartDelay(0);
                        animatorSet.start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }
        });
    }

    private void initView() {
        setBackgroundColor(Color.GRAY);
        setGravity(Gravity.CENTER);
        setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(params);

        addShapeViewToParent(this);
        addImageViewToParent(this);
        addTextViewToParent(this);
    }

    ShapeView mShapeView;

    private void addShapeViewToParent(ViewGroup parent) {
        mShapeView = new ShapeView(mContext, mShadowWidth, mCircleColor, mSquareColor, mTriangleColor);
        parent.addView(mShapeView);
    }

    private void addTextViewToParent(ViewGroup parent) {
        TextView textView = new TextView(mContext);
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setTextColor(mLoadingTextColor);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLoadingTextSize);
        if (null != mLoadingText) {
            textView.setText(mLoadingText);
        }
        parent.addView(textView);
    }

    private void addImageViewToParent(ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        if (null != mShadowDraable) {
            imageView.setBackground(mShadowDraable);
        }
        imageView.setLayoutParams(new LayoutParams(mShadowWidth, mShadowHeight));
        parent.addView(imageView);
    }

    @Override
    public void initData(AttributeSet attrs) {
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.LoadingView58);
        mShadowDraable = array.getDrawable(R.styleable.LoadingView58_loadingview58_shadow_background);
        mCircleColor = array.getColor(R.styleable.LoadingView58_loadingview58_circle_color, mCircleColor);
        mSquareColor = array.getColor(R.styleable.LoadingView58_loadingview58_square_color, mSquareColor);
        mTriangleColor = array.getColor(R.styleable.LoadingView58_loadingview58_triangle_color, mTriangleColor);
        mShadowWidth = (int) array.getDimension(R.styleable.LoadingView58_loadingview58_shadow_background_width, dp2px(mShadowWidth));
        mShadowHeight = (int) array.getDimension(R.styleable.LoadingView58_loadingview58_shadow_background_height, dp2px(mShadowHeight));
        String text = array.getString(R.styleable.LoadingView58_loadingview58_loadingtext);
        if (null != text) {
            mLoadingText = text;
        }
        mLoadingTextSize = array.getDimensionPixelOffset(R.styleable.LoadingView58_loadingview58_loadingtext_size, sp2px(mLoadingTextSize));
        mLoadingTextColor = array.getColor(R.styleable.LoadingView58_loadingview58_loadingtext_color, mLoadingTextColor);
        array.recycle();
        Debug.D(String.format("mCircleColor=%d,mSquareColor=%d,mTriangleColor=%d", mCircleColor, mSquareColor, mTriangleColor));
    }

    private int sp2px(int spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, mContext.getResources().getDisplayMetrics());
    }

    private int dp2px(int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, mContext.getResources().getDisplayMetrics());
    }

    @Override
    public void initPaint() {
    }

    /**
     * 优化性能
     * @param visibility
     */
    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(View.INVISIBLE);
        ViewGroup parent = (ViewGroup) this.getParent();
        if(parent != null){
            parent.removeView(this);
            mShapeView.clearAnimation();
            this.removeAllViews();
        }
    }

    /**
     * Shap上升动画
     *
     * @return
     */
    private ObjectAnimator riseInAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mShapeView, "translationY", 0, -150);
//        animator.setRepeatCount(Animation.INFINITE);
        animator.setDuration(DURATION);
        animator.setInterpolator(new DecelerateInterpolator());
        return animator;
    }

    /**
     * Shape落动画
     *
     * @return
     */
    private ObjectAnimator fallInAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mShapeView, "translationY", -150, 0);
//        animator.setRepeatCount(Animation.INFINITE);
        animator.setDuration(DURATION);
//        animator.setInterpolator(new AccelerateInterpolator());
        return animator;
    }

    /**
     * Shape旋转动画
     *
     * @return
     */
    private ObjectAnimator rotateAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mShapeView, "rotation", 0, 90);
//        animator.setRepeatCount(Animation.INFINITE);
        animator.setDuration(DURATION);
        animator.setInterpolator(new AccelerateInterpolator());
        return animator;
    }
}

class ShapeView extends BaseView {

    private int mViewWidth;
    private int mViewHeight;
    private Paint mShapePaint;
    private int mCircleColor;
    private int mTriangleColor;
    private int mSquareColor;
    private int mCurShape;
    public static final int CIRCLE_SHAPE = 1;
    public static final int SQUARE_SHAPE = 2;
    public static final int TRIANGLE_SHAPE = 3;

    public ShapeView(Context context, int pxValue, int circleColor, int squareColor, int triangleColor) {
        super(context);
        mViewWidth = mViewHeight = pxValue;
        mCircleColor = circleColor;
        mSquareColor = squareColor;
        mTriangleColor = triangleColor;
        mCurShape = SQUARE_SHAPE;
        mShapePaint.setColor(mSquareColor);
    }

    @Override
    public void initData(AttributeSet attrs) {
    }

    @Override
    public void initPaint() {
        mShapePaint = createCommonPaint();
    }

    public void changeShape() {
        switch (mCurShape) {
            case ShapeView.CIRCLE_SHAPE:
                mCurShape = ShapeView.SQUARE_SHAPE;
                mShapePaint.setColor(mSquareColor);
                break;
            case ShapeView.SQUARE_SHAPE:
                mCurShape = ShapeView.TRIANGLE_SHAPE;
                mShapePaint.setColor(mTriangleColor);
                break;
            case ShapeView.TRIANGLE_SHAPE:
                mCurShape = ShapeView.CIRCLE_SHAPE;
                mShapePaint.setColor(mCircleColor);
                break;
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (mCurShape) {
            case ShapeView.CIRCLE_SHAPE:
                canvas.drawCircle(mViewWidth / 2, mViewHeight / 2, mViewWidth / 2, mShapePaint);
                break;
            case ShapeView.SQUARE_SHAPE:
                canvas.drawRect(0, 0, mViewWidth, mViewHeight, mShapePaint);
                break;
            case ShapeView.TRIANGLE_SHAPE:
                Path path = new Path();
                path.moveTo(0,mViewHeight);
                path.lineTo(mViewWidth / 2, 0);
                path.lineTo(mViewWidth, mViewHeight);
                canvas.drawPath(path, mShapePaint);
                break;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mViewWidth, mViewHeight);
    }
}