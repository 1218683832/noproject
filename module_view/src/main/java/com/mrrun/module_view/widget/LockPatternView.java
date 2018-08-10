package com.mrrun.module_view.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
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
 * <p>
 * 需求;
 * 1、可手动设置是否允许绘制九宫格解锁;
 * 2、对外提供接口;
 * 3、重置密码;
 *
 * @author lipin
 * @version 1.0
 * @date 2018/07/24
 */
public class LockPatternView extends BaseView {

    public static final int ERROR = -1;
    public static final int NORMAL = 0;
    public static final int TOUCHING = 1;
    public static final int SUCCEED = 2;

    private int mStatus = NORMAL;
    /**
     * View宽高
     */
    private int mViewWidth, mViewHeight;
    /**
     * 九宫格锁正常情况下画笔
     */
    private Paint mNornalPaint;
    /**
     * 九宫格锁正常颜色白色
     */
    private int mLockNormalColor = Color.WHITE;
    /**
     * 九宫格锁手指触摸划过后情况下画笔
     */
    private Paint mTouchPaint;
    /**
     * 九宫格锁错误情况下画笔
     */
    private Paint mErrorPaint;
    /**
     * 九宫格锁手错误情况下画笔实心圆
     */
    private Paint mErrorFillPaint;
    /**
     * 九宫格锁错误情况颜色红色
     */
    private int mLockErrorColor = Color.RED;
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
    private int mLockR = 36;
    /**
     * 锁内圆圈的半径
     */
    private int mLockRR = mLockR / 3;
    /**
     * 锁的上下左右间隔
     */
    private int mLockInterval = 44;
    private int mShakeRange = 20;
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
    /**
     * 是否允许绘制解锁
     */
    private boolean mCanDraw = true;
    /**
     * 是否重置手势密码，相当于要不要设置手势密码
     */
    private boolean mResetPassword = true;
    private boolean mIsFirstDraw = true;

    public void setOnLockListener(OnLockListener onLockListener) {
        this.mOnLockListener = onLockListener;
    }

    /**
     * 九宫格回调接口
     */
    public interface OnLockListener {
        /**
         * 第一次设置解锁
         */
        int FIRST_SET_UNLOCK = 0;
        /**
         * 第二次设置解锁
         */
        int SECOND_SET_UNLOCK = 1;
        /**
         * 解锁
         */
        int ONLY_UNLOCK = 2;
        /**
         * 解锁失败
         */
        int UNLOCK_FAIL = 3;
        /**
         * 解锁成功
         */
        int UNLOCK_SUCCEED = 4;
        /**
         * 设置失败
         */
        int SET_FAIL = 5;
        /**
         * 设置成功
         */
        int SET_SUCCEED = 6;
        /**
         * 手指滑动结束时回调的当前绘制密码
         *
         * @param currentPassword
         * @param type
         */
        void onLockedPassword(String currentPassword, int type);
        /**
         * 比较两次密码是否相同
         */
        boolean onLockedCompared();

        void onSucceed(int code);

        void onFail(int code);
    }

    public LockPatternView(Context context) {
        this(context, null);
    }

    public LockPatternView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LockPatternView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override
    protected void init(AttributeSet attrs) {
        initData(attrs);
        initPaint();
        initListener();
    }

    private void initListener() {
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

    private void reset() {
        mLockPosition.clear();
        LockInfo.S = 0;
        mStatus = NORMAL;
        postInvalidateDelayed(200);
    }

    /**
     * 解锁失败动画
     */
    private void unlockFailAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "translationX",0, -mShakeRange,
                mShakeRange, 0);
        animator.setDuration(250);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                reset();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    /**
     * 解锁成功动画
     */
    private void unlockSucceedAnimation() {
        reset();
    }

