package com.mrrun.module_view.bethel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.mrrun.module_view.BaseView;
import com.mrrun.module_view.Debug;

/**
 * 贝塞尔 消息
 *
 * @author lipin
 * @date 2018/09/12
 * @version 1.0
 */
public class MessageBubbleView extends BaseView{

    // 固定点和移动点
    private PointF mFixedPoint, mMovedPoint;
    private int mFixedPointRadiusMax = 8, mFixedPointRadiusMin = 3;
    private Paint mPaint;
    private float mFixedPointRadius;

    public MessageBubbleView(Context context) {
        this(context, null);
    }

    public MessageBubbleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MessageBubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override
    protected void init(AttributeSet attrs) {
        initData(attrs);
        initPaint();
    }

    @Override
    protected void initData(AttributeSet attrs) {
        mFixedPointRadiusMax = dp2px(mFixedPointRadiusMax);
        mFixedPointRadiusMin = dp2px(mFixedPointRadiusMin);
    }

    @Override
    protected void initPaint() {
        mPaint = createCommonPaint();
        mPaint.setColor(Color.RED);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Debug.D("onTouchEvent--->event=" + event.getAction());
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                iniFixedPoint(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                updateMovePoint(event.getX(), event.getY());
                break;
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (null == mFixedPoint || null == mMovedPoint) {
            return;
        }
        drawDragCircle(canvas);
        drawFixedCircle(canvas);
        drawBezierPath(canvas);

    }

    /**
     * 画贝塞尔
     * @param canvas
     */
    private void drawBezierPath(Canvas canvas) {
        Path bezierPath = getBezierPath();
        if (null != bezierPath){
            canvas.drawPath(getBezierPath(), mPaint);
        }
    }

    /**
     * 画固定圆
     * @param canvas
     */
    private void drawFixedCircle(Canvas canvas) {
        double d = calculateDistanceTwoPoints(mFixedPoint, mMovedPoint);
        Debug.D("onDraw--->计算两点之间的距离d=" + d);
        mFixedPointRadius = (float) (mFixedPointRadiusMax - d / 15);
        if (mFixedPointRadius >= mFixedPointRadiusMin){
            canvas.drawCircle(mFixedPoint.x, mFixedPoint.y, mFixedPointRadius, mPaint);
        }
    }

    /**
     * 画拖拽圆
     * @param canvas
     */
    private void drawDragCircle(Canvas canvas) {
        // 拖拽圆
        canvas.drawCircle(mMovedPoint.x, mMovedPoint.y, mFixedPointRadiusMax, mPaint);
    }

    /**
     * 获取Bezier曲线路径
     */
    private Path getBezierPath() {
        if (mFixedPointRadius < mFixedPointRadiusMin) {
            return null;
        }
        Path bezierPath = new Path();
        // 计算斜率
        float dx = mMovedPoint.x - mFixedPoint.x;
        float dy = mMovedPoint.y - mFixedPoint.y;
        if (dx == 0) {
            dx = 0.001f;
        }
        float tan = dy / dx;
        // 获取角a度值
        float arcTanA = (float) Math.atan(tan);
        // 依次计算 p0 , p1 , p2 , p3 点的位置
        float P0X = (float) (mFixedPoint.x + mFixedPointRadius * Math.sin(arcTanA));
        float P0Y = (float) (mFixedPoint.y - mFixedPointRadius * Math.cos(arcTanA));

        float P1X = (float) (mMovedPoint.x + mFixedPointRadiusMax * Math.sin(arcTanA));
        float P1Y = (float) (mMovedPoint.y - mFixedPointRadiusMax * Math.cos(arcTanA));

        float P2X = (float) (mMovedPoint.x - mFixedPointRadiusMax * Math.sin(arcTanA));
        float P2Y = (float) (mMovedPoint.y + mFixedPointRadiusMax * Math.cos(arcTanA));

        float P3X = (float) (mFixedPoint.x - mFixedPointRadius * Math.sin(arcTanA));
        float P3Y = (float) (mFixedPoint.y + mFixedPointRadius * Math.cos(arcTanA));

        // 求控制点 两个点的中心位置作为控制点
//        PointF controlPoint = BubbleUtils.getPointByPercent(mDragPoint, mFixationPoint, 0.5f);
        PointF controlPoint = new PointF((mMovedPoint.x + mFixedPoint.x)/2, (mMovedPoint.y + mFixedPoint.y)/2);
        // 整合贝塞尔曲线路径
        bezierPath.moveTo(P0X, P0Y);
        bezierPath.quadTo(controlPoint.x, controlPoint.y, P1X, P1Y);
        bezierPath.lineTo(P2X, P2Y);
        bezierPath.quadTo(controlPoint.x, controlPoint.y, P3X, P3Y);
        bezierPath.close();
        return bezierPath;
    }

    /**
     * 计算两点之间的距离
     * @param p1
     * @param p2
     */
    private double calculateDistanceTwoPoints(PointF p1, PointF p2) {
        return Math.sqrt(Math.pow((p1.x - p2.x), 2) + Math.pow((p1.y - p2.y), 2));
    }

    private void updateMovePoint(float x, float y) {
        if (null == mMovedPoint){
            mMovedPoint = new PointF();
        }
        mMovedPoint.x = x;
        mMovedPoint.y = y;
    }

    private void iniFixedPoint(float x, float y) {
        if (null == mFixedPoint){
            mFixedPoint = new PointF();
        }
        mFixedPoint.x = x;
        mFixedPoint.y = y;
    }
}
