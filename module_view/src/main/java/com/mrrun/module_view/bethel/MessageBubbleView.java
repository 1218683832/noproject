package com.mrrun.module_view.bethel;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.mrrun.module_view.BaseView;
import com.mrrun.module_view.Debug;
import com.mrrun.module_view.R;

/**
 * 贝塞尔 消息
 *
 * @author lipin
 * @date 2018/09/14
 * @version 1.0
 */
public class MessageBubbleView extends BaseView{

    // 固定点和移动点
    private PointF mFixedPoint, mDragPoint;
    private int mFixedPointRadiusMax = 8, mFixedPointRadiusMin = 3;
    private Paint mPaint;
    private float mFixedPointRadius;
    private Bitmap mDragBitmap;
    // 拖拽爆炸控件View消失和重置的监听
    private OnMessageBubbleViewDragListener dragListener;

    public void setOnMessageBubbleViewDragListener(OnMessageBubbleViewDragListener dragListener) {
        this.dragListener = dragListener;
    }

    public void initPoint(int x, int y) {
        mFixedPoint = new PointF(x, y);
        mDragPoint = new PointF(x, y);
    }

    public interface OnMessageBubbleViewDragListener {

        void onViewDragDisappear(PointF pointF);

        void onViewDragRestore();
    }

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

    public static void bindView(View view, OnMessageBubbleTouchListener.OnViewDragDisappearListener disappearListener){
        // 不能在MessageBubbleView里处理Touch事件，要继承View.OnTouchListener重写onTouch方法处理MessageBubbleView。
        view.setOnTouchListener(new OnMessageBubbleTouchListener(view, view.getContext(), disappearListener));
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
    protected void onDraw(Canvas canvas) {
        if (null == mFixedPoint || null == mDragPoint) {
            return;
        }
        drawFixedCircle(canvas);
        drawBezierPath(canvas);
        drawDragCircle(canvas);
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
        double d = BubbleUtils.getDistanceBetween2Points(mFixedPoint, mDragPoint);
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
        canvas.drawCircle(mDragPoint.x, mDragPoint.y, mFixedPointRadiusMax, mPaint);
        // 获取那个没有动的 View，然后去画
        // 画图片  位置也是手指移动的位置，中心位置才是手指拖动的位置
        if (null != mDragBitmap){
            // 搞一个渐变动画
            canvas.drawBitmap(mDragBitmap, mDragPoint.x - mDragBitmap.getWidth()/2 , mDragPoint.y - mDragBitmap.getHeight()/2  , null);
        }
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
        float dx = mDragPoint.x - mFixedPoint.x;
        float dy = mDragPoint.y - mFixedPoint.y;
        if (dx == 0) {
            dx = 0.001f;
        }
        float tan = dy / dx;
        // 获取角a度值
        float arcTanA = (float) Math.atan(tan);
        // 依次计算 p0 , p1 , p2 , p3 点的位置
        float P0X = (float) (mFixedPoint.x + mFixedPointRadius * Math.sin(arcTanA));
        float P0Y = (float) (mFixedPoint.y - mFixedPointRadius * Math.cos(arcTanA));

        float P1X = (float) (mDragPoint.x + mFixedPointRadiusMax * Math.sin(arcTanA));
        float P1Y = (float) (mDragPoint.y - mFixedPointRadiusMax * Math.cos(arcTanA));

        float P2X = (float) (mDragPoint.x - mFixedPointRadiusMax * Math.sin(arcTanA));
        float P2Y = (float) (mDragPoint.y + mFixedPointRadiusMax * Math.cos(arcTanA));

        float P3X = (float) (mFixedPoint.x - mFixedPointRadius * Math.sin(arcTanA));
        float P3Y = (float) (mFixedPoint.y + mFixedPointRadius * Math.cos(arcTanA));

        // 求控制点 两个点的中心位置作为控制点
        PointF controlPoint = BubbleUtils.getPointByPercent(mDragPoint, mFixedPoint, 0.5f);
        // 整合贝塞尔曲线路径
        bezierPath.moveTo(P0X, P0Y);
        bezierPath.quadTo(controlPoint.x, controlPoint.y, P1X, P1Y);
        bezierPath.lineTo(P2X, P2Y);
        bezierPath.quadTo(controlPoint.x, controlPoint.y, P3X, P3Y);
        bezierPath.close();
        return bezierPath;
    }

    public void setDragBitmap(Bitmap dragBitmap) {
        mDragBitmap = dragBitmap;
    }

    public void updateDragPoint(float x, float y) {
        mDragPoint.x = x;
        mDragPoint.y = y;
        invalidate();
    }

    /**
     * 处理松手逻辑:
     * 松手时,如果距离超过一定值,则显示爆炸效果;
     * 如果没有超过一定值,则显示回弹效果
     */
    public void handleActionUP() {
        if (mFixedPointRadius < mFixedPointRadiusMin){// 显示消失效果
            if (dragListener != null) {
                dragListener.onViewDragDisappear(mDragPoint);
            }
        } else {// 显示回弹效果
            ValueAnimator animator = ObjectAnimator.ofFloat(1f);
            animator.setDuration(300);

            final PointF startPoint = new PointF(mDragPoint.x, mDragPoint.y);
            final PointF endPoint = new PointF(mFixedPoint.x, mFixedPoint.y);

            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float percent = (float) animation.getAnimatedValue();
                    // PointF pointF = BubbleUtils.getPointByPercent(mDragPoint,mFixationPoint,percent);
                    // 这个起始点和结束点,不能这样传值(这样传值的话,起点和终点一直在发生变化),应该转的是固定点和刚开始放手时的坐标的点
                    PointF pointF = BubbleUtils.getPointByPercent(startPoint, endPoint, percent);
                    updateDragPoint(pointF.x, pointF.y);
                }
            });
            animator.setInterpolator(new OvershootInterpolator(4f));
            animator.start();
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    // 当动画结束的时候,重新让View可拖动
                    if (dragListener != null) {
                        dragListener.onViewDragRestore();
                    }
                }
            });
        }
    }
}
