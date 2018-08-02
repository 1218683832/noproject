package com.mrrun.module_view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.mrrun.module_view.BaseView;
import com.mrrun.module_view.Debug;
import com.mrrun.module_view.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 九宫格解锁界面。
 * 基本完成，现在记录下思路:
 * 1、用LockInfo类记录每个锁的位置信息等；
 * 2、通过判断手指位置确定触摸了哪个锁并将该锁按顺序加入集合中；
 * 3、onDraw时画正常情况下的锁，判断是否有手指划过的锁，如果有那么画划过的锁样式以及横线，搞定。
 * 4、判断是否正确划过解锁可以转变为判断顺序集合中的数字是否一致即可；
 * 5、对外提供几个接口，绘制九宫格返回绘制密码；
 *
 * @author lipin
 * @version 1.0
 * @date 2018/07/24
 */
public class LockPatternView extends BaseView {

    /**
     * View宽高
     */
    private int mViewWidth, mViewHeight;
    /**
     * 九宫格锁正常情况下画笔
     */
    private Paint mNornalPaint;
    /**
     * 九宫格锁正常颜色红色
     */
    private int mLockNormalColor = Color.WHITE;
    /**
     * 九宫格锁手指触摸划过后情况下画笔
     */
    private Paint mTouchPaint;
    /**
     * 九宫格锁手指触摸划过后情况下画笔实心圆
     */
    private Paint mTouchFillPaint;
    /**
     * 九宫格锁指触摸划过后颜色红色
     */
    private int mLockTouchColor = Color.BLUE;
    /**
     * 九宫格背景画笔
     */
    private Paint mBackgroudPaint;
    /**
     * 背景默认颜色黑色
     */
    private int mBackgroundColor = Color.BLACK;
    /**
     * 锁的数量
     */
    private static final int LOCK_COUNT = 9;
    /**
     * 多少行锁
     */
    private static final int LOCK_ROW = 3;
    /**
     * 锁的半径
     */
    private int mLockR = 80;
    /**
     * 锁内圆圈的半径
     */
    private int mLockRR = mLockR / 3;
    /**
     * 锁的上下左右间隔
     */
    private int mLockInterval = 80;
    /**
     * 界面正常情况下的锁
     */
    private List<LockInfo> mLockInfos = new ArrayList<>();
    /**
     * 手指划过的锁
     */
    private List<LockInfo> mLockPosition = new ArrayList<>();
    /**
     * 手指触摸位置
     */
    private int mFingerPositionX, mFingerPositionY;
    private OnLockListener mOnLockListener;

    public void setOnLockListener(OnLockListener onLockListener) {
        this.mOnLockListener = onLockListener;
    }

    /**
     * 九宫格回调接口
     */
    public interface OnLockListener {
        /**
         * 手指滑动结束时回调的当前绘制密码
         */
        void onLockedPassword(String pwd);
    }

    public LockPatternView(Context context) {
        this(context, null);
    }

