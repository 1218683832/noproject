package com.mrrun.module_view.tagview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;

import com.mrrun.module_view.Debug;

/**
 * 自定义流式标签
 *
 * @author lipin
 * @version 1.0
 * @date 2018/07/03
 */
public class TagView extends AppCompatTextView {

    private Context mContext;
    private DisplayMetrics mDm;
    /**
     * 屏幕密度
     */
    private float mDensity;

    public TagView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public TagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public TagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    int shapeColor = 0xff66a3;

    ShapeDrawable mDrawable;

    private void init() {
        mDm = mContext.getResources().getDisplayMetrics();
        mDensity = mDm.density;
        Debug.D(String.format("Density = %f", mDensity));
        initText();
        initViewBg();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mDrawable.setBounds(0, 0, w, h);
    }

    /**
     * View背景样式,代码绘制图形ShapeDrawable
     */
    private void initViewBg() {
        float[] outerRadii = {dp2px(20), dp2px(20), dp2px(20), dp2px(20), dp2px(20), dp2px(20), dp2px(20), dp2px(20)};//外矩形 左上、右上、右下、左下 圆角半径
        // 设置图形，如果该构造方法为空，则默认显示的是矩形
        mDrawable = new ShapeDrawable(new RoundRectShape(outerRadii, null, null));
        // 修改默认颜色（默认色为黑色）
        mDrawable.getPaint().setColor(Color.parseColor("#ff66a3"));
        mDrawable.getPaint().setAntiAlias(true);
        mDrawable.getPaint().setStyle(Paint.Style.STROKE);//描边
        setBackground(mDrawable);
    }

    /**
     * 文字的格式
     */
    private void initText() {
        // 文字要居中
        setGravity(Gravity.CENTER);
        // 文字上下左右间隔
        setPadding(dp2px(8), dp2px(6), dp2px(6), dp2px(8));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private int dp2px(float dp) {
        return (int) (mDensity * dp + 0.5f);
    }
}
