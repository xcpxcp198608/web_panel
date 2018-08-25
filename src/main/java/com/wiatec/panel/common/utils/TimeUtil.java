package com.wiatec.panel.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author patrick
 */
public class TimeUtil {

    public static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    public static final long DEFAULT_TIME = 1483200000000L;


    public static long getUnixFromStr(String time){
        if(TextUtil.isEmpty(time)){
            return 0;
        }
        Date date;
        try {
            date = FORMATTER.parse(time);
        } catch (ParseException e) {
            return 0;
        }
        return date.getTime();
    }

    public static String getStrTime(){
        return getStrTime(System.currentTimeMillis());
    }

    public static String getStrTime(long time){
        try {
            return FORMATTER.format(new Date(time));
        } catch (Exception e) {
            return "";
        }
    }

    public static String getStrDate(){
        return getStrDate(System.currentTimeMillis());
    }

    public static String getStrDate(long time){
        try {
            return DATE_FORMATTER.format(new Date(time));
        } catch (Exception e) {
            return "";
        }
    }

    public static String getExpiresTimeByDay(String activateTime, int days){
        Date date = new Date(TimeUtil.getUnixFromStr(activateTime));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        date = calendar.getTime();
        return getStrTime(date.getTime());
    }

    public static String getExpiresTime(String activateTime, int expires){
        Date date = new Date(TimeUtil.getUnixFromStr(activateTime));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, expires);
        date = calendar.getTime();
        return getStrTime(date.getTime());
    }

    public static String getExpiresDate(String activateTime, int expires){
        Date date = new Date(TimeUtil.getUnixFromStr(activateTime));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, expires);
        date = calendar.getTime();
        return getStrDate(date.getTime());
    }

    public static Date getExpiresDate(int expires){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, expires);
        return calendar.getTime();
    }

    public static boolean isOutExpires(String activateTime, int expires){
        Date date = new Date(TimeUtil.getUnixFromStr(activateTime));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, expires);
        date = calendar.getTime();
        return System.currentTimeMillis() > date.getTime();
    }

    public static boolean isOutExpires(String expiresTime) {
        long uTime = TimeUtil.getUnixFromStr(expiresTime);
        long sys = System.currentTimeMillis();
        return uTime > 0 && sys > uTime;
    }

    /**
     * 获取当前时间的字符串格式
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String toStr(){
        return toStr(new Date(System.currentTimeMillis()));
    }

    /**
     * 获取指定unix毫秒数对应的时间字符串
     * @param milliseconds  milliseconds
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String toStr(long milliseconds){
        return toStr(new Date(milliseconds));
    }

    /**
     * 获取指定date对应的时间字符串
     * @param date date
     * @return milliseconds
     */
    public static String toStr(Date date){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(date);
    }

    /**
     * 判断指定日期是否已过期
     * @param expiresDate expiresDate
     * @return boolean
     */
    public static boolean isExpires(Date expiresDate){
        return expiresDate.before(new Date());
    }

    /**
     * 获取从现在起指定month后的结束date
     * @param month month
     * @return end date
     */
    public static Date getExpires(int month){
        return getExpires(new Date(), month);
    }

    /**
     * 获取指定起始date在指定month后的结束date
     * @param date start date
     * @param month month
     * @return end date
     */
    public static Date getExpires(Date date, int month){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    /**
     * 获取字符串格式时间对应的unix时间戳
     * @param date 字符串时间 yyyy-MM-dd or yyyy-MM-dd HH:mm:ss
     * @return unix时间戳
     */
    public static long toUnix(String date){
        if(TextUtil.isEmpty(date)){
            return 0;
        }
        date = date.trim();
        try {
            Date d;
            if(date.length() == 10){
                d = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(date);
            }else{
                d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).parse(date);
            }
            return toUnix(d);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取指定date对应的unix时间戳
     * @param date date
     * @return unix时间戳
     */
    public static long toUnix(Date date){
        return date.getTime();
    }


    public static void main (String [] args){
        System.out.println(toStr());
        System.out.println(toStr(new Date()));
        System.out.println(toStr(1523912301000L));
        System.out.println(isExpires(new Date(1523912301000L)));
        System.out.println(getExpires(5));
        System.out.println(getExpires(new Date(1523912301000L), 5));
        System.out.println(toUnix(new Date()));
        System.out.println(toUnix("2018-01-01  "));
        System.out.println(toUnix("2018-01-01 00:00:00"));
        System.out.println(getStrDate());
    }

}
