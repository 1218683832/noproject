package com.mrrun.module_view;

import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * 测量工具
 *
 * @author lipin
 * @date 2018/09/30
 * @version 1.0
 */
public class MeasureUtil {

    /**
     * Common measure width int.
     *
     * @param defValue
     *         the def value of width
     * @param measureSpec
     *         the measure spec
     * @return the int
     */
    public static int commonMeasureWidth(int defValue, int measureSpec){
        int mode = View.MeasureSpec.getMode(measureSpec);
        int val = View.MeasureSpec.getSize(measureSpec);
        int result = 0;
        switch (mode){
            case View.MeasureSpec.EXACTLY:{
                result = val;
                break;
            }
            case View.MeasureSpec.AT_MOST:
            case View.MeasureSpec.UNSPECIFIED:
                result = defValue;
                break;
        }
        result = mode == View.MeasureSpec.AT_MOST ? Math.min(result, val) : result;
        return result;
    }

    /**
     * Common measure height int.
     *
     * @param defValue
     *         the def value of width
     * @param measureSpec
     *         the measure spec
     * @return the int
     */
    public static int commonMeasureHeight(int defValue, int measureSpec){
        int mode = View.MeasureSpec.getMode(measureSpec);
        int val = View.MeasureSpec.getSize(measureSpec);
        int result = 0;
        switch (mode){
            case View.MeasureSpec.EXACTLY:{
                result = val;
                break;
            }
            case View.MeasureSpec.AT_MOST:
            case View.MeasureSpec.UNSPECIFIED:
                result = defValue;
                break;
        }
        result = mode == View.MeasureSpec.AT_MOST ? Math.min(result, val) : result;
        return result;
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

    /**
     * 测文字边框
     */
    public static Rect measureTextRect(Paint paint, String text) {
        Rect textRect = new Rect();
        paint.getTextBounds(text, 0, text.length(), textRect);
        return textRect;
    }

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
}