    public LockPatternView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LockPatternView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mNornalPaint.setColor(mLockNormalColor);
        mTouchPaint.setColor(mLockTouchColor);
        mTouchFillPaint.setColor(mLockTouchColor);
        mBackgroudPaint.setColor(mBackgroundColor);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Debug.D("onTouch--->MotionEvent " + event.getAction());
                Debug.D("onTouch--->MotionEvent " + v.toString());
                return false;
            }
        });
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Debug.D("onClick--->" + v.toString());
            }
        });
    }

    private void reset(){
        mLockPosition.clear();
    }

    @Override
    protected void init(AttributeSet attrs) {
        initData(attrs);
        initPaint();
    }

    @Override
    protected void initData(AttributeSet attrs) {
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.LockPatternView);
        mBackgroundColor = array.getColor(R.styleable.LockPatternView_viewBackground, mBackgroundColor);
        mLockNormalColor = array.getColor(R.styleable.LockPatternView_ringNormalColor, mLockNormalColor);
        mLockTouchColor = array.getColor(R.styleable.LockPatternView_ringTouchColor, mLockTouchColor);
        mLockR = (int) array.getDimension(R.styleable.LockPatternView_lockRadius,dp2px(mLockR));
        mLockRR = mLockR / 3;
        mLockInterval = (int) array.getDimension(R.styleable.LockPatternView_lockInterval,dp2px(mLockInterval));
        array.recycle();
        Debug.D(String.format("mBackgroundColor=%d,mLockNormalColor=%d,mLockR=%d", mBackgroundColor, mLockNormalColor,mLockR));
    }

    @Override
    protected void initPaint() {
        mNornalPaint = createCommonPaint();
        mNornalPaint.setStyle(Paint.Style.STROKE);

        mTouchPaint = createCommonPaint();
        mTouchPaint.setStyle(Paint.Style.STROKE);

        mTouchFillPaint = createCommonPaint();
        mTouchFillPaint.setStyle(Paint.Style.FILL);

        mBackgroudPaint = createCommonPaint();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Debug.D("onTouchEvent--->MotionEvent " + event.getAction());
        int x, y;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                mFingerPositionX = (int) event.getX();
                mFingerPositionY = (int) event.getY();
                checkLock(mFingerPositionX, mFingerPositionY);
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                mFingerPositionX = (int) event.getX();
                mFingerPositionY = (int) event.getY();
                checkLock(mFingerPositionX, mFingerPositionY);
                break;
            }
            case MotionEvent.ACTION_UP:{
                if (null != mOnLockListener) {
                    String pwd = "";
                    for (LockInfo l: mLockPosition) {
                        pwd = pwd + l.position;
                    }
                    mOnLockListener.onLockedPassword(pwd);
                }
                reset();
                invalidate();
                break;
            }
        }
        return true;
    }

    private void checkLock(int x, int y) {
        for (LockInfo l: mLockInfos) {
            if (l.centerPoint.x - mLockR <= x && l.centerPoint.x + mLockR >= x) {
                if (l.centerPoint.y - mLockR <= y && l.centerPoint.y + mLockR >= y){
                    if (!mLockPosition.contains(l)){
                        mLockPosition.add(l);
                        Debug.D("invalidate");
                        Debug.D("mLockPosition = " + mLockPosition.toString());
                        invalidate();
                    }
                }
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        mLockInfos.clear();
        Point fistLockPoint = calculateFirstLock();
        for (int i = 0; i < LOCK_COUNT / LOCK_ROW; i++) {
            for (int j = 0; j < LOCK_COUNT / LOCK_ROW; j++) {
                LockInfo lockInfo = new LockInfo();
                lockInfo.position = LockInfo.S ++;
                lockInfo.centerPoint.x = fistLockPoint.x + (mLockR + mLockInterval + mLockR) * i;
                lockInfo.centerPoint.y = fistLockPoint.y + (mLockR + mLockInterval + mLockR) * j;
                mLockInfos.add(lockInfo);
                Debug.D(lockInfo.toString());
            }
        }
    }

    /**
     * 计算第一个锁的位置点
     */
    private Point calculateFirstLock() {
        int x = mViewWidth / 2 - mLockR - mLockInterval - mLockR;
        int y = mViewHeight / 2 - mLockR - mLockInterval - mLockR;
        return new Point(x, y);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 画背景色
        canvas.drawRect(0, 0, mViewWidth, mViewHeight, mBackgroudPaint);
        // 把每个锁画出来
        if (null != mLockInfos && mLockInfos.size() > 0){
            for (LockInfo l: mLockInfos) {
                // 画锁正常情况效果
                canvas.drawCircle(l.centerPoint.x, l.centerPoint.y, mLockR, mNornalPaint);
                if (null != mLockPosition && mLockPosition.size() > 0){
                    for (int i = 0; i < mLockPosition.size(); i++) {
                        LockInfo ll = mLockPosition.get(i);
                        // 画锁手指划过情况效果
                        canvas.drawCircle(ll.centerPoint.x, ll.centerPoint.y, mLockR, mTouchPaint);
                        canvas.drawCircle(ll.centerPoint.x, ll.centerPoint.y, mLockRR, mTouchFillPaint);
                        if (mLockPosition.size() >= 2 && i > 0){// 画线
                            LockInfo lll = mLockPosition.get(i - 1);
                            canvas.drawLine(lll.centerPoint.x, lll.centerPoint.y, ll.centerPoint.x, ll.centerPoint.y, mTouchPaint);
                        }
                    }
                }
            }
        }
    }
}

/**
 * 九宫格界面每个锁的信息
 *
 * @author lipin
 * @version 1.0
 * @date 2018/08/01
 */
class LockInfo{
    public static int S = 0;
    /**
     * 第几把锁(0~8)
     */
    public int position = S;
    /**
     * 锁的中心点
     */
    public Point centerPoint = new Point();

    @Override
    public String toString() {
        return "LockInfo{" +
                "position=" + position +
                ", centerPoint=" + centerPoint +
                '}';
    }
}