package com.mrrun.module_view.parallax.animation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ParallaxFragment extends Fragment implements LayoutInflaterFactory {

    public static final String LAYOUT_ID_KEY = "LAYOUT_ID_KEY";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 获取布局Id
        int layoutId = getArguments().getInt(LAYOUT_ID_KEY);
        // 拦截View的创建,自己解析属性
        LayoutInflaterCompat.setFactory(inflater, this);
        return inflater.inflate(layoutId, null);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        // View都会来这里，创建View
        return null;
    }
}
