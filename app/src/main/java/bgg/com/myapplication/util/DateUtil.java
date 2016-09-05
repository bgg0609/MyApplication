package bgg.com.myapplication.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    /**
     * 时间戳转yyyy-mm-dd
     *
     * @param timestamp
     * @return
     */
    public static String changeStrFromTime(long timestamp) {
        String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss",
                Locale.getDefault())
                .format(new Date(timestamp * 1000));

        return date;
    }

    /**
     * 时间戳转yyyy-mm-dd
     *
     * @param timestamp
     * @return
     */
    public static String changeStrFromMills(long timestamp) {
        String date = new SimpleDateFormat("yyyy-MM-dd hh:mm",
                Locale.getDefault()).format(new Date(timestamp));

        return date;
    }

    /**
     * 转日期时间yyyy-MM-dd HH:mm:ss
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @return
     */
    public static String changeStrFromNumber(int year, int month, int day) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault());

        Calendar mCalendar = Calendar.getInstance();

        mCalendar.set(year, month - 1, day);

        Date mDate = mCalendar.getTime();

        String target = sdf.format(mDate);

        return target;
    }

    /**
     * 转日期时间yyyy-MM-dd
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String changeStrFromNumber2(int year, int month, int day) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
                Locale.getDefault());

        Calendar mCalendar = Calendar.getInstance();

        mCalendar.set(year, month - 1, day);

        Date mDate = mCalendar.getTime();

        String target = sdf.format(mDate);

        return target;
    }

    /**
     * 转yyyy-MM-dd HH:mm:ss +0800
     *
     * @param year   年
     * @param month  月
     * @param day    日
     * @param hour   时
     * @param minute 分
     * @return
     */
    public static String changeStrFromNumber(int year, int month, int day,
                                             int hour, int minute) {

        SimpleDateFormat sdf = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss +0800", Locale.getDefault());

        Calendar mCalendar = Calendar.getInstance();

        mCalendar.set(year, month - 1, day, hour, minute);

        Date mDate = mCalendar.getTime();

        String target = sdf.format(mDate);

        return target;
    }

}
