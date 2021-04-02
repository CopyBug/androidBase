package com.sx.widget.view;


import android.content.Context;

public class CountDownUtils {
    private static long day = 0;
    private static long hour = 0;
    private static long minute = 0;
    private static long second = 0;

    private static boolean dayNotAlready = false;
    private static boolean hourNotAlready = false;
    private static boolean minuteNotAlready = false;
    private static boolean secondNotAlready = false;


    public static String initData(Context mContext, long totalSeconds) {
        resetData();
        if (totalSeconds > 0) {
            secondNotAlready = true;
            second = totalSeconds;
            if (second >= 60) {
                minuteNotAlready = true;
                minute = second / 60;
                second = second % 60;
                if (minute >= 60) {
                    hourNotAlready = true;
                    hour = minute / 60;
                    minute = minute % 60;
                    if (hour > 24) {
                        dayNotAlready = true;
                        day = hour / 24;
                        hour = hour % 24;
                    }
                }
            }
        }
        String mHour = hour + "";
        String mMinute = minute + "";
        String mSecond = second + "";
        if (hour < 10) {
            mHour = "0" + hour;
        }
        if (minute < 10) {
            mMinute = "0" + minute;
        }
        if (second < 10) {
            mSecond = "0" + second;
        }
        return String.format("%s:%s:%s", mHour, mMinute, mSecond);
    }

    public static String initDataDjs(long totalSeconds) {
        resetData();
        if (totalSeconds > 0) {
            secondNotAlready = true;
            second = totalSeconds;
            if (second >= 60) {
                minuteNotAlready = true;
                minute = second / 60;
                second = second % 60;
                if (minute >= 60) {
                    hourNotAlready = true;
                    hour = minute / 60;
                    minute = minute % 60;
                    if (hour > 24) {
                        dayNotAlready = true;
                        day = hour / 24;
                        hour = hour % 24;
                    }
                }
            }
        }
        String mHour = hour + "";
        String mMinute = minute + "";
        String mSecond = second + "";

        if (hour <= 0) {
            return String.format("%s分钟", mMinute);
        } else {
            return String.format("%s小时%s分", mHour, mMinute);
        }


    }

    private static void resetData() {
        day = 0;
        hour = 0;
        minute = 0;
        second = 0;
        dayNotAlready = false;
        hourNotAlready = false;
        minuteNotAlready = false;
        secondNotAlready = false;
    }


}