package com.mrrun.module_view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.mrrun.module_view.Debug;
import com.mrrun.module_view.R;

/**
 * 自定义评分控件
 *
 * @author lipin
 * @version 1.0
 * @date 2018/07/15
 */
public class RatingBar extends View {

    private Context mContext;
    /**
     * 评分等级
     */
    private int mGradeNumber = 5;
    /**
     * 评分
     */
    private float mRating = 0;
    /**
     * 当前评分
     */
    private int mCurRating = 0;
    /**
     * 评分图片
     */
    private Bitmap mStarNormalBitmap, mStarFousedBitmap;


    public RatingBar(Context context) {
        this(context, null);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        initData(attrs);
        initPaint();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    private void initData(AttributeSet attrs) {
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.RatingBar);
        mGradeNumber = array.getInt(R.styleable.RatingBar_numStars, mGradeNumber);
        mRating = array.getFloat(R.styleable.RatingBar_rating, mRating);
        int starNormalId = array.getResourceId(R.styleable.RatingBar_starNormal, R.drawable.rating_normal);
        int starFousedId = array.getResourceId(R.styleable.RatingBar_starFoused, R.drawable.rating_foused);
        mStarNormalBitmap = BitmapFactory.decodeResource(getResources(), starNormalId, null);
        mStarFousedBitmap = BitmapFactory.decodeResource(getResources(), starFousedId, null);
        array.recycle();
        Debug.D(String.format("mGradeNumber=%d,mRating=%f",mGradeNumber,mRating));
        Debug.D(String.format("mStarNormalBitmap(width=%d,height=%d)",mStarNormalBitmap.getWidth(),mStarNormalBitmap.getHeight()));
        Debug.D(String.format("mStarFousedBitmap(width=%d,height=%d)",mStarFousedBitmap.getWidth(),mStarFousedBitmap.getHeight()));
        setClickable(true);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Debug.D(String.format("onTouch ACTION_DOWN"));
                        return false;
                    case MotionEvent.ACTION_MOVE:
                        Debug.D(String.format("onTouch ACTION_MOVE"));
                        return false;
                    case MotionEvent.ACTION_UP:
                        Debug.D(String.format("onTouch ACTION_UP"));
                        return false;
                }
                return false;
            }
        });
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Debug.D("onClick");
            }
        });
    }

    @Override
    public boolean performClick() {
        Debug.D("performClick");
        return super.performClick();
    }

    private void initPaint() {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Debug.D(String.format("onTouchEvent ACTION_DOWN"));
            case MotionEvent.ACTION_MOVE:
                Debug.D(String.format("onTouchEvent ACTION_MOVE"));
            case MotionEvent.ACTION_UP:
                Debug.D(String.format("onTouchEvent ACTION_UP"));
                float dx = event.getX();
                Debug.D(String.format("dx=%f", dx));
                mCurRating = (int) (((dx - getPaddingLeft())/ mStarFousedBitmap.getWidth()) + 1);
                if (mCurRating <= 0){
                    mCurRating = 1;
                }
                if (mCurRating > mGradeNumber){
                    mCurRating = mGradeNumber;
                }
                Debug.D(String.format("评分=%d", mCurRating));
                invalidate();
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < mGradeNumber; i++) {
            int x = i * mStarFousedBitmap.getWidth();
            if (i < mCurRating){
                canvas.drawBitmap(mStarFousedBitmap, x, 0, null);
            } else {
                canvas.drawBitmap(mStarNormalBitmap, x, 0, null);
            }
        }
    }
}
