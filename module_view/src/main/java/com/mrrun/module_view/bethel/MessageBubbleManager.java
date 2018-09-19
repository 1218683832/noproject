package com.mrrun.module_view.bethel;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.View;
import android.view.WindowManager;

public class MessageBubbleManager {

    private static MessageBubbleManager instance;
    private static WindowManager winManager;

    private MessageBubbleManager(){
    }

    public static synchronized MessageBubbleManager getInstance(Context context){
        if (null == instance){
            winManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
            instance = new MessageBubbleManager();
        }
        return instance;
    }

    public void addBubbleView(View view) {
        // 布局参数
        WindowManager.LayoutParams params = new WindowManager.LayoutParams() ;
        // 背景要透明
        params.format = PixelFormat.TRANSPARENT ;
        // 在应用窗口上方显示View，
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        // 在WindowManager上面要搞一个View，而这个View就是我们写好的 贝塞尔的View，然后把这个贝塞尔的View 添加到WindowManager上面来
        winManager.addView(view, params);
    }

    public void removeBubbleView(View view){
        winManager.removeViewImmediate(view);
    }

    public void addBombView(View view) {
        // 布局参数
        WindowManager.LayoutParams params = new WindowManager.LayoutParams() ;
        // 背景要透明
        params.format = PixelFormat.TRANSPARENT ;
        // 在应用窗口上方显示View，
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        // 在WindowManager上面要搞一个View，而这个View就是我们写好的 贝塞尔的View，然后把这个贝塞尔的View 添加到WindowManager上面来
        winManager.addView(view, params);
    }

    public void removeBombView(View view){
        winManager.removeViewImmediate(view);
    }
}