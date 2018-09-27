package com.mrrun.module_view.parallax.animation;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatViewInflater;
import android.support.v7.widget.VectorEnabledTintResources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.mrrun.module_view.Debug;
import com.mrrun.module_view.R;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

public class ParallaxFragment extends Fragment implements LayoutInflaterFactory {

    public static final String LAYOUT_ID_KEY = "LAYOUT_ID_KEY";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 获取布局Id
        int layoutId = getArguments().getInt(LAYOUT_ID_KEY);
        // 拦截View的创建,自己解析属性
        // View创建的时候我们去解析，这里传inflater是有问题的，单例设计模式代表所有View的创建都是该Fragment去创建的
        // 克隆一个inflater出来
        inflater = inflater.cloneInContext(getActivity()) ;
        LayoutInflaterCompat.setFactory(inflater, this);
        return inflater.inflate(layoutId , container , false);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        // 所有的View都会在这里创建
        // 拦截到View的创建 获取View之后要去解析
        Debug.D("1.创建View");
        // 1.创建View
        // If the Factory didn't handle it, let our createView() method try
        View view = createView(parent, name, context, attrs);
        // 2.一个activity/fragment的布局肯定对应多个这样的View
        Debug.D("2.一个activity/fragment的布局肯定对应多个这样的View");
        if (view != null){
            // 解析所有的我们关注的属性
            analysisAttrs(view , context , attrs) ;
        }
        return view;
    }

    private CompatViewInflater mCompatViewInflater;
    // 存放所有的需要位移的View
    private List<View> mParallaxViews = new ArrayList<>() ;
    // 存放视差动画的属性
    private int[] mParallaxAttrs = new int[]{R.attr.translationXIn, R.attr.translationXOut,
            R.attr.translationYIn, R.attr.translationYOut};

    public View createView(View parent, final String name, @NonNull Context context,
                           @NonNull AttributeSet attrs) {

        final boolean isPre21 = Build.VERSION.SDK_INT < 21;
        if (mCompatViewInflater == null){
            mCompatViewInflater = new CompatViewInflater() ;
        }

        // We only want the View to inherit it's context if we're running pre-v21
        final boolean inheritContext = isPre21 && true
                && shouldInheritContext((ViewParent) parent);

        return mCompatViewInflater.createView(parent, name, context, attrs, inheritContext,
                isPre21, /* Only read android:theme pre-L (L+ handles this anyway) */
                true /* Read read app:theme as a fallback at all times for legacy reasons */
        );
    }

    private boolean shouldInheritContext(ViewParent parent) {
        if (parent == null) {
            // The initial parent is null so just return false
            return false;
        }
        while (true) {
            if (parent == null) {
                // Bingo. We've hit a view which has a null parent before being terminated from
                // the loop. This is (most probably) because it's the root view in an inflation
                // call, therefore we should inherit. This works as the inflated layout is only
                // added to the hierarchy at the end of the inflate() call.
                return true;
            } else if (!(parent instanceof View)
                    || ViewCompat.isAttachedToWindow((View) parent)) {
                // We have either hit the window's decor view, a parent which isn't a View
                // (i.e. ViewRootImpl), or an attached view, so we know that the original parent
                // is currently added to the view hierarchy. This means that it has not be
                // inflated in the current inflate() call and we should not inherit the context.
                return false;
            }
            parent = parent.getParent();
        }
    }

    /**
     * 解析所有的 我们关注的属性
     * @param view
     * @param context
     * @param attrs
     */
    private void analysisAttrs(View view, Context context, AttributeSet attrs) {
        Debug.D("解析所有的我们关注的属性");
        TypedArray array = context.obtainStyledAttributes(attrs, mParallaxAttrs);
        if (array != null && array.getIndexCount() != 0){
            int n = array.getIndexCount();
            ParallaxTag tag = new ParallaxTag();
            for (int i = 0; i < n; i++) {
                int attr = array.getIndex(i);
                switch (attr){
                    case 0:
                        tag.translationXIn = array.getFloat(attr , 0f) ;
                        break;
                    case 1:
                        tag.translationXOut = array.getFloat(attr , 0f) ;
                        break;
                    case 2:
                        tag.translationYIn = array.getFloat(attr , 0f) ;
                        break;
                    case 3:
                        tag.translationYOut = array.getFloat(attr , 0f) ;
                        break;
                }
            }

            // 自定义属性怎么存? 需要一一绑定，在View上边设置tag
            view.setTag(R.id.parallax_tag , tag);
            mParallaxViews.add(view) ;
        }
        array.recycle();
    }

    public List<View> getParallaxViews(){
        return mParallaxViews ;
    }
}
