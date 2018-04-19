package com.mrrun.module_utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class IMEUtils {
    /**
     * 切换键盘显示与否状态
     * 如果输入法在窗口上已经显示，则隐藏，反之则显示
     * @param context
     */
    public static void toggleSoftInput(Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 调用系统默认的显示输入法
     * @param context
     * @param tagView
     */
    public static void showSoftInput(Context context, View tagView){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(tagView, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * 调用系统默认的隐藏输入法
     * @param context
     * @param tagView
     */
    public static void hideSoftInput(Context context, View tagView){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tagView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        imm.showSoftInput(tagView, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * 强制显示键盘
     * @param context
     * @param tagView
     */
    public static void forcedShowSoftInput(Context context, View tagView) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(tagView,InputMethodManager.SHOW_FORCED);
    }

    /**
     * 强制隐藏键盘
     * @param context
     * @param tagView
     */
    public static void forcedHideSoftInput(Context context, View tagView) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromInputMethod(tagView.getWindowToken(), 0);
    }

    /**
     * 获取输入法打开的状态
     * @param context
     * @return
     */
    public static boolean isOpen(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();// isOpen若返回true，则表示输入法打开
        return isOpen;
    }
}
