package com.app.sanyou.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String getTimeStart(String timeStr){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String timestamp = "";
        try {
            Date date = sdf.parse(timeStr);
            long time = date.getTime();
            timestamp = String.valueOf(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    public static String getTimeEnd(String timeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
        String timestamp = "";
        try {
            Date date = sdf.parse(timeStr + "23:59");
            long time = date.getTime();
            timestamp = String.valueOf(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }
}
