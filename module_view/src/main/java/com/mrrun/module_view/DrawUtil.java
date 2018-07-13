package com.mrrun.module_view;

import android.graphics.Paint;

public class DrawUtil {
    /**
     * 绘制文字时的基线到中轴线Y的距离
     *
     * @param paint
     *         the paint
     * @return the float
     */
    public static float textBaseLine(Paint paint){
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        return dy;
    }

    /**
     * 绘制文字时的基线到中轴线Y的距离
     *
     * @param paint
     *         the paint
     * @return the float
     */
    public static float textBaseLine2(Paint paint){
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float dy = (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent;
        return dy;
    }

    /**
     * Measure text.
     * 测量文字宽度
     *
     * @param paint
     *         the paint
     * @param text
     *         the text
     * @return the float text width
     */
    public static float measureText(Paint paint, String text){
        return paint.measureText(text);
    }
}
