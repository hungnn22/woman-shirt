package com.ws.masterserver.utils.common;

import com.ws.masterserver.utils.constants.enums.NotificationTypeEnum;

import java.util.Calendar;
import java.util.Date;

/**
 * @author myname
 */
public class NotificationUtils {
    public static String getCreatedDate(Date createdDate) {
        var calendar = Calendar.getInstance();
        var nowYear = calendar.get(Calendar.YEAR);
        var nowWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        var nowDay = calendar.get(Calendar.DAY_OF_WEEK);
        var nowHour = calendar.get(Calendar.HOUR_OF_DAY);
        var nowMin = calendar.get(Calendar.MINUTE);
        calendar.setTime(createdDate);
        var year = calendar.get(Calendar.YEAR);
        var week = calendar.get(Calendar.WEEK_OF_YEAR);
        var day = calendar.get(Calendar.DAY_OF_WEEK);
        var hour = calendar.get(Calendar.HOUR_OF_DAY);
        var min = calendar.get(Calendar.MINUTE);
        if (nowWeek == week) {
            if (nowDay == day) {
                return getTimeOfDay(nowHour, hour, nowMin, min);
            }
            return getDay(day, createdDate);
        }
        if (nowYear == year) {
            return DateUtils.toStr(createdDate, DateUtils.DD_MM);
        }
        return DateUtils.toStr(createdDate, DateUtils.F_DDMMYYYY);
    }


    private static String getTimeOfDay(int nowHour, int hour, int nowMin, int min) {
        if (nowHour - hour < 1) {
            return nowMin - min + " phút trước";
        }
        return nowHour - hour + " giờ trước";
    }

    private static String getDay(int dow, Date createdDate) {
        var day = "";
        var time = DateUtils.toStr(createdDate, DateUtils.HHMM);
        switch (dow) {
            case 1:
                break;
            case 2:
                day = "Thứ hai";
                break;
            case 3:
                day = "Thứ ba";
                break;
            case 4:
                day = "Thứ tư";
                break;
            case 5:
                day = "Thứ năm";
                break;
            case 6:
                day = "Thứ sáu";
                break;
            case 7:
                day = "Thứ bảy";
                break;
            default:
                break;
        }
        return day + ", " + time;
    }

}
