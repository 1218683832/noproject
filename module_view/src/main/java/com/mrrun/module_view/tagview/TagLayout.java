package com.mrrun.module_view.tagview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.mrrun.module_view.Debug;

/**
 * 自定义流式标签布局
 *
 * @author lipin
 * @version 1.0
 * @date 2018/07/03
 */
public class TagLayout extends ViewGroup {
    private Context mContext;
    /**
     * ViewGroup宽度
     */
    private int mWidth;
    /**
     * ViewGroup高度
     */
    private int mHeight;
    /**
     * Tag之间的垂直间距
     */
    private int mVerticalInterval;
    /**
     * Tag之间的水平间距
     */
    private int mHorizontalInterval;

    public TagLayout(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        // 如果想要自己绘制内容，则必须设置这个标志位为false，否则onDraw()方法不会调用
        // setWillNotDraw(false);
    }

    // 根据各个子View的大小，确定ViewGroup大小（重写onMeasure()方法）
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMyWidth(0, widthMeasureSpec, heightMeasureSpec);
        mHeight = getMyHeight(0, widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
        Debug.D(String.format("ViewGroup宽高：%d,%d", mWidth, mHeight));
    }

    private int getMyHeight(int size, int widthMeasureSpec, int heightMeasureSpec) {
        int result = size;
        int lineHeight = 0;
        int childLift = 0;
        if (getChildCount() == 0) {
            result = 0;
        } else {
            int specMode = MeasureSpec.getMode(heightMeasureSpec);
            int specSize = MeasureSpec.getSize(heightMeasureSpec);
            // getChildCount得到子view的数目,遍历循环出每个子View
            for (int i = 0; i < getChildCount(); i++) {
                // 拿到index上的子view
                View childView = getChildAt(i);
                // 测量每一个child的宽和高
                measureChild(childView, widthMeasureSpec, heightMeasureSpec);
                // 当前子控件实际占据的宽度
                int childWidth = childView.getMeasuredWidth();
                // 当前子控件实际占据的高度
                int childHeight = childView.getMeasuredHeight();
                Debug.D(String.format("onMeasure childView宽高：%d,%d", childWidth, childHeight));
                if (childLift + childWidth > mWidth) {// 一行排不下了
                    childLift = childWidth;
                    lineHeight = lineHeight + childHeight;
                } else {// 不用加这个子View的高
                    childLift = childLift + childWidth;
                    lineHeight = Math.max(lineHeight, childHeight);
                }
            }
            result = lineHeight;
        }
        return result;
    }

    private int getMyWidth(int size, int widthMeasureSpec, int heightMeasureSpec) {
        int result = size;
        if (getChildCount() == 0) {// 如果没有子View,当前ViewGroup没有存在的意义，不用占用空间
            result = 0;
        } else {
            int specMode = MeasureSpec.getMode(widthMeasureSpec);
            int specSize = MeasureSpec.getSize(widthMeasureSpec);
            result = specSize;
        }
        return result;
    }

    // 在ViewGroup中进行子View的摆放（重写onLayout（）方法）
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Debug.D(String.format("ViewGroup l,t,r,b：%d,%d,%d,%d", l, t, r, b));
        if (getChildCount() == 0) {
            return;
        }
        int childLeft = 0;
        int childTop = 0;
        // 将子View逐个摆放
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            Debug.D(String.format("onLayout childView宽高：%d,%d", childWidth, childHeight));
            if (childLeft + childWidth > mWidth) {// 一行排不下了
                childLeft = 0;
                childTop = childTop + childHeight;
            }
            Debug.D(String.format("onLayout childTop：%d", childTop));
            childView.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
            childLeft = childLeft + childWidth;
        }
    }

    private TagView.OnTagClickListener onTagClickListener;

    public void setOnTagClickListener(TagView.OnTagClickListener onTagClickListener) {
        this.onTagClickListener = onTagClickListener;
    }

    /**
     * 添加Tag
     *
     * @param content tag内容
     */
    public void addTag(String content) {
        TagView view = new TagView(mContext,content);
        view.setOnTagClickListener(onTagClickListener);
        this.addView(view);
    }

    /**
     *  移除所有Tags
     */
    public void removeAllTags(){
        this.removeAllViews();
    }
}
