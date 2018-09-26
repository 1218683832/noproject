package com.mrrun.module_view.parallax.parallaxanimation1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ParallaxFragment extends Fragment {

    public static final String LAYOUT_ID_KEY = "LAYOUT_ID_KEY";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = getArguments().getInt(LAYOUT_ID_KEY);
        return inflater.inflate(layoutId, null);
    }
}
