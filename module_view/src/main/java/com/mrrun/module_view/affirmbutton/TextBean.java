package com.mrrun.module_view.affirmbutton;

import android.graphics.Paint;
import android.graphics.RectF;

public class TextBean {

    private String contentStr;

    private RectF rectF;

    private Paint paint;

    private float baseline;

    private float startX;

    public TextBean(String contentStr,float left, float right, float top, float bottom, Paint paint) {
        this.contentStr = contentStr;
        rectF = new RectF();
        rectF.left = 0;
        rectF.right = right;
        rectF.top = 0;
        rectF.bottom = bottom;
        this.paint = paint;
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        this.baseline = (rectF.bottom + rectF.top - fontMetrics.bottom - fontMetrics.top) / 2;
        this.startX = rectF.centerX();
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public void setRight(float right){
        this.rectF.right = right;
    }

    public String getContentStr() {
        return contentStr;
    }

    public RectF getRectF() {
        return rectF;
    }

    public Paint getPaint() {
        return paint;
    }

    public float getBaseline() {
        return baseline;
    }

    public float getStartX() {
        return startX = rectF.centerX();
    }
}
