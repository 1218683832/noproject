package com.mrrun.module_view;

import android.graphics.PointF;

/**
 * 计算工具类
 *
 * @author lipin
 * @date 2018/08/28
 * @version 1.0
 */
public class CalculateUtil {

    /**
     * 在自定义view中，计算某个点在圆上的位置.
     *
     * @param centerPoint 圆中心点
     * @param radius 圆半径
     * @param degrees 该点到中心点的连线与Y轴的夹角 [0, 360]
     * @return 该点的坐标
     */
    public static PointF calculateCirclePoint(PointF centerPoint, float radius, int degrees) {
        PointF pointF = new PointF();
        pointF.x = (float) (centerPoint.x +  radius * Math.cos((degrees) * 3.14 / 180));
        pointF.y = (float) (centerPoint.y +  radius * Math.sin((degrees) * 3.14 / 180));
        return pointF;
    }
}
