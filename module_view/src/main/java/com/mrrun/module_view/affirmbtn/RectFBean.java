package com.mrrun.module_view.affirmbtn;

import android.graphics.Paint;
import android.graphics.RectF;

/**
 * 圆角属性
 */
public class RectFBean {

    private RectF rectF;

    private Paint paint;

    private float circleAngle;

    public RectFBean(float left, float right, float top, float bottom, float circleAngle, Paint paint) {
        // this.rectF = new RectF(left, right, top, bottom);
        rectF = new RectF();
        rectF.left = 0;
        rectF.right = right;
        rectF.top = 0;
        rectF.bottom = bottom;
        this.paint = paint;
        this.circleAngle = circleAngle;
    }
    public void setLeft(float left){
        this.rectF.left = left;
    }

    public void setTop(float top){
        this.rectF.top = top;
    }

    public void setBottom(float bottom){
        this.rectF.bottom = bottom;
    }

    public void setRight(float right){
        this.rectF.right = right;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public void setCircleAngle(float circleAngle) {
        this.circleAngle = circleAngle;
    }

    public RectF getRectF() {
        return rectF;
    }

    public Paint getPaint() {
        return paint;
    }

    public float getCircleAngle() {
        return circleAngle;
    }
}
