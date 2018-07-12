package com.mrrun.module_view;

import android.view.View;

public class MeasureUtil {

    /**
     * Common measure width int.
     * carry paddingleft and paddingright
     *
     * @param defValue
     *         the def value of width
     * @param measureSpec
     *         the measure spec
     * @param view
     *         the view
     * @return the int
     */
    public static int commonMeasureWidth(int defValue, int measureSpec, View view){
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
        result = result + view.getPaddingLeft() + view.getPaddingRight();
        return result;
    }

    /**
     * Common measure height int.
     * carry paddingtop and paddingbottom
     *
     * @param defValue
     *         the def value of width
     * @param measureSpec
     *         the measure spec
     * @param view
     *         the view
     * @return the int
     */
    public static int commonMeasureHeight(int defValue, int measureSpec, View view){
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
        result = result + view.getPaddingTop() + view.getPaddingBottom();
        return result;
    }
}
