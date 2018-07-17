package com.mrrun.module_view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.mrrun.module_view.Debug;
import com.mrrun.module_view.DrawUtil;
import com.mrrun.module_view.R;

/**
 * 索引侧边栏
 * 需求1：默认按26字母索引;
 * 需求2：View能够响应左右padding;
 * 需求3：索引数值都在一条竖直线显示美观;
 * 需求4：对外提供touch接口回调;
 *
 * @author lipin
 * @version 1.0
 * @date 2018/07/17
 */
public class IndexSidebar extends View {

    private Context mContext;
    /**
     * 默认索引值大小
     */
    private int mIndexSize = 15;
    /**
     * 默认索引值间隔
     */
    private int mIndexInterval = 6;
    private Paint mNormalPaint, mSelectedPaint, mSelectedBgPaint;
    /**
     * 索引未选中颜色
     */
    private int mIndexNormalColor = Color.GRAY;
    /**
     * 索引选中颜色
     */
    private int mIndexSelectedColor = Color.RED;
    /**
     * 索引选中的背景颜色
     */
    private int mIndexSelectedBgColor = Color.BLUE;
    /**
     * View实际宽高
     */
    private int mViewWidth, mViewHeight;
    /**
     * 索引内容
     */
    private static String[] mIndexTexts = {
            "A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};
    private static int MAX_INDEX = mIndexTexts.length - 1;
    private static int MIN_INDEX = 0;
    /**
     * 一个索引内容范围
     */
    private Rect mTextRect = new Rect();
    private int mCurIndex = -1;

    public interface OnTouchIndexSidebarListener {
        /**
         * 当前选中的索引
         *
         * @param text
         * @param position
         */
        public void onSelecedIndex(String text, int position);

        /**
         * 手指抬起离开屏幕
         */
        public void onFingerUp();
    }

    private OnTouchIndexSidebarListener mListener;

    public IndexSidebar(Context context) {
        this(context, null);
    }

    public IndexSidebar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexSidebar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        initData(attrs);
        initPaint();
    }

    private void initPaint() {
        mNormalPaint = new Paint();
        mNormalPaint.setColor(mIndexNormalColor);
        mNormalPaint.setAntiAlias(true);
        mNormalPaint.setTextSize(mIndexSize);

        mSelectedPaint = new Paint();
        mSelectedPaint.setColor(mIndexSelectedColor);
        mSelectedPaint.setAntiAlias(true);
        mSelectedPaint.setTextSize(mIndexSize);

        mSelectedBgPaint = new Paint();
        mSelectedBgPaint.setColor(mIndexSelectedBgColor);
        mSelectedBgPaint.setAntiAlias(true);
        mSelectedBgPaint.setStyle(Paint.Style.FILL);
    }

    private void initData(AttributeSet attrs) {
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.IndexSidebar);
        mIndexSize = array.getDimensionPixelSize(R.styleable.IndexSidebar_indexSize, sp2px(mIndexSize));
        mIndexInterval = (int) array.getDimension(R.styleable.IndexSidebar_indexInterval, dp2px(mIndexInterval));
        mIndexNormalColor = array.getColor(R.styleable.IndexSidebar_indexNormalColor, mIndexNormalColor);
        mIndexSelectedColor = array.getColor(R.styleable.IndexSidebar_indexSelectedColor, mIndexSelectedColor);
        mIndexSelectedBgColor = array.getColor(R.styleable.IndexSidebar_indexSelectedBgColor, mIndexSelectedBgColor);
        array.recycle();
    }

    public void setOnTouchIndexSidebarListener(OnTouchIndexSidebarListener listener) {
        this.mListener = listener;
    }

    private int sp2px(int spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, mContext.getResources().getDisplayMetrics());
    }

    private int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, mContext.getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 只根据索引的内容来设置宽高
        int w = 0;
        int h = 0;
        for (int i = 0; i <= MAX_INDEX; i++) {
            mNormalPaint.getTextBounds(mIndexTexts[i], 0, mIndexTexts[i].length(), mTextRect);
            // 1、计算索引文字中最大的宽度
            w = Math.max(w, mTextRect.width());
            // 2、累计索引文字总高度
            Debug.D(String.format("索引文字高度h=%d", mTextRect.height()));
            h = h + mTextRect.height();
        }
        Debug.D(String.format("计算索引文字中最大的宽度w=%d", w));
        Debug.D(String.format("累计索引文字和间隔总共高度h=%d", h));
        mViewWidth = w + getPaddingLeft() + getPaddingRight();
        // 上下都间隔
        mViewHeight = h + mIndexInterval * (MAX_INDEX);
        Debug.D(String.format("View的实际宽高wh(%d,%d)", mViewWidth, mViewHeight));
        setMeasuredDimension(mViewWidth, mViewHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // text的起点
        float x = 0;
        // text的baseline
        float y = 0;
        float cx = mViewWidth / 2;
        float cy = 0;
        float dy = DrawUtil.textBaseLine2(mNormalPaint);
        for (int i = 0; i <= MAX_INDEX; i++) {
            mNormalPaint.getTextBounds(mIndexTexts[i], 0, mIndexTexts[i].length(), mTextRect);
            // 计算让每个索引都居中
            x = mViewWidth / 2 - mTextRect.width() / 2;
            // 计算每个索引的baseline
            y = y + mTextRect.height() / 2 + mIndexInterval + dy;
            // 背景圆的中心点
            cy = y - dy;
            if (i == mCurIndex) {
                canvas.drawCircle(cx, cy, (Math.max(mTextRect.width(), mTextRect.height()) + mIndexInterval)/ 2, mSelectedBgPaint);
                canvas.drawText(mIndexTexts[i], x, y, mSelectedPaint);
            } else {
                canvas.drawText(mIndexTexts[i], x, y, mNormalPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float downX = event.getX();
                float downY = event.getY();
                int i = (int) (downY / (mViewHeight / MAX_INDEX));
                if (i <= MIN_INDEX) {
                    i = MIN_INDEX;
                } else if (i >= MAX_INDEX) {
                    i = MAX_INDEX;
                }
                Debug.D(String.format("View ACTION_MOVE xyi(%f,%f,%d)", downX, downY, i));
                if (mCurIndex != i) {
                    mCurIndex = i;
                    if (null != mListener) {
                        mListener.onSelecedIndex(mIndexTexts[i], i);
                    }
                    Debug.D(String.format("View invalidate"));
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (null != mListener) {
                    mListener.onFingerUp();
                }
                break;
        }
        return true;
    }
}
