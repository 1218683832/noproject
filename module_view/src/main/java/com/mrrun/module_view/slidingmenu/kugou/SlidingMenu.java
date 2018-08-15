package com.mrrun.module_view.slidingmenu.kugou;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.mrrun.module_view.Debug;

/**
 * 仿酷狗音乐的侧滑菜单栏
 *
 * 1、只允许向ViewGroup添加最多2个子View；
 * 2、展开/关闭内容View动画；
 * 3、拦截点击/滑动事件，响应展开/关闭内容View动画；
 * 4、侧边栏跟随手指滑动而缓慢展开/关闭；
 * 5、有遮盖层；
 *
 * @author lipin
 * @version 1.0
 * @date 2018/08/02
 */
public class SlidingMenu extends FrameLayout {

    private Context mContext;
    /**
     * ViewGroup的宽高
     **/
    private int mViewWidth, mViewHeight;
    /**
     * 遮盖内容窗口的浮层，用来增加效果
     **/
    private View mMaskView;
    /**
     * 内容窗口
     **/
    private View mContentView;
    /**
     * 侧边栏
     **/
    private View mSidebar;
    /**
     * 最多子View个数
     **/
    private final int MAX_CHILDVIEW = 2;
    /**
     * 可进行侧滑的比例检测值(View宽度的比例)
     **/
    private final float SIDESLIP_RATIO_MIN = 0.1f;
    private final float SIDESLIP_RATIO_MAX = 0.83f;
    /**
     * 可进行侧滑的范围值
     **/
    private float mSideRangWidthMin, mSideRangWidthMax;
    /**
     * 遮盖的透明值范围值
     **/
    private float MASK_ALPHA_MIN = 0.0f, MASK_ALPHA_MAX = 0.5f;
    /**
     * 侧滑菜单栏的打开关闭状态
     **/
    private boolean mHasContentClosed = false;
    /**
     * ViewGroup是否拦截事件
     **/
    private boolean mHasIntercept = false;
    private long ANIM_DURATION = 250;

    public SlidingMenu(@NonNull Context context) {
        this(context, null);
    }

    public SlidingMenu(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        initData(attrs);
        Debug.D(String.format("init"));
    }

    private void initData(AttributeSet attrs) {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mViewWidth = w;
        this.mViewHeight = h;
        this.mSideRangWidthMin = mViewWidth * SIDESLIP_RATIO_MIN;
        this.mSideRangWidthMax = mViewWidth * SIDESLIP_RATIO_MAX;
        Debug.D(String.format("mViewWidth, mViewHeight=(%d, %d)", mViewWidth, mViewHeight));
        Debug.D(String.format("mSideRangWidthMin, mSideRangWidthMax=(%f, %f)", mSideRangWidthMin, mSideRangWidthMax));
        mSidebar.setTranslationX(-mSideRangWidthMax);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        mHasIntercept = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float downX = ev.getX();
                float downY = ev.getY();
                Debug.D(String.format("onInterceptTouchEvent--->手指按下时的坐标(%f, %f)", downX, downY));
                // 内容未关闭且手指在侧边栏左边一定范围内按下时ViewGroup拦截事件
                if (!mHasContentClosed && downX <= mSideRangWidthMin) {
                    mHasIntercept = true;
                }
                // 内容已关闭且手指在侧边栏右边一定范围内按下时ViewGroup拦截事件
                else if (mHasContentClosed && downX >= mSideRangWidthMax) {
                    mHasIntercept = true;
                }
                break;
        }
        Debug.D(String.format("onInterceptTouchEvent返回的结果真假值: " + mHasIntercept));
        return mHasIntercept;
    }

    /**
     * 手指初次按下时的坐标位置
     **/
    float downX, downY;
    /**
     * 内容View平移的距离
     **/
    float translationX;
    /**
     * 遮盖层ViewAlpha的值
     **/
    float alpha;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = false;
        if (mHasIntercept) {
            result = true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                Debug.D(String.format("onTouchEvent--->手指按下时的坐标(%f, %f)", downX, downY));
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mHasContentClosed) {
                    translationX = event.getX() - downX;
                    alpha = translationX * MASK_ALPHA_MAX / mSideRangWidthMax;
                    if (alpha >= MASK_ALPHA_MAX) {// 防止透明度抖动
                        alpha = MASK_ALPHA_MAX;
                    }
                } else {
                    translationX = event.getX();
                    alpha = translationX * MASK_ALPHA_MAX / mSideRangWidthMax;
                    if (alpha <= MASK_ALPHA_MIN) {// 防止透明度抖动
                        alpha = MASK_ALPHA_MIN;
                    }
                }
                Debug.D(String.format("translationX=" + translationX));
                Debug.D(String.format("alpha=" + alpha));
                mContentView.setTranslationX(translationX);
                mSidebar.setTranslationX(translationX - mSideRangWidthMax);
                mMaskView.setAlpha(alpha);
                mMaskView.setTranslationX(translationX);
                break;
            case MotionEvent.ACTION_UP:
                if (mHasContentClosed) {
                    expandContent();
                } else {
                    closeContent();
                }
                break;
        }
        Debug.D(String.format("onTouchEvent " + event.getAction()));
        Debug.D(String.format("onTouchEvent返回的结果真假值: " + result));
        return result;
    }

    /**
     * 关闭内容动画
     */
    private void closeContent() {
        mHasContentClosed = true;
        ObjectAnimator animator = ObjectAnimator.ofFloat(mContentView, "translationX", translationX, mSideRangWidthMax);
        animator.setDuration(ANIM_DURATION);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                float a = animation.getAnimatedFraction() * MASK_ALPHA_MAX;
                alpha = Math.max(a, alpha);// 防止透明度抖动
                Debug.D(String.format("alpha=" + alpha));
                mMaskView.setAlpha(alpha);
                mMaskView.setTranslationX(value);
                mSidebar.setTranslationX(value - mSideRangWidthMax);
            }
        });
    }

    /**
     * 展开内容动画
     */
    private void expandContent() {
        mHasContentClosed = false;
        ObjectAnimator animator = ObjectAnimator.ofFloat(mContentView, "translationX", translationX, 0);
        animator.setDuration(ANIM_DURATION);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                float a = MASK_ALPHA_MAX - animation.getAnimatedFraction() * MASK_ALPHA_MAX;
                alpha = Math.min(a, alpha);// 防止透明度抖动
                Debug.D(String.format("alpha=" + alpha));
                mMaskView.setAlpha(alpha);
                mMaskView.setTranslationX((Float) animation.getAnimatedValue());
                mMaskView.setTranslationX(value);
                mSidebar.setTranslationX(value - mSideRangWidthMax);
            }
        });
    }

    @Override
    protected void onFinishInflate() {// 布局xml解析完毕
        super.onFinishInflate();
        // 先判断ViewGroup中子View个数
        int count = this.getChildCount();
        if (count > MAX_CHILDVIEW) {
            throw new IllegalStateException(String.format("子View不能大于%d个", MAX_CHILDVIEW));
        }
        if (count < MAX_CHILDVIEW) {
            throw new IllegalStateException(String.format("子View不能小于%d个", MAX_CHILDVIEW));
        }
        mSidebar = getChildAt(0);
        mContentView = getChildAt(1);
        addMaskView();
        Debug.D(String.format("onFinishInflate"));
    }

    private void addMaskView() {
        mMaskView = new View(mContext);
        mMaskView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mMaskView.setBackgroundColor(Color.parseColor("#222222"));
        mMaskView.setAlpha(MASK_ALPHA_MIN);
        mMaskView.setVisibility(VISIBLE);
        addView(mMaskView, 2);
    }
}
