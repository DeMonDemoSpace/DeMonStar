package com.demon.baseutil;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author DeMon
 * @date 2017/12/8
 * @description
 */
@SuppressLint("SimpleDateFormat")
public class DateUtil {

    public static final String YYYY = "yyyy";
    public static final String YYYY_MM = "yyyy-MM";
    public static final String YYYYMM = "yyyy/MM";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYYMMDD = "yyyy/MM/dd";
    public static final String YYYY_MM_DD_HH = "yyyy-MM-dd HH";
    public static final String YYYYMMDDHH = "yyyy/MM/dd HH";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYYMMDDH_MM = "yyyy/MM/dd HH:mm";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDDHHMMSS = "yyyy/MM/dd HH:mm:ss";
    public static final String UTC = "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * 获取当前时间的第n天
     *
     * @param n
     * @return
     */
    public static String getDay(int n) {
        Date date = getNextDay(new Date(), n);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String formatTimestamp(long time) {
        return formatTimestamp(time, YYYY_MM_DD_HH_MM);
    }

    public static String formatTimestamp(long time, String format) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static Date parseTime(String time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static String getNowTime() {
        return getNowTime(YYYY_MM_DD_HH_MM_SS);
    }

    public static String getNowTime(String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        return sdf.format(date);
    }

    public static String UTCFormat(String s) {
        return UTCFormat(s, YYYY_MM_DD_HH_MM_SS);
    }

    public static String UTCFormat(String s, String format) {
        SimpleDateFormat sdf1 = new SimpleDateFormat(UTC);
        SimpleDateFormat sdf2 = new SimpleDateFormat(format);
        String str = "";
        try {
            Date date = sdf1.parse(s);
            str = sdf2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static int getDistanceDays(String date) {
        SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD);
        int days = 0;
        try {
            Date time = df.parse(date);//String转Date
            Date now = new Date();//获取当前时间
            long time1 = time.getTime();
            long time2 = now.getTime();
            long diff = time2 - time1;
            days = (int) (diff / (1000 * 60 * 60 * 24));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return days;
    }

    public static int getDaysOfMonth(String data) {
        return getDaysOfMonth(stringToDate(data, YYYY_MM_DD));
    }

    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }


    public static Date stringToDate(String time, String mat) {
        DateFormat format = new SimpleDateFormat(mat);//日期格式
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    public static String dateToString(Date date, String mat) {
        SimpleDateFormat sformat = new SimpleDateFormat(mat);//日期格式
        String time = sformat.format(date);
        return time;
    }

    /**
     * 仿QQ，微信聊天时间格式化
     *
     * @param date 日期时间
     * @return
     */
    public static String QQFormatTime(Date date) {
        return QQFormatTime(date.getTime());
    }

    /**
     * 仿QQ，微信聊天时间格式化
     *
     * @param time 时间戳
     * @return
     */
    public static String QQFormatTime(long time) {
        Date date = new Date();
        date.setTime(time);
        if (isSameYear(date)) { //同一年 显示MM-dd HH:mm
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.CHINA);
            if (isSameDay(date)) { //同一天 显示HH:mm
                int minute = minutesAgo(time);
                if (minute < 60) {//1小时之内 显示n分钟前
                    if (minute <= 1) {//一分钟之内，显示刚刚
                        return "刚刚";
                    } else {
                        return minute + "分钟前";
                    }
                } else {
                    return simpleDateFormat.format(date);
                }
            } else {
                if (isYesterday(date)) {//昨天，显示昨天+HH:mm
                    return "昨天 " + simpleDateFormat.format(date);
                } else if (isSameWeek(date)) {//本周,显示周几+HH:mm
                    String weekday = null;
                    if (date.getDay() == 1) {
                        weekday = "周一";
                    }
                    if (date.getDay() == 2) {
                        weekday = "周二";
                    }
                    if (date.getDay() == 3) {
                        weekday = "周三";
                    }
                    if (date.getDay() == 4) {
                        weekday = "周四";
                    }
                    if (date.getDay() == 5) {
                        weekday = "周五";
                    }
                    if (date.getDay() == 6) {
                        weekday = "周六";
                    }
                    if (date.getDay() == 0) {
                        weekday = "周日";
                    }
                    return weekday + " " + simpleDateFormat.format(date);
                } else {//同一年 显示MM-dd HH:mm
                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA);
                    return sdf.format(date);
                }
            }
        } else {//不是同一年 显示完整日期yyyy-MM-dd HH:mm
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
            return sdf.format(date);
        }
    }

    /**
     * 几分钟前
     *
     * @param time
     * @return
     */
    public static int minutesAgo(long time) {
        return (int) ((System.currentTimeMillis() - time) / (60000));
    }


    /**
     * 与当前时间是否在同一周
     * 先判断是否在同一年，然后根据Calendar.DAY_OF_YEAR判断所得的周数是否一致
     *
     * @param date
     * @return
     */
    public static boolean isSameWeek(Date date) {
        if (isSameYear(date)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int a = calendar.get(Calendar.DAY_OF_YEAR);

            Date now = new Date();
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(now);
            int b = calendar1.get(Calendar.DAY_OF_WEEK);
            return a == b;
        } else {
            return false;
        }
    }

    /**
     * 是否是当前时间的昨天
     * 获取指定时间的后一天的日期，判断与当前日期是否是同一天
     *
     * @param date
     * @return
     */
    public static boolean isYesterday(Date date) {
        Date yesterday = getNextDay(date, 1);
        return isSameDay(yesterday);
    }

    /**
     * 判断与当前日期是否是同一天
     *
     * @param date
     * @return
     */
    public static boolean isSameDay(Date date) {
        return isEquals(date, "yyyy-MM-dd");
    }

    /**
     * 判断与当前日期是否是同一月
     *
     * @param date
     * @return
     */
    public static boolean isSameMonth(Date date) {
        return isEquals(date, "yyyy-MM");
    }

    /**
     * 判断与当前日期是否是同一年
     *
     * @param date
     * @return
     */
    public static boolean isSameYear(Date date) {
        return isEquals(date, "yyyy");
    }


    /**
     * 格式化Date，判断是否相等
     *
     * @param date
     * @return 是返回true，不是返回false
     */
    private static boolean isEquals(Date date, String format) {
        //当前时间
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat(format);
        //获取今天的日期
        String nowDay = sf.format(now);
        //对比的时间
        String day = sf.format(date);
        return day.equals(nowDay);
    }

    /**
     * 获取某个date第n天的日期
     * n<0 表示前n天
     * n=0 表示当天
     * n>1 表示后n天
     *
     * @param date
     * @param n
     * @return
     */
    public static Date getNextDay(Date date, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, n);
        date = calendar.getTime();
        return date;
    }

}