    @Override
    protected void initData(AttributeSet attrs) {
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.LockPatternView);
        mBackgroundColor = array.getColor(R.styleable.LockPatternView_viewBackground, mBackgroundColor);
        mLockNormalColor = array.getColor(R.styleable.LockPatternView_ringNormalColor, mLockNormalColor);
        mLockTouchColor = array.getColor(R.styleable.LockPatternView_ringTouchColor, mLockTouchColor);
        mLockR = (int) array.getDimension(R.styleable.LockPatternView_lockRadius, dp2px(mLockR));
        mLockRR = mLockR / 3;
        mLockInterval = (int) array.getDimension(R.styleable.LockPatternView_lockInterval, dp2px(mLockInterval));
        mShakeRange = dp2px(mShakeRange);
        array.recycle();
        Debug.D(String.format("mBackgroundColor=%d,mLockNormalColor=%d,mLockR=%d,mShakeRange=%d",
                mBackgroundColor, mLockNormalColor, mLockR,mShakeRange));
    }

    @Override
    protected void initPaint() {
        mNornalPaint = createCommonPaint();
        mNornalPaint.setStyle(Paint.Style.STROKE);
        mNornalPaint.setColor(mLockNormalColor);

        mTouchPaint = createCommonPaint();
        mTouchPaint.setStyle(Paint.Style.STROKE);
        mTouchPaint.setColor(mLockTouchColor);

        mTouchFillPaint = createCommonPaint();
        mTouchFillPaint.setStyle(Paint.Style.FILL);
        mTouchFillPaint.setColor(mLockTouchColor);

        mErrorPaint = createCommonPaint();
        mErrorPaint.setStyle(Paint.Style.STROKE);
        mErrorPaint.setColor(mLockErrorColor);

        mErrorFillPaint = createCommonPaint();
        mErrorFillPaint.setStyle(Paint.Style.FILL);
        mErrorFillPaint.setColor(mLockErrorColor);

        mBackgroudPaint = createCommonPaint();
        mBackgroudPaint.setColor(mBackgroundColor);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Debug.D("onTouchEvent--->MotionEvent " + event.getAction());
        if (!mCanDraw) {// 不允许绘制解锁密码
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mStatus = TOUCHING;
                mFingerPositionX = (int) event.getX();
                mFingerPositionY = (int) event.getY();
                checkLock(mFingerPositionX, mFingerPositionY);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                mFingerPositionX = (int) event.getX();
                mFingerPositionY = (int) event.getY();
                checkLock(mFingerPositionX, mFingerPositionY);
                break;
            }
            case MotionEvent.ACTION_UP: {
                String pwd = obtainPassword();
                int type = obtainType();
                mOnLockListener.onLockedPassword(pwd, type);
                if (type == OnLockListener.SECOND_SET_UNLOCK || type == OnLockListener.ONLY_UNLOCK) {
                    boolean ss = mOnLockListener.onLockedCompared();
                    int code = obtainCode(ss);
                    if (ss) {
                        mStatus = SUCCEED;
                        mOnLockListener.onSucceed(code);
                        unlockSucceedAnimation();
                    } else {
                        mStatus = ERROR;
                        mOnLockListener.onFail(code);
                        unlockFailAnimation();
                    }
                    invalidate();
                }
                break;
            }
        }
        return true;
    }

    private int obtainCode(boolean b) {
        if (b && mResetPassword) {// 设置成功
            mIsFirstDraw = false;
            mResetPassword = false;
            return OnLockListener.SET_SUCCEED;
        }
        if (!b && mResetPassword) {// 设置失败
            mIsFirstDraw = true;
            mResetPassword = true;
            return OnLockListener.SET_FAIL;
        }
        if (b && !mResetPassword){// 解锁成功
            return OnLockListener.UNLOCK_SUCCEED;
        }
        if (!b && !mResetPassword){// 解锁失败
            return OnLockListener.UNLOCK_FAIL;
        }
        return OnLockListener.UNLOCK_FAIL;
    }

    /**
     * 获得当前解锁绘制操作的类型
     */
    private int obtainType() {
        if (mResetPassword && mIsFirstDraw) {// 第一次设置密码
            mIsFirstDraw = false;
            return OnLockListener.FIRST_SET_UNLOCK;
        } else  if (mResetPassword) {// 第二次设置密码
            return OnLockListener.SECOND_SET_UNLOCK;
        } else {// 解锁密码
            return OnLockListener.ONLY_UNLOCK;
        }
    }

    /**
     * 获得绘制完后的密码串
     *
     * @return
     */
    private String obtainPassword() {
        String pwd = "";
        if (null != mOnLockListener) {
            for (LockInfo l : mLockPosition) {
                pwd = pwd + l.position;
            }
        }
        return pwd;
    }

    private void checkLock(int x, int y) {
        for (LockInfo l : mLockInfos) {
            if (l.centerPoint.x - mLockR <= x && l.centerPoint.x + mLockR >= x) {
                if (l.centerPoint.y - mLockR <= y && l.centerPoint.y + mLockR >= y) {
                    if (!mLockPosition.contains(l)) {
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
                lockInfo.position = LockInfo.S++;
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

    /**
     * 是否重置手势密码
     *
     * @param resetPassword
     */
    public void resetPassword(boolean resetPassword) {
        this.mResetPassword = resetPassword;
        this.mIsFirstDraw = resetPassword;
    }

    /**
     * 设置是否允许绘制
     *
     * @param canDraw
     */
    public void setCanDraw(boolean canDraw) {
        this.mCanDraw = canDraw;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 画背景色
        canvas.drawRect(0, 0, mViewWidth, mViewHeight, mBackgroudPaint);
        // 把每个锁画出来
        drawNormalLocks(canvas);
        switch (mStatus){
            case NORMAL:
            case SUCCEED:
                break;
            case TOUCHING:
                drawTouchingEffect(canvas);
                break;
            case ERROR:
                drawErrorLocks(canvas);
                break;
        }
    }

    private void drawErrorLocks(Canvas canvas) {
        if (null != mLockPosition && mLockPosition.size() > 0) {
            for (int i = 0; i < mLockPosition.size(); i++) {
                LockInfo ll = mLockPosition.get(i);
                canvas.drawCircle(ll.centerPoint.x, ll.centerPoint.y, mLockR, mErrorPaint);
                canvas.drawCircle(ll.centerPoint.x, ll.centerPoint.y, mLockRR, mErrorFillPaint);
                if (mLockPosition.size() >= 2 && i > 0) {// 画线
                    LockInfo lll = mLockPosition.get(i - 1);
                    canvas.drawLine(lll.centerPoint.x, lll.centerPoint.y, ll.centerPoint.x, ll.centerPoint.y, mErrorPaint);
                }
            }
        }
    }

    /**
     * 画锁手指划过情况效果
     * @param canvas
     */
    private void drawTouchingEffect(Canvas canvas) {
        if (null != mLockPosition && mLockPosition.size() > 0) {
            for (int i = 0; i < mLockPosition.size(); i++) {
                LockInfo ll = mLockPosition.get(i);
                canvas.drawCircle(ll.centerPoint.x, ll.centerPoint.y, mLockR, mTouchPaint);
                canvas.drawCircle(ll.centerPoint.x, ll.centerPoint.y, mLockRR, mTouchFillPaint);
                if (mLockPosition.size() >= 2 && i > 0) {// 画线
                    LockInfo lll = mLockPosition.get(i - 1);
                    canvas.drawLine(lll.centerPoint.x, lll.centerPoint.y, ll.centerPoint.x, ll.centerPoint.y, mTouchPaint);
                }
            }
        }
    }

    /**
     * 把每个锁画出来
     * @param canvas
     */
    private void drawNormalLocks(Canvas canvas) {
        if (null != mLockInfos && mLockInfos.size() > 0) {
            for (LockInfo l : mLockInfos) {
                // 画锁正常情况效果
                canvas.drawCircle(l.centerPoint.x, l.centerPoint.y, mLockR, mNornalPaint);
                if (!mCanDraw) {
                    continue;
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
class LockInfo {
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