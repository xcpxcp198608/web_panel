package com.wiatec.panel.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author patrick
 */
public class TimeUtil {

    public static long getUnixFromStr(String time){
        if(TextUtil.isEmpty(time)){
            return 0;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        try {
            date = simpleDateFormat.parse(time);
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
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return simpleDateFormat.format(new Date(time));
        } catch (Exception e) {
            return "";
        }
    }

    public static String getStrDate(){
        return getStrDate(System.currentTimeMillis());
    }

    public static String getStrDate(long time){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return simpleDateFormat.format(new Date(time));
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

    public static boolean isOutExpires(String expiresTime){
        return System.currentTimeMillis() > TimeUtil.getUnixFromStr(expiresTime);
    }

}
