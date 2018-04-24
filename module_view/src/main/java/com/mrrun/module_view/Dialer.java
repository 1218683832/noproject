package com.mrrun.module_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author lipin
 * @version 1.0
 * @date 2018/04/18
 *
 * 拨号盘
 */
public class Dialer extends ViewGroup{

    private int mWidth = 0;

    private int mHeight = 0;

    public Dialer(Context context) {
        super(context);
    }

    public Dialer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Dialer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mWidth = this.getMeasuredWidth();
        mHeight = this.getMeasuredHeight();
        Debug.D("mWidth: " + mWidth);
        Debug.D("mHeight: " + mHeight);
        int widthFactor = mWidth / 3;
        int childLeft = 0;
        int childRight = 0;
        int childTop = 0;
        int childBottom = 0;
        final int count = getChildCount();
        for(int i = 0; i <= count; i++) {
            final int children = i;
            final View child = getChildAt(children);
            if (child == null) {
            } else if (child.getVisibility() != View.GONE) {
                int hIndex = i / 3;
                int lIndex = i % 3;
                Debug.D("hIndex: " + hIndex);
                Debug.D("lIndex: " + lIndex);
                childLeft = childLeft + child.getPaddingLeft();
                childTop = childTop + childBottom;
                childBottom = childBottom + child.getMeasuredHeight();
                child.layout(widthFactor * hIndex, childTop, widthFactor * hIndex + widthFactor, childBottom);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 计算所有childView的宽高
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        // 设置容器测量宽高
        setMeasuredDimension(getSize(100, widthMeasureSpec), getSize(100, heightMeasureSpec));
    }

    private int getSize(int defaultSize, int measureSpec) {
        int returnSize = defaultSize;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {// 如果是没有任何限制，就设置为默认大小
                returnSize = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST: {// 如果是随着内容大小模式
                // 我们将大小取最大值,你也可以取其他值
                returnSize = defaultSize;
                break;
            }
            case MeasureSpec.EXACTLY: {// 如果是精确模式，那就不要去改变它
                returnSize = size;
                break;
            }
        }
        return returnSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setShader(new Shader());
//        canvas.drawLine(0, 0, 200, 200, paint);
    }
}
