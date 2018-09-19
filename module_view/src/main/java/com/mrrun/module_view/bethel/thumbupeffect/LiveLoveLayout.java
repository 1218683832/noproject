package com.mrrun.module_view.bethel.thumbupeffect;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mrrun.module_view.Debug;
import com.mrrun.module_view.R;

import java.util.Random;

/**
 * 仿花束直播点赞效果Layout
 *
 * @author lipin
 * @date 2018/09/19
 * @version 1.0
 */
public class LiveLoveLayout extends RelativeLayout {

    private Context mContext;
    private Drawable[] mLoveDrawables;
    private int mLoveDrawableHeight, mLoveDrawableWidth;

    public LiveLoveLayout(Context context) {
        this(context, null);
    }

    public LiveLoveLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LiveLoveLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        initDrawable();
    }

    private void initDrawable() {
        mLoveDrawables = new Drawable[4];
        mLoveDrawables[0] = getResources().getDrawable(R.drawable.love1);
        mLoveDrawables[1] = getResources().getDrawable(R.drawable.love2);
        mLoveDrawables[2] = getResources().getDrawable(R.drawable.love3);
        mLoveDrawables[3] = getResources().getDrawable(R.drawable.love4);

        mLoveDrawableWidth = mLoveDrawables[0].getIntrinsicWidth();
        mLoveDrawableHeight = mLoveDrawables[0].getIntrinsicHeight();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Debug.D("onTouchEvent--->event=" + event.getAction());
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                Debug.D("onTouchEvent--->MotionEvent.ACTION_UP:添加一个点赞图");
                addLove(event.getX() - mLoveDrawableWidth / 2, event.getY() - mLoveDrawableHeight / 2);
                break;
        }
        return true;
    }

    private void addLove(float x, float y) {
        Random random = new Random();
        int num = random.nextInt(4);// 产生的随机数为0-4的整数,不包括4
        final ImageView loveImgView = new ImageView(mContext);
        loveImgView.setImageDrawable(mLoveDrawables[num]);
        loveImgView.setX(x);
        loveImgView.setY(y);
        this.addView(loveImgView);
    }
}
