package com.mrrun.module_view.bethel;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.View;
import android.view.WindowManager;

public class BuWindowManager {

    private static BuWindowManager instance;
    private static WindowManager winManager;

    private BuWindowManager(){
    }

    public static synchronized BuWindowManager getInstance(Context context){
        if (null == instance){
            instance = new BuWindowManager();
            winManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return instance;
    }

    public void addBubbleView(View bubbleView) {
        // 布局参数
        WindowManager.LayoutParams params = new WindowManager.LayoutParams() ;
        // 背景要透明
        params.format = PixelFormat.TRANSPARENT ;
        // 在WindowManager上面要搞一个View，而这个View就是我们写好的 贝塞尔的View，然后把这个贝塞尔的View 添加到WindowManager上面来
        winManager.addView(bubbleView, params);
    }

    public void removeBubbleView(View bubbleView){
        winManager.removeViewImmediate(bubbleView);
    }
}