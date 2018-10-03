package com.mrrun.module_view.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;

import com.mrrun.module_view.Debug;
import com.mrrun.module_view.MeasureUtil;

/**
 * TextView实现打印机效果。
 * 思路：
 * 继承自TextView充分利用TextView自带的属性；
 * 重写onDraw()方法，用drawText()，用属性动画一个个绘制文字;
 * 对外暴露的方法,设置字符的方法少不了，接下来开启动画和停止动画，然后就是动画结束的回调。
 * 如何使用：
 * printerTextView
 * .setTextString("自定义view实现字符串逐字显示")
 * .startPrinterAnimation()
 * .setTextAnimationListener(new PrinterTextView.TextAnimationListener() {
 *
 * @author lipin
 * @version 1.0
 * @Override public void onAnimationFinish() {
 * }
 * });
 * 问题：
 * 1、如果多行的话未显示的地方会是先空白区域占有；
 * 可优化的地方：
 * 1、可优化的地方，根据文字长度设置动画时长；
 * 2、控件消失取消动画；
 *
 * @date 2018/09/30
 */
public class PrinterEffectTextView extends AppCompatTextView {

    // 文字内容
    String mText = null;
    // 控件宽高
    private float mViewWidth, mViewHeight;
    // 文字行数
    private int mRows;
    // 每行宽度
    private float mRowWidth;
    private TextPaint mTextPaint;
    private int mTextLength;

    public PrinterEffectTextView(Context context) {
        this(context, null);
    }

    public PrinterEffectTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PrinterEffectTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        initData(attrs);
    }

    private void initData(AttributeSet attrs) {
    }

    private Rect mTextRect = null;

    public PrinterEffectTextView text(String text) {
        mText = text;
        setText(text);
        mTextRect = MeasureUtil.measureTextRect(getPaint(), mText);
        mTextLength = mText.length();
        return this;
    }

    public void printText(){
        ValueAnimator animator = ValueAnimator.ofInt(0, mTextLength);
        // 可优化的地方，根据文字长度设置动画时长
        animator.setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mTextLength = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        Debug.D("mViewWidth : " + mViewWidth);
        if (null != mTextRect){
            Debug.D("mTextRect.width : " + mTextRect.width());
            // 这里计算行数时，如果不能整除就要进位。
            mRowWidth = mViewWidth - getCompoundPaddingLeft() - getCompoundPaddingRight();
            Double d = Math.ceil(mTextRect.width() / (mRowWidth));
            mRows = d.intValue();
            Debug.D("text rows : " + mRows);
            Debug.D("d : " + d);
            Debug.D("onSizeChanged--->mRowWidth : " + mRowWidth);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas);
        if (null == mText || mText.length() <= 0) {
            return;
        }
        textDraw(canvas);
    }

    /**
     * 画文本内容
     * 遇到的问题点：
     * 如何画多行文字？
     * 以及多行文字的定位？
     *
     * @param canvas
     */
    private void textDraw(Canvas canvas) {
        mTextPaint = getPaint();
        float x = getPaddingLeft();
        float dy = MeasureUtil.textBaseLine(getPaint());
        int start = 0;
        int end = 0;
        for (int i = 1; i <= mRows; i++) {
            Debug.D("当前text row : " + i);
            float t = 0;
            while (end < mTextLength){
                end ++;
                t = MeasureUtil.measureText(mTextPaint, mText.substring(start, end));
                if (t > mRowWidth){
                    end --;
                    break;
                }
            }
            Debug.D("当前text start : " + start);
            Debug.D("当前text end : " + end);
//            方式一:计算每行的baseline
//            canvas.translate(0, mViewHeight / rows);
//            canvas.drawText(mText, 0, mText.length(), x, dy * i + mViewHeight / (2 * rows) * i
//                    , getPaint());

//            方式二:平移画布坐标系

            canvas.save();
            canvas.translate(0, mViewHeight / (2 * mRows) * i);
            canvas.drawText(mText, start, end, x, dy * i, mTextPaint);
            canvas.restore();
            start = end;
        }
    }
}
