package com.mrrun.module_view.bethel.thumbupeffect;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * 三次方贝塞尔估值器(有两个控制点)
 *
 * @author lipin
 * @date 2018/09/20
 * @version 1.0
 */
public class Bezier3Evaluator implements TypeEvaluator<PointF>{

    // 两个控制点
    private PointF point1, point2;

    public Bezier3Evaluator(PointF pointF1, PointF pointF2) {
        this.point1 = pointF1;
        this.point2 = pointF2;
    }

    /**
     * @param t 百分比， [0, 1]
     * @param point0 起点
     * @param point3 终点
     * @return point 贝塞尔点
     */
    @Override
    public PointF evaluate(float t, PointF point0, PointF point3) {
        // t百分比， 0~1
        PointF point = new PointF();
        point.x = point0.x * (1 - t) * (1 - t) * (1 - t)
                + 3 * point1.x * t * (1 - t) * (1 - t)
                + 3 * point2.x * t * t * (1 - t)
                + point3.x * t * t * t;

        point.y = point0.y * (1 - t) * (1 - t) * (1 - t)
                + 3 * point1.y * t * (1 - t) * (1 - t)
                + 3 * point2.y * t * t * (1 - t)
                + point3.y * t * t * t;
        // 套用公式把点返回
        return point;
    }
}
