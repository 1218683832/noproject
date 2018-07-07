package com.mrrun.module_view.loadingview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.mrrun.module_view.Debug;

/**
 * 仿微信三点式加载动态View
 *
 * @author lipin
 * @version 1.0
 * @date 2018 /07/07
 */
public class LoadingView extends View {

    private Context mContext;
    private DisplayMetrics dm;
    private int mViewWidth;
    private int mViewHeight;
    /**
     * 点的间距
     */
    private int dotInterval;
    private int dotRadius;
    private Paint mDullPaint;
    private Paint mLightPaint;
    /**
     * View的中心点坐标
     */
    private float centerX;
    private float centerY;
    private int mLoadingPosition = 0;
    private boolean isLoading = false;

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
            removeCallbacks(mRunnable);
        }
    };

    public LoadingView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        dm = mContext.getResources().getDisplayMetrics();
        initData();
        initPaint();
    }

    private void initData() {
        dotRadius = dp2px(3.5f);
        dotInterval = dp2px(4.5f);
    }

    private void initPaint() {
        mDullPaint = new Paint();
        mDullPaint.setStyle(Paint.Style.FILL);
        mDullPaint.setColor(Color.parseColor("#cccccc"));
        mDullPaint.setAlpha(125);
        mDullPaint.setAntiAlias(true);

        mLightPaint = new Paint();
        mLightPaint.setStyle(Paint.Style.FILL);
        mLightPaint.setColor(Color.parseColor("#ffffff"));
        mLightPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mViewWidth = w;
        this.mViewHeight = h;
        this.centerX = mViewWidth / 2;
        this.centerY = mViewHeight / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas);
        Debug.D("onDraw " + isLoading);
        if (isLoading) {
            drawLoading(canvas, mLoadingPosition);
            mLoadingPosition = (mLoadingPosition + 1) % 3;
            postDelayed(mRunnable, 300);
        } else {
            drawLoading(canvas, mLoadingPosition);
        }
    }

    public void startLoading() {
        if (isLoading)
            return;
        isLoading = true;
        mLoadingPosition = 0;
        invalidate();
        Debug.D("isLoading " + isLoading);
    }

    public void stopLoading() {
        if (!isLoading)
            return;
        isLoading = false;
        Debug.D("isLoading " + isLoading);
    }

    private void drawLoading(Canvas canvas, int index) {
        Debug.D("mLoadingPosition " + index);
        if (index < 0 || index > 2)
            return;
        switch (index) {
            case 0: {
                canvas.drawCircle(centerX - dotInterval - 2 * dotRadius, centerY, dotRadius, mLightPaint);
                canvas.drawCircle(centerX, centerY, dotRadius, mDullPaint);
                canvas.drawCircle(centerX + dotInterval + 2 * dotRadius, centerY, dotRadius, mDullPaint);
                break;
            }
            case 1: {
                canvas.drawCircle(centerX - dotInterval - 2 * dotRadius, centerY, dotRadius, mDullPaint);
                canvas.drawCircle(centerX, centerY, dotRadius, mLightPaint);
                canvas.drawCircle(centerX + dotInterval + 2 * dotRadius, centerY, dotRadius, mDullPaint);
                break;
            }
            case 2: {
                canvas.drawCircle(centerX - dotInterval - 2 * dotRadius, centerY, dotRadius, mDullPaint);
                canvas.drawCircle(centerX, centerY, dotRadius, mDullPaint);
                canvas.drawCircle(centerX + dotInterval + 2 * dotRadius, centerY, dotRadius, mLightPaint);
                break;
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    private int dp2px(float dp) {
        return (int) (dm.density * dp + 0.5f);
    }
}
