package com.mrrun.module_view.inputview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.mrrun.module_view.Debug;

/**
 * 仿微信自定义支付密码输入框.
 * 需求1：可自定义输入框宽度但不得小于某个阀值；
 * 需求2：最多6个输入框；
 * 需求3：输入框内是正方形小框；
 * 需求4：不需要有明显焦点；
 * 需求5：框内只显示黑点代替输入的数字
 *
 * @author lipin
 * @version 1.0
 * @date 2018/07/06
 */
public class PayPsdInputView extends AppCompatEditText {

    /**
     * 最大输入数字数量
     */
    private static int MAX_INPUT_COUNT = 6;

    /**
     * 定义输入框宽度最小阀值
     */
    private int minWidth;
    private Context mContext;
    private DisplayMetrics dm;
    /**
     * View的实际宽度
     */
    private int mViewWidth;
    /**
     * View的实际高度
     */
    private int mViewHeight;
    private RectF rectF = new RectF();
    private Paint recFPaint;
    private Paint lineRecFPaint;
    private Paint dotPaint;
    /**
     * 小框的线条宽度
     */
    private int mLineWidth = 2;
    /**
     * 框已画好只需要画小圆点了
     */
    private boolean onlyDrawDot = false;
    /**
     * 小圆点半径
     */
    private float mDotRadius = 10;
    /**
     * 当前输入密码位数
     */
    private int textLength = 0;
    /**
     * 输入的密码
     */
    private String mPassword = null;

    /**
     * 密码输入监听器
     */
    public interface OnPasswordInputListener {
        void finished(String psd);
    }

    public void setOnPasswordInputListener(OnPasswordInputListener onPasswordInputListener) {
        this.mOnPasswordInputListener = onPasswordInputListener;
    }

    private OnPasswordInputListener mOnPasswordInputListener;

    public PayPsdInputView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public PayPsdInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        // 设置文本类型为数字
        setInputType(InputType.TYPE_CLASS_NUMBER);
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_INPUT_COUNT)});
        maskFun();
        initData();
        initPaint();
    }

    /**
     * 屏蔽功能
     */
    private void maskFun() {
        // 屏蔽选择、复制等行为,而对粘贴的行为，无法起到作用。
        setLongClickable(false);
        // 屏蔽粘贴行为.setCustomInsertionActionModeCallback此方法在小于Build.VERSION_CODES.M的版本中，不兼容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setCustomInsertionActionModeCallback(new ActionModeCallbackInterceptor());
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setCustomSelectionActionModeCallback(new ActionModeCallbackInterceptor());
        }
        // 屏蔽
        setTextIsSelectable(false);
        setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        recFPaint = new Paint();
        recFPaint.setStyle(Paint.Style.FILL);
        recFPaint.setColor(Color.parseColor("#ffffff"));
        recFPaint.setAntiAlias(true);

        lineRecFPaint = new Paint();
        lineRecFPaint.setStyle(Paint.Style.STROKE);
        lineRecFPaint.setStrokeWidth(mLineWidth);
        lineRecFPaint.setColor(Color.parseColor("#e0e0eb"));
        lineRecFPaint.setAntiAlias(true);


        dotPaint = new Paint();
        dotPaint.setStyle(Paint.Style.FILL);
        dotPaint.setColor(Color.parseColor("#004d00"));
        dotPaint.setAntiAlias(true);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        this.dm = mContext.getResources().getDisplayMetrics();
        // 输入框最小宽度不得小于整个屏幕的三分之二
        this.minWidth = getScreenWidth() / 3 * 2;
        this.mLineWidth = dp2px(0.8f);
        this.mDotRadius = dp2px(7);
        Debug.D(String.format("输入框最小宽度不得小于：%d", minWidth));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        if (width < minWidth){
            width = minWidth;
        }
        int height =  width / MAX_INPUT_COUNT;
        this.mViewWidth = width;
        this.mViewHeight = height;
        setMeasuredDimension(width, height);
        Debug.D(String.format("测量的View宽度和高度：%d，%d", width, height));
    }

    // 也是为了屏蔽EditText的复制粘贴剪切等行为
    @Override
    public boolean onTextContextMenuItem(int id) {
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mViewWidth = w;
        this.mViewWidth = h;
        this.rectF.set(0,0,w,h);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        mPassword = text.toString();
        textLength = mPassword.length();
        Debug.D(String.format("当前密码输入:%s", mPassword));
        if (textLength == MAX_INPUT_COUNT) {
            if (mOnPasswordInputListener != null) {
                if (TextUtils.isEmpty(text)) {
                    mOnPasswordInputListener.finished(mPassword);
                    Debug.D(String.format("密码输入完成:%s", mPassword));
                }
            }
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Debug.D("onDraw");
        // 先画整个长框
        canvas.drawRect(rectF,recFPaint);
        // 再画最大个数的小框
        float left = 0;
        float right = 0;
        float top = 0 + mLineWidth;
        float bottom = mViewHeight - mLineWidth;
        float w = ((mViewWidth - mLineWidth) / MAX_INPUT_COUNT);
        for (int i = 1; i <= MAX_INPUT_COUNT; i++) {
            if (i == 1) {
                left = right + mLineWidth;
            } else {
                left = right;
            }
            right = w * i + mLineWidth / 2;
            canvas.drawRect(left, top, right, bottom, lineRecFPaint);
        }
        // 根据输入画最多最大个数的小圆点
        float startX = mViewWidth / MAX_INPUT_COUNT / 2;
        float startY = mViewHeight / 2;
        for (int i = 0; i < textLength; i++) {
            canvas.drawCircle(startX + i * 2 * startX,
                    startY,
                    mDotRadius,
                    dotPaint);
        }
    }

    private int dp2px(float dp) {
        return (int) (dm.density * dp + 0.5f);
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    private int getScreenWidth() {
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    private int getScreenHeight() {
        return dm.widthPixels;
    }

    private class ActionModeCallbackInterceptor implements ActionMode.Callback {

        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        public void onDestroyActionMode(ActionMode mode) {
        }
    }
}
