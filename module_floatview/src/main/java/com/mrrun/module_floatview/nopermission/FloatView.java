package com.mrrun.module_floatview.nopermission;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.mrrun.module_floatview.R;

/**
 * 悬浮框View
 *
 * @author lipin
 * @version 1.0
 * @date 2018/06/25
 */
public class FloatView extends FrameLayout {

    private static final String TAG = "FloatView";

    /**
     * 是否被添加进WindowManager
     */
    private boolean isAdded = false;

    /**
     * 悬浮框正在吸附边框
     */
    private boolean isAnchoring = false;

    /**
     * 记录手指按下时在小悬浮窗的View上的横坐标的值
     */
    private float xInView;

    /**
     * 记录手指按下时在小悬浮窗的View上的纵坐标的值
     */
    private float yInView;

    /**
     * 记录当前手指位置在屏幕上的横坐标值
     */
    private float xInScreen;

    /**
     * 记录当前手指位置在屏幕上的纵坐标值
     */
    private float yInScreen;

    /**
     * 记录手指按下时在屏幕上的横坐标的值
     * <p>
     * /**
     * 记录手指按下时在屏幕上的横坐标的值
     */
    private float xDownInScreen;

    /**
     * 记录手指按下时在屏幕上的纵坐标的值
     */
    private float yDownInScreen;

    private WindowManager windowManager = null;
    private WindowManager.LayoutParams mParams = null;

    public FloatView(@NonNull Context context) {
        super(context);
        initView();
    }

    private void initView() {
        windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        initParams(getContext());
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View floatView = inflater.inflate(R.layout.lay_float_window, null);
        addView(floatView);
    }

    private void initParams(Context context) {
        /**
         * 获取屏幕宽高
         */
        Point size = new Point();
        windowManager.getDefaultDisplay().getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;
        Log.i(TAG, "screenWidth =" + screenWidth);
        Log.i(TAG, "screenHeight =" + screenHeight);

        mParams = new WindowManager.LayoutParams();
        mParams.packageName = context.getPackageName();
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

        int mType;
        // API level 19之后TYPE_TOAST就可以达到TYPE_SYSTEM_ALERT效果,API level 19之后TYPE_TOAST可以接受事件
        // 4.4开始, TYPE_TOAST被移除了。所以从 4.4 开始, 使用 TYPE_TOAST 的同时还可以接收触摸事件和按键事件了, 而4.4以前只能显示出来, 不能交互
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 解决Android 7.1.1起不能再用Toast的问题（先解决crash）
            if (Build.VERSION.SDK_INT > 24) {
                mType = WindowManager.LayoutParams.TYPE_PHONE;
            } else {
                // 啊哈，采用TYPE_TOAST能让用不开启悬浮窗权限的情况下，也能显示。为的就是尽量少请求权限。
                mType = WindowManager.LayoutParams.TYPE_TOAST;
            }
        } else {
            mType = WindowManager.LayoutParams.TYPE_PHONE;
        }

        mParams.type = mType;
        mParams.format = PixelFormat.RGBA_8888;
        /**
         * 定位悬浮框
         */
        mParams.gravity = Gravity.LEFT | Gravity.TOP;
        mParams.x = screenWidth - dp2px(context, 100);
        mParams.y = screenHeight - dp2px(context, 100);
    }

    private int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean intercept = true;
        if (isAnchoring) {
            return intercept;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                xInView = event.getX();
                yInView = event.getY();
                xDownInScreen = event.getRawX();
                yDownInScreen = event.getRawY();
                xInScreen = event.getRawX();
                yInScreen = event.getRawY();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                xInScreen = event.getRawX();
                yInScreen = event.getRawY();
                // 手指移动的时候更新悬浮窗的位置
                updateViewPosition();
                break;
            }
            case MotionEvent.ACTION_UP: {
                Log.i(TAG, "触发移动事件的最小距离 = " + ViewConfiguration.get(getContext()).getScaledTouchSlop());
                // ViewConfiguration.get(context).getScaledTouchSlop() 触发移动事件的最小距离，自定义View处理touch事件的时候，
                // 有的时候需要判断用户是否真的存在movie，系统提供了这样的方法。表示滑动的时候，手的移动要大于这个返回的距离值才开始移动控件。
                if (Math.abs(xDownInScreen - xInScreen) <= ViewConfiguration.get(getContext()).getScaledTouchSlop()
                        && Math.abs(yDownInScreen - yInScreen) <= ViewConfiguration.get(getContext()).getScaledTouchSlop()) {
                    // 点击效果
                    Toast.makeText(getContext(), "this float window is clicked", Toast.LENGTH_SHORT).show();
                } else {
                    // 吸附效果
                    anchorToSide();
                }
            }
        }
        Log.i(TAG, "onTouchEvent是否拦截消耗 =" + intercept);
        return intercept;
    }

    /**
     * 悬浮框吸附效果
     */
    private void anchorToSide() {
        isAnchoring = true;
        Point size = new Point();
        windowManager.getDefaultDisplay().getSize(size);
        // 屏幕宽高
        int screenWidth = size.x;
        int screenHeight = size.y;
        // 悬浮框中心点横坐标屏幕距离
        int middleX = mParams.x + getWidth() / 2;

        int animTime = 0;
        int xDistance = 0;
        int yDistance = 0;

        if (middleX <= screenWidth / 2) {
            xDistance = -mParams.x;
        } else {
            xDistance = screenWidth - mParams.x;
        }
        Log.e(TAG, "xDistance  " + xDistance + "   yDistance " + yDistance);
        animTime = (int) (((float) xDistance / (float) screenWidth) * 600f);
        this.post(new AnchorAnimRunnable(Math.abs(animTime), xDistance, yDistance, System.currentTimeMillis()));
    }

    /**
     * 更新悬浮窗的位置
     */
    private void updateViewPosition() {
        // 增加移动误差
        mParams.x = (int) (xInScreen - xInView);
        mParams.y = (int) (yInScreen - yInView);
        Log.e(TAG, "x  " + mParams.x + "   y  " + mParams.y);
        windowManager.updateViewLayout(this, mParams);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        isAdded = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isAdded = false;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    public WindowManager.LayoutParams getmParams() {
        return mParams;
    }

    private class AnchorAnimRunnable implements Runnable {

        private int animTime;
        private long currentStartTime;
        private Interpolator interpolator;
        private int xDistance;
        private int yDistance;
        private int startX;
        private int startY;

        public AnchorAnimRunnable(int animTime, int xDistance, int yDistance, long currentStartTime) {
            this.animTime = animTime;
            this.currentStartTime = currentStartTime;
            this.interpolator = new AccelerateDecelerateInterpolator();
            this.xDistance = xDistance;
            this.yDistance = yDistance;
            this.startX = mParams.x;
            this.startY = mParams.y;
        }

        @Override
        public void run() {
            if (System.currentTimeMillis() >= currentStartTime + animTime) {
                isAnchoring = false;
                return;
            }
            float delta = interpolator.getInterpolation((System.currentTimeMillis() - currentStartTime) / (float) animTime);
            int xMoveDistance = (int) (xDistance * delta);
            int yMoveDistance = (int) (yDistance * delta);
            mParams.x = startX + xMoveDistance;
            mParams.y = startY + yMoveDistance;
            windowManager.updateViewLayout(FloatView.this, mParams);
            FloatView.this.postDelayed(this, 16);
        }
    }
}
