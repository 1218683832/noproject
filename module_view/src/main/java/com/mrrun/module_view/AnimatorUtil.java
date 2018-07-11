package com.mrrun.module_view;

import android.animation.AnimatorSet;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * 属性动画工具类
 *
 * @author lipin
 * @version 1.0
 * @date 2018/07/10
 */
public class AnimatorUtil {
    /**
     * 默认延迟进入动画时间
     */
    public static long FADE_START_TIME = ViewConfiguration.getScrollDefaultDelay() + 125;

    /**
     * Start animation.
     *
     * @param animatorSet
     *         the animator set
     * @param view
     *         the view
     */
    public static void startAnimation(final AnimatorSet animatorSet, View view) {
        if (null == view) {
            return;
        }
        if (!animatorSet.isRunning()) {
            view.post(new Runnable() {
                @Override
                public void run() {
                    animatorSet.start();
                }
            });
        }
    }

    /**
     * Stop animation.
     *
     * @param animatorSet
     *         the animator set
     */
    public static void stopAnimation(AnimatorSet animatorSet) {
        if (animatorSet.isStarted() || animatorSet.isRunning() || animatorSet.isPaused()) {
            animatorSet.cancel();
        }
    }

    /**
     * Pause animation.
     *
     * @param animatorSet
     *         the animator set
     */
    public static void pauseAnimation(AnimatorSet animatorSet) {
        if (animatorSet.isRunning()) {
            animatorSet.pause();
        }
    }

    /**
     * Resume animation.
     *
     * @param animatorSet
     *         the animator set
     * @param view
     *         the view
     */
    public static void resumeAnimation(final AnimatorSet animatorSet, View view) {
        if (null == view) {
            animatorSet.cancel();
        }
        if (animatorSet.isPaused()) {
            // 2018/07/10
            // 解决Activity从stop状态恢复到resume状态后View显示动画继续不流畅问题
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    animatorSet.resume();
                }
            }, FADE_START_TIME);
        }
    }
}
