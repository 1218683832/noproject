package com.mrrun.module_utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 时间格式化工具(日期、时间)
 * @author lipin
 * @version 1.0
 */
public class TimeUtils {

    /**
     * （1）1分钟之内显示“刚刚”；
     * （2）前1天显示“昨天”
     * （3）超过1分钟，在1天之内显示具体时间，时+分（24小时制），如“14:10”；
     * （4）超过1天且在7天之内显示“星期X”，如今天是星期一，则从星期六开始倒数显示到星期一。
     * （5）超过7天的显示具体日期，年/月/日，如“17/03/07”
     * （6）特殊情况 : 系统设置非正确的超前时间后又改回正确时间
     *
     * @return
     */
    public static String getSimpleTimeStringByDate(long date) {
        DateTime targetTime = new DateTime(date); // 传入参数对应的时间
        DateTime currentTime = new DateTime();    // 当前时间

        // 特殊情况 : currentTime < targetTime 系统设置非正确的超前时间后又改回正确时间
        if (currentTime.isBefore(targetTime)) {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yy/MM/dd");
            return formatter.print(targetTime);
        }

        // 第一种情况 : (currentTime - 1分钟) < targetTime < (currentTime + 1分钟)
        if (currentTime.minusMinutes(1).isBefore(targetTime)
                && currentTime.plusMinutes(1).isAfter(targetTime)) {
            return "刚刚";
        }

        // 第二种情况 : 前一天的凌晨0:00 < targetTime < 当天凌晨0:00
        DateTime yesterdayTime = currentTime.minusDays(1).withTime(0, 0, 0, 0);  // 前一天凌晨0:00
        DateTime todayAMTime = currentTime.withTime(0, 0, 0, 0); // 今天凌晨0:00
        if (yesterdayTime.isBefore(targetTime)
                && todayAMTime.isAfter(targetTime)) {
            return "昨天";
        }

        // 第三种情况 : (currentTime - 1天) < targetTime < (currentTime - 1分钟)
        if (currentTime.minusDays(1).isBefore(targetTime)
                && currentTime.minusMinutes(1).isAfter(targetTime)) {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm");
            return formatter.print(targetTime);
        }

        // 第四种情况 : (currentTime - 7天) < targetTime < (current - 1天)
        if (currentTime.minusDays(7).isBefore(targetTime)
                && currentTime.minusDays(1).isAfter(targetTime)) {
            String dayOfWeek = null;
            switch (targetTime.getDayOfWeek()) {
                case 1:
                    dayOfWeek = "星期一";
                    break;
                case 2:
                    dayOfWeek = "星期二";
                    break;
                case 3:
                    dayOfWeek = "星期三";
                    break;
                case 4:
                    dayOfWeek = "星期四";
                    break;
                case 5:
                    dayOfWeek = "星期五";
                    break;
                case 6:
                    dayOfWeek = "星期六";
                    break;
                case 7:
                    dayOfWeek = "星期日";
                    break;
            }
            return dayOfWeek;
        }

        // 第五种情况 : (currentTime - 7天) < targetTime
        if (currentTime.minusDays(7).isAfter(targetTime)) {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yy/MM/dd");
            return formatter.print(targetTime);
        }

        return null;
    }
}
