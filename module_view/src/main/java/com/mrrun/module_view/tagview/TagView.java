package com.mrrun.module_view.tagview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;

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
    /**
     * 标签正常状态时的Drawable(未选中)
     */
    private GradientDrawable mNormalDrawable;
    /**
     * 标签选中状态时的Drawable(选中)
     */
    private GradientDrawable mSelectedDrawable;
    private boolean isSelected = false;

    private String mTagContent;

    /**
     * Tag点击监听器
     */
    public interface OnTagClickListener{
        void onTagClick(String text);
        void onTagLongClick(String text);
    }

    private OnTagClickListener mOnTagClickListener;

    public TagView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public TagView(Context context, String content) {
        super(context);
        mContext = context;
        this.mTagContent = content;
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

    private void init() {
        mDm = mContext.getResources().getDisplayMetrics();
        mDensity = mDm.density;
        Debug.D(String.format("Density = %f", mDensity));
        initDrawable();
        initText();
        setTagViewStyle(isSelected);
        setClickable(true);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Debug.D("TagView OnClick");
                isSelected = !isSelected;
                setTagViewStyle(isSelected);

                if (null != mOnTagClickListener) {
                    mOnTagClickListener.onTagClick(mTagContent);
                }
            }
        });
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Debug.D("TagView onLongClick");
                if (null != mOnTagClickListener) {
                    mOnTagClickListener.onTagLongClick(mTagContent);
                }
                return true;
            }
        });
    }

    /**
     * 根据点击是否选中设置TagView的显示样式
     *
     * @param isSelected
     */
    private void setTagViewStyle(boolean isSelected) {
        if (isSelected) {
            setBackground(mSelectedDrawable);
            setTextColor(Color.parseColor("#00b300"));
        } else {
            setBackground(mNormalDrawable);
            setTextColor(Color.parseColor("#000000"));
        }
    }

    private void initDrawable() {
        mNormalDrawable = new GradientDrawable();
        // 填充
        mNormalDrawable.setColor(Color.parseColor("#f2f2f2"));
        // 圆角
        mNormalDrawable.setCornerRadius(dp2px(20));
        // 描边
        mNormalDrawable.setStroke(dp2px(1), Color.parseColor("#d1d1e0"));

        mSelectedDrawable = new GradientDrawable();
        mSelectedDrawable.setColor(Color.parseColor("#ffffff"));
        mSelectedDrawable.setCornerRadius(dp2px(20));
        mSelectedDrawable.setStroke(dp2px(1), Color.parseColor("#00b300"));
    }

    private void initPaint() {
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /**
     * View背景样式,代码编写GradientDrawable
     * 给控件加上圆角或边颜色的效果
     */
    private void initViewBg() {
        GradientDrawable gd = new GradientDrawable();
//        gd.setColor(Color.parseColor("#00b300"));
        gd.setCornerRadius(dp2px(20));
        gd.setStroke(dp2px(6), Color.parseColor("#00b300"));
        setBackground(gd);
    }

    /**
     * 文字的格式
     */
    private void initText() {
        // 文字要居中
        setGravity(Gravity.CENTER);
        // 文字上下左右间隔
        setPadding(dp2px(8), dp2px(6), dp2px(6), dp2px(8));
        setTextColor(Color.parseColor("#000000"));
        if (!TextUtils.isEmpty(mTagContent))
            setText(mTagContent);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public boolean tagSelected() {
        return isSelected;
    }

    public OnTagClickListener getOnTagClickListener() {
        return mOnTagClickListener;
    }

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        this.mOnTagClickListener = onTagClickListener;
    }

    private int dp2px(float dp) {
        return (int) (mDensity * dp + 0.5f);
    }
}
