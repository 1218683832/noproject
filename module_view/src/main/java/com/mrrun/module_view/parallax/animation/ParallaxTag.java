package com.mrrun.module_view.parallax.animation;

/**
 * Email: 2185134304@qq.com
 * Created by JackChen 2018/3/9 9:01
 * Version 1.0
 * Params:
 * Description:  视差动画的 tag 标记
*/
public class ParallaxTag {
    public float translationXIn ;
    public float translationXOut ;
    public float translationYIn ;
    public float translationYOut ;

    @Override
    public String toString() {
        return "ParallaxTag{" +
                "translationXIn=" + translationXIn +
                ", translationXOut=" + translationXOut +
                ", translationYIn=" + translationYIn +
                ", translationYOut=" + translationYOut +
                '}';
    }
}
