package com.mrrun.module_view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.mrrun.module_view.Debug;
import com.mrrun.module_view.DrawUtil;
import com.mrrun.module_view.R;

/**
 * 字母索引列表
 * 需求1：根据手指位置以及滑动改变选中的字母；
 *
 * @author lipin
 * @version 1.0
 * @date 2018/07/16
 */
public class AlphabeticIndexView extends View {

    private Context mContext;
    private int mIndexSize = 20;
    private float mIndexInterval = 10;
    private int mIndexGravity = CENTER | RIGHT;
    private int mViewWidth,mViewHeight;

    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    private static final int TOP = 2;
    private static final int BOTTOM = 3;
    private static final int CENTER = 4;
    private Paint mPaint;
    private int mIndexNormalColor = Color.GRAY;
    private int mIndexSelectedColor = Color.RED;
    private char[] mText = {'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static final int MAX_INDEX_LENGTH = 26;
    private static final int MIN_INDEX = 1;
    Rect rect = new Rect();
    private int mCurIndex = -1;

    public AlphabeticIndexView(Context context) {
        this(context, null);
    }

    public AlphabeticIndexView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlphabeticIndexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        initData(attrs);
        initPaint();
    }

    private void initData(AttributeSet attrs) {
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.AlphabeticIndexView);
        mIndexSize = array.getDimensionPixelSize(R.styleable.AlphabeticIndexView_index_size, mIndexSize);
        mIndexInterval = array.getDimension(R.styleable.AlphabeticIndexView_index_interval, mIndexInterval);
        mIndexGravity = array.getInt(R.styleable.AlphabeticIndexView_index_gravity, mIndexGravity);
        mIndexNormalColor = array.getColor(R.styleable.AlphabeticIndexView_index_normal_color, mIndexNormalColor);
        mIndexSelectedColor = array.getColor(R.styleable.AlphabeticIndexView_index_selected_color, mIndexSelectedColor);
        array.recycle();
        Debug.D(String.format("mIndexSize=%d, mIndexInterval=%f, mIndexGravity=%d, mIndexNormalColor=%d, mIndexSelectedColor=%d",
                mIndexSize, mIndexInterval, mIndexGravity, mIndexNormalColor, mIndexSelectedColor));
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(mIndexNormalColor);
        mPaint.setTextSize(mIndexSize);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        Debug.D(String.format("View 宽高(%d,%d)", w, h));

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        Debug.D(String.format("View 宽高(%d,%d)", w, h));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float x = 0, y = 0;
        for (int i = MIN_INDEX; i <= MAX_INDEX_LENGTH; i++) {// A-Z
            mPaint.getTextBounds(String.valueOf(mText[i - 1]), 0, 1, rect);
            x = 0;
            y = y + rect.height() / 2 + DrawUtil.textBaseLine(mPaint) + mIndexInterval;
            if (i == mCurIndex){
                mPaint.setColor(mIndexSelectedColor);
            } else {
                mPaint.setColor(mIndexNormalColor);
            }
            canvas.drawText(String.valueOf(mText[i - 1]), x , y  , mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Debug.D(String.format("View ACTION_DOWN"));
            case MotionEvent.ACTION_MOVE:{
                float downX = event.getX();
                float downY = event.getY();
                mPaint.getTextBounds("A", 0, 1, rect);
                if (0<= downX && downX <= rect.width() * 3) {
                    int i = (int) ((downY - 0) / (rect.height() + mIndexInterval)) + MIN_INDEX;
                    if (i <= MIN_INDEX) {
                        i = MIN_INDEX;
                    } else if (i >= MAX_INDEX_LENGTH) {
                        i = MAX_INDEX_LENGTH;
                    }
                    Debug.D(String.format("View ACTION_MOVE xyi(%f,%f,%d)", downX, downY, i));
                    if (mCurIndex != i){
                        mCurIndex = i;
                        Debug.D(String.format("View invalidate"));
                        invalidate();
                    }
                }
                break;
            }
        }
        return true;
    }
}
