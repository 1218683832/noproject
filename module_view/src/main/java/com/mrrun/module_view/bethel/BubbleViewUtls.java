package com.mrrun.module_view.bethel;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.View;
import android.view.WindowManager;

public class BubbleViewUtls {

    public static void bindView(Context context, View view){
        BubbleView bubbleView = new BubbleView(context);
        bubbleView.setBindView(view);
    }
}
