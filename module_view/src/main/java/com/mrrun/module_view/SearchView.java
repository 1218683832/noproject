package com.mrrun.module_view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

/**
 * @author lipin
 * @version 1.0
 * @date 2018/04/16
 *
 * 搜索框
 * 1、实时查询筛选结果接口；
 * @see Callback#onQuery(String)
 * 2、无输入显示历史搜索接口；
 * @see Callback#onHistory()
 * 3、搜索框右侧清空文本内容；
 * @see SearchView#clearText()
 * 4、监听删除键接口
 * @see Callback#onDel()
 */
public class SearchView extends AppCompatEditText
        implements View.OnFocusChangeListener, TextWatcher, View.OnKeyListener {

    private static final String TAG = "SearchView";
    Context mContext;
    // 右侧清除图的引用
    Drawable mClearDrawable;
    boolean isClearShow = true;
    // 是否有焦点
    boolean mHasFocus = false;

    public interface Callback {
        /**
         * 查数据接口
         * @param text
         */
        void onQuery(String text);
        /**
         * 搜索历史接口
         */
        void onHistory();
        /**
         * 删除回退接口
         */
        void onDel();
    }

    Callback mCallback;

    private SearchView(Context context) {
        super(context);
        this.mContext = context;
        init(null);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(attrs);
    }

    /**
     * UI、数据、属性等的初始化
     * @param attrs
     */
    private void init(AttributeSet attrs) {
        initAttrs(attrs);
        initView();
    }

    /**
     * UI初始化
     */
    private void initView() {
        // 默认设置隐藏图标
        setRightIconVisible(false);
        // 设置焦点改变的监听
        setOnFocusChangeListener(this);
        // 设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
        // 默认单行
        setSingleLine(true);
        // 默认输入文本
        int inputType = this.getInputType() == InputType.TYPE_CLASS_TEXT ? InputType.TYPE_CLASS_TEXT : this.getInputType();
        // Log.d(TAG, "inputType = " + this.getInputType());
        setInputType(inputType);
        // 默认搜索
        int imeOptions = this.getImeOptions() == EditorInfo.IME_NULL ? EditorInfo.IME_ACTION_SEARCH : this.getImeOptions();
        // Log.d(TAG, "imeOptions = " + this.getImeOptions());
        setImeOptions(imeOptions);
        setOnKeyListener(this);
    }

    /**
     * 属性初始化
     * @param attrs
     */
    private void initAttrs(AttributeSet attrs) {
    }

    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置在 EditText的宽度 - 图标到控件右边的间距 - 图标的宽度和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向没有考虑
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (isClearEditEvent(event)) {
                    clearText();
                } else {
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 是否点击清除编辑框
     */
    private boolean isClearEditEvent(MotionEvent event) {
        boolean clearable = false;
        if (mClearDrawable != null) {
            clearable = event.getX() > (getWidth() - getPaddingRight() - mClearDrawable.getIntrinsicWidth())
                    && (event.getX() < ((getWidth() - getPaddingRight())));
        }
        return clearable;
    }

    /**
     * 清除文本内容
     */
    public void clearText() {
        Log.d(TAG, "清除文本内容");
        this.setText("");
    }

    /**
     * 设置右侧图是否显示
     * @param visible
     */
    private void setRightIconVisible(boolean visible) {
        if (isClearShow == visible) {
            return;
        } else {
            isClearShow = visible;
        }
        if (mClearDrawable == null) {
            mClearDrawable = this.getCompoundDrawables()[2];
        }
        Drawable right = isClearShow ? mClearDrawable : null;
        this.setCompoundDrawables(this.getCompoundDrawables()[0], this.getCompoundDrawables()[1], right, this.getCompoundDrawables()[3]);
    }

    @Override
    public boolean isFocused() {
        mHasFocus = super.isFocused();
        return mHasFocus;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        mHasFocus = hasFocus;
        if (mHasFocus) {
            Log.d(TAG, "has focus");
        } else {
            Log.d(TAG, "not focus");
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mHasFocus) {
            setRightIconVisible(s.length() > 0);
        }
    }

    /**
     *  查询数据实时筛选结果
     */
    @Override
    public void afterTextChanged(Editable s) {
        Log.d(TAG, "根据输入查询数据实时筛选结果");
        if (mCallback == null) {
            Log.d(TAG, "Callback can't be null!");
        } else {
            if (s.toString().trim().length() == 0) {// 若搜索框为空,则显示所有的搜索历史
                // 调用回调显示所有搜索历史
                Log.d(TAG, "搜索框为空,显示所有的搜索历史");
                mCallback.onHistory();
            } else {// 调用回调查数据
                Log.d(TAG, "搜索框不为空,显示搜索结果");
                String queryTerm = s.toString();
                Log.d(TAG, "查询词:" + queryTerm);
                mCallback.onQuery(queryTerm);
            }
        }
    }

    /**
     * 监听软键盘搜索键
     * @param v
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    Log.d(TAG, "监听软键盘搜索键隐藏键盘");
                    forcedHideSoftInput();
                }
                break;
            case KeyEvent.KEYCODE_DEL:// 删除键
                if (event.getAction() == KeyEvent.ACTION_DOWN && mCallback != null) {
                    Log.d(TAG, "删除键");
                    mCallback.onDel();
                }
                break;
            default:
                break;
        }
        return false;
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    /**
     * 强制隐藏键盘
     */
    private void forcedHideSoftInput() {
        Log.d(TAG, "隐藏键盘");
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getWindowToken(), 0);
    }
}
