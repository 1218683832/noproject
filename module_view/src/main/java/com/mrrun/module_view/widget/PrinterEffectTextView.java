package com.mrrun.module_view.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * TextView实现打印机效果。
 * 思路：
 * 继承自TextView充分利用TextView自带的属性；
 * 重写onDraw()方法，用drawText()，用属性动画一个个绘制文字;
 * 对外暴露的方法,设置字符的方法少不了，接下来开启动画和停止动画，然后就是动画结束的回调。
 * 如何使用：
 * printerTextView
 *   .setTextString("自定义view实现字符串逐字显示")
 *   .startPrinterAnimation()
 *   .setTextAnimationListener(new PrinterTextView.TextAnimationListener() {
 *      @Override
 *      public void onAnimationFinish() {
 *      }
 *  });
 *  问题：
 *  1、如果多行的话未显示的地方会是先空白区域占有；
 *
 * @author lipin
 * @date 2018/09/30
 * @version 1.0
 */
public class PrinterEffectTextView extends AppCompatTextView {

    public PrinterEffectTextView(Context context) {
        super(context);
    }

    public PrinterEffectTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PrinterEffectTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
