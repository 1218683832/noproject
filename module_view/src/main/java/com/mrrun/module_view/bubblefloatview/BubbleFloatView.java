package com.mrrun.module_view.bubblefloatview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 气泡浮动View及动画
 *
 * @author lipin
 * @version 1.0
 * @date 2018/07/02
 */
public class BubbleFloatView extends View{

    public BubbleFloatView(Context context) {
        super(context);
    }

    public BubbleFloatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BubbleFloatView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        Point point = new Point(0, 0);
        canvas.drawCircle(point.x, point.y, 100f, paint);
    }
}
