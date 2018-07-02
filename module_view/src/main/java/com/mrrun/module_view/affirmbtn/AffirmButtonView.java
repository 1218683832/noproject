package com.mrrun.module_view.affirmbtn;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义确认按钮带动画
 *
 * @author lipin
 * @version 1.0
 * @date 2018/07/02
 */
public class AffirmButtonView extends View{

    private Context mContext;

    public AffirmButtonView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public AffirmButtonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public AffirmButtonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
    }
}
