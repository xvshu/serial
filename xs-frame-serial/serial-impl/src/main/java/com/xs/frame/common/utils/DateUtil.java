package com.xs.frame.common.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by zscome on 15-4-26.
 */
public class DateUtil {

    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {

        System.out.print(getCurrentTime());

//        Date currentDate = DateUtil.createDate("2015-05-03 12:01:01", YYYYMMDD_HHMMSS);
//        Date d = DateUtil.getAfterHoursDate(currentDate, 1);
//        System.out.println(DateUtil.format2AddFirstTime(new Timestamp(d.getTime())));
//        System.out.println(DateUtil.format2AddLastTime(new Timestamp(d.getTime())));
    }

    private static final Log logger = LogFactory.getLog(DateUtil.class);
    public static final String YYYYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDD = "yyyy-MM-dd";
    public static final String YYYYMMDD_000000 = "yyyy-MM-dd 00:00:00";
    public static final String YYYYMMDD_235959 = "yyyy-MM-dd 23:59:59";
    public static int SECONDS_OF_ONE_DAY = 24 * 60 * 60;

    public static Date getNowDate() {
        return Calendar.getInstance().getTime();
    }

    public static int getNowDayOfWeek() {
        int index = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        return index == 1 ? 7 : index - 1;
    }

    public static int getNowDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK) == 1 ? 7 : calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    public static Timestamp getNowTime() {
        return new Timestamp(getNowDate().getTime());
    }


    public static Timestamp getNowTime(long time) {
        return new Timestamp(time);
    }

    public static Date getAfterHoursDate(Date currentDate, int hours) {
        Date returnDate = null;
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.HOUR, hours);
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD_HHMMSS);
        try {
            returnDate = sdf.parse(sdf.format(d));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return returnDate;
    }

    /**
     * 获取一个时间范围内的所有日期
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return
     */
    public static List<Date> getDates(Date beginDate, Date endDate) {
        beginDate = formatToDate(beginDate, YYYYMMDD);
        endDate = formatToDate(endDate, YYYYMMDD);
        List<Date> dates = new ArrayList<Date>();
        ;
        if (endDate.compareTo(beginDate) > 0) {
            Date index = formatToDate(beginDate, YYYYMMDD);
            while (index.compareTo(endDate) <= 0) {
                dates.add(index);
                index = getTomorrow(index);
            }
        } else if (endDate.compareTo(beginDate) == 0) {
            dates.add(formatToDate(beginDate, YYYYMMDD));
        }
        return dates;
    }


    /**
     * 获取输入日期的前一个月
     *
     * @param currentDate 输入日期
     * @return 输入日期的前一个月的日期
     */
    public static Date getDateBeforeMonth(Date currentDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.MONTH, -1);
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);
        return createDate(sdf.format(d), YYYYMMDD);
    }

    /**
     * 获取输入日期的前一天
     *
     * @param currentDate 输入日期
     * @return 输入日期的前一天
     */
    public static Date getYesterday(Date currentDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, -1);
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);
        return createDate(sdf.format(d), YYYYMMDD);
    }

    /**
     * 获取输入日期的后一天
     *
     * @param currentDate 输入日期
     * @return 输入日期的前一天
     */
    public static Date getTomorrow(Date currentDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, 1);
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);
        return createDate(sdf.format(d), YYYYMMDD);
    }

    /**
     * 获取输入日期的当月第一天
     *
     * @param currentDate 输入日期
     * @return 输入日期的当月第一天
     */
    public static Date getFirstDayOfMonth(Date currentDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.set(Calendar.DATE, 1);
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);
        return createDate(sdf.format(d), YYYYMMDD);
    }

    /**
     * 将时间格式的字符串，转化为Date
     *
     * @param dateFormat exp: 2010-7-22
     * @return java.util.Date
     */
    public static Date createDate(String dateString, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date d = null;
        try {
            d = sdf.parse(dateString);
        } catch (ParseException e) {
            logger.error("字符串转化为Date失败,[string=" + dateString + "]", e);
        }
        return d;
    }

    /**
     * 将时间转化为指定格式的字符串
     *
     * @param date   输入日期
     * @param format 日期格式
     * @return 字符串格式的日期
     * @author lishuai
     */
    public static String format(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String formatAddTime(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date) + " 23:59:59";
    }

    public static Date formatToDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return createDate(sdf.format(date), format);
    }

    /**
	 * 根据给予的时间获取当前一天的最早时间
	 * 
	 * @param cdate
	 * @return
	 */
	public static Integer getStartDate(Integer cdate) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(cdate * 1000L);
		// 将时间设置为当天的0点0分0秒
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		return (int) (cal.getTimeInMillis() / 1000);
	}
	
	/**
	 * 根据给予的时间获取当前一天的最后时间
	 * 
	 * @param cdate
	 * @return
	 */
	public static Integer getEndDate(Integer cdate) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(cdate * 1000L);
		// 将时间设置为当天的23点59分59秒
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);

		return (int) (cal.getTimeInMillis() / 1000);
	}
	
	/**
	 * 获取以毫秒为单位的当前时间。保留10位
	 * 
	 * @param
	 * @return
	 */
	public static int getCurrentTime() {
		return (int) (System.currentTimeMillis() / 1000);
	}
	
	/**
	 * 根据给予时间获取30天后的时间
	 * 
	 * @param date
	 * @return 30天后的时间点
	 */
	public static Integer loadThirtyDays(Integer date) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date * 1000L);
		cal.add(Calendar.DAY_OF_MONTH, 30);
		return (int) (cal.getTimeInMillis() / 1000);
	}
	
	/**
	 * 获取以毫秒为单位的当前时间。保留10位
	 * 
	 * @param
	 * @return
	 * @throws ParseException 
	 */
	public static int getYestdayTime() throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String yesterday = new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
		return DateUtil.stringDateToInt(yesterday);
	}

	/**
	 * 获取当天的年月日
	 * @return
	 */
	public static String getCurrentYMD(){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return sf.format(date);
	}
	
	/**
	 * 将一个字符串型日期数据（年-月-日 ）转化为十位整数值（秒数）
	 * 
	 * @param sdate
	 * @return
	 * @throws ParseException
	 */
	public static int stringDateToInt(String sdate) throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sf.parse(sdate);
		return (int) (date.getTime() / 1000);
	}
	
	public static int getCurrentYMDTime() {
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sf.parse(getCurrentYMD());
			return (int) (date.getTime() / 1000);
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static int stringToInt(String sdate) throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sf.parse(sdate);
		return (int) (date.getTime() / 1000);
	}

    /**
     * 根据给予的时间获取当前月份
     *
     * @param cdate
     * @return
     */
    public static Integer getMonthByCdate(Integer cdate) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(cdate * 1000L);
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 根据给予的时间获取年份
     * @return
     */
    public static Integer getYearByCdate(Integer cdate){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(cdate * 1000L);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 根据给予的时间获取当前的日期
     *
     * @param cdate
     * @return
     */
    public static Integer getDayByCdate(Integer cdate) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(cdate * 1000L);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 将一个十位整形数字转换为日期格式 年-月-日 时：分：秒
     *
     * @param date
     * @return
     */
    public static String intToDate2(int date) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date * 1000L);
        return sf.format(calendar.getTime());
    }

    /**
     * 将一个字符串型日期数据（年-月-日 时：分：秒 ）转化为十位整数值（秒数）
     *
     * @param sdate
     * @return
     * @throws ParseException
     */
    public static int stringDateToInt2(String sdate) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sf.parse(sdate);
        return (int) (date.getTime() / 1000);
    }



    public static Integer getZhiDingMonthDateAfter(Integer cdate, int month) throws Exception{
        if (cdate == null || cdate < 0) {
            cdate = getCurrentTime();
        }
        int currentMonth = getMonthByCdate(cdate);
        int x = currentMonth+month;
        int y = getYearByCdate(cdate);
        int d = getDayByCdate(cdate);
        if (x <= 0) {
            x = 12 +x;
            y = y -1;
        }

        String str = y+"-"+x+"-"+d;

        String strDate = intToDate2(cdate);

        if (strDate != null && !strDate.trim().equals("")) {
            return stringDateToInt2(str + " " + strDate.split(" ")[1]);
        }else {
            return stringDateToInt(str);
        }
    }
}
