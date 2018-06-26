package com.mrrun.module_floatview.nopermission;

import android.content.Context;
import android.util.Log;
import android.view.WindowManager;

/**
 * 悬浮框管理
 * 奇淫技巧无需权限显示悬浮窗
 * @author lipin
 * @date 2018/06/25
 * @version 1.0
 */
public class FloatViewWindowManager {

    private static final String TAG = "FloatViewWindowManager";

    private static volatile FloatViewWindowManager instance;

    private WindowManager mWindowManager = null;
    private FloatView mFloatView;

    public static FloatViewWindowManager getInstance() {
        if (instance == null) {
            synchronized (FloatViewWindowManager.class) {
                if (instance == null) {
                    instance = new FloatViewWindowManager();
                }
            }
        }
        return instance;
    }

    /**
     * 显示
     * @param context
     */
    public void showFloatView(Context context) {
        if (mFloatView == null) {
            mFloatView = new FloatView(context.getApplicationContext());
        }
        if (mFloatView.isAdded()) {
            Log.i(TAG, "view is already added here");
            return;
        }
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        }
        mFloatView.setAdded(true);
        mWindowManager.addView(mFloatView, mFloatView.getmParams());
    }

    /**
     *
     */
    public void dismissFloatView() {
        if (mFloatView == null) {
            return;
        }
        if (!mFloatView.isAdded()) {
            Log.e(TAG, "window can not be dismiss cause it has not been added");
        }
        mFloatView.setAdded(false);
        if (mWindowManager != null) {
            mWindowManager.removeViewImmediate(mFloatView);
        }
    }
}
