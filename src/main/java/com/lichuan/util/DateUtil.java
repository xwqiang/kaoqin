package com.lichuan.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * KEG Tsinghua University Description: 时间操作类 Copyright: (c) Copyright KEG, 2010
 * .
 * 
 * @author lichuan Create at: 2010-4-12 上午11:30:15
 */
public class DateUtil {

	/**
	 * 获取现在时间
	 * 
	 * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
	 * @throws ParseException
	 */
	public static Date getNowDate() throws ParseException {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(8);
		Date currentTime_2 = formatter.parse(dateString, pos);
		return currentTime_2;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回短时间格式 yyyy-MM-dd
	 */
	public static Date getNowDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(8);
		Date currentTime_2 = formatter.parse(dateString, pos);
		return currentTime_2;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回短时间字符串格式yyyy-MM-dd
	 */
	public static String getStringDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取时间 小时:分;秒 HH:mm:ss
	 * 
	 * @return
	 */
	public static String getTimeShort() {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date currentTime = new Date();
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDateLong(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String dateToStrLong(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式时间转换为字符串 yyyy-MM-dd
	 * 
	 * @param dateDate
	 * @param k
	 * @return
	 */
	public static String dateToStr(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 得到现在时间
	 * 
	 * @return
	 */
	public static Date getNow() {
		Date currentTime = new Date();
		return currentTime;
	}

	/**
	 * 提取一个月中的最后一天
	 * 
	 * @param day
	 * @return
	 */
	public static Date getLastDate(long day) {
		Date date = new Date();
		long date_3_hm = date.getTime() - 3600000 * 34 * day;
		Date date_3_hm_date = new Date(date_3_hm);
		return date_3_hm_date;
	}

	/**
	 * 得到现在时间
	 * 
	 * @return 字符串 yyyyMMdd HHmmss
	 */
	public static String getStringToday() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 得到现在小时
	 */
	public static String getHour() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		String hour;
		hour = dateString.substring(11, 13);
		return hour;
	}

	/**
	 * 得到现在分钟
	 * 
	 * @return
	 */
	public static String getTime() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		String min;
		min = dateString.substring(14, 16);
		return min;
	}

	/**
	 * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
	 * 
	 * @param sformat
	 *            yyyyMMddhhmmss
	 * @return
	 */
	public static String getUserDate(String sformat) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(sformat);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 二个小时时间间的差值,必须保证二个时间都是"HH:MM"的格式，返回字符型的分钟
	 */
	public static int getTwoMinutes(String str1, String str2) {

		String h1 = PatternTool.getMatch(str1,
				"\\d{4}-\\d{2}-\\d{2} (\\d{2}):\\d{2}:\\d{2}", 1);
		String m1 = PatternTool.getMatch(str1,
				"\\d{4}-\\d{2}-\\d{2} \\d{2}:(\\d{2}):\\d{2}", 1);
		String h2 = PatternTool.getMatch(str2,
				"\\d{4}-\\d{2}-\\d{2} (\\d{2}):\\d{2}:\\d{2}", 1);
		String m2 = PatternTool.getMatch(str2,
				"\\d{4}-\\d{2}-\\d{2} \\d{2}:(\\d{2}):\\d{2}", 1);

		int mt1 = Integer.valueOf(h1) * 60 + Integer.valueOf(m1);
		int mt2 = Integer.valueOf(h2) * 60 + Integer.valueOf(m2);

		return mt1 - mt2;

	}

	/**
	 * 得到二个日期间的间隔天数
	 */
	public static String getTwoDay(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			java.util.Date date = myFormatter.parse(sj1);
			java.util.Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return "";
		}
		return day + "";
	}

	/**
	 * 时间前推或后推分钟,其中JJ表示分钟.
	 */
	public static String getPreTime(String sj1, String jj) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mydate1 = "";
		try {
			Date date1 = format.parse(sj1);
			long Time = (date1.getTime() / 1000) + Integer.parseInt(jj) * 60;
			date1.setTime(Time * 1000);
			mydate1 = format.format(date1);
		} catch (Exception e) {
		}
		return mydate1;
	}

	/**
	 * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
	 */
	public static String getNextDay(String nowdate, String delay) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String mdate = "";
			Date d = strToDate(nowdate);
			long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24
					* 60 * 60;
			d.setTime(myTime * 1000);
			mdate = format.format(d);
			return mdate;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 判断是否润年
	 * 
	 * @param ddate
	 * @return
	 */
	public static boolean isLeapYear(String ddate) {

		/**
		 * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
		 * 3.能被4整除同时能被100整除则不是闰年
		 */
		Date d = strToDate(ddate);
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(d);
		int year = gc.get(Calendar.YEAR);
		if ((year % 400) == 0)
			return true;
		else if ((year % 4) == 0) {
			if ((year % 100) == 0)
				return false;
			else
				return true;
		} else
			return false;
	}

	/**
	 * 返回美国时间格式 26 Apr 2006
	 * 
	 * @param str
	 * @return
	 */
	public static String getEDate(String str) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(str, pos);
		String j = strtodate.toString();
		String[] k = j.split(" ");
		return k[2] + k[1].toUpperCase() + k[5].substring(2, 4);
	}

	// 获取上个月
	public static String getLastMonth(String everyMonth) {

		String lastMonth = null;

		if (everyMonth == null) {

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
			everyMonth = formatter.format(Calendar.getInstance().getTime());
		}

		String[] arr = everyMonth.split("-");
		int year = (Integer.valueOf(arr[0]));
		int month = (Integer.valueOf(arr[1]));

		int lastMon = 0;
		if (month == 1) {

			year = year - 1;
			lastMon = 12;

		} else {
			lastMon = month - 1;
		}

		if (lastMon < 10) {

			lastMonth = year + "-0" + lastMon;
		} else {
			lastMonth = year + "-" + lastMon;
		}

		return lastMonth;

	}

	public static String getCurrentMonth() {

		Calendar cal = Calendar.getInstance();
		int year = 0;
		int month = cal.get(Calendar.MONTH); // 上月月份
		String newMonth = "";
		// 设置年月
		if (month == 12) {
			year = cal.get(Calendar.YEAR) + 1;
			newMonth = "01";
		} else {
			if (month < 9) {
				newMonth = "0" + String.valueOf(month + 1);
			} else {
				newMonth = String.valueOf(month + 1);
			}
			year = cal.get(Calendar.YEAR);
		}
		// 设置天数
		return year + "-" + newMonth.toString();
	}

	// 获取下个月
	public static String getNextMonth(String everyMonth) {
		
		String nextMonth = null;

		if (everyMonth == null) {

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
			everyMonth = formatter.format(Calendar.getInstance().getTime());
		}
		
		String[] arr = everyMonth.split("-");
		int year = (Integer.valueOf(arr[0]));
		int month = (Integer.valueOf(arr[1]));

		int nextMon = 0;
		if (month == 12) {

			year = year + 1;
			nextMon = 1;

		} else {
			nextMon = month + 1;
		}

		if (nextMon < 10) {

			nextMonth = year + "-0" + nextMon;
		} else {
			nextMonth = year + "-" + nextMon;
		}


		return nextMonth;

	}

	public static void main(String[] args) throws ParseException {

		System.out.println(getNow());
	}

	public static String getMonth(String month) {

		String result = null;
		if (month.equals("Jan")) {
			result = "01";
		} else if (month.equals("Feb")) {
			result = "02";
		} else if (month.equals("Mar")) {
			result = "03";
		} else if (month.equals("Apr")) {
			result = "04";
		} else if (month.equals("May")) {
			result = "05";
		} else if (month.equals("Jun")) {
			result = "06";
		} else if (month.equals("Jul")) {
			result = "08";
		} else if (month.equals("Aug")) {
			result = "08";
		} else if (month.equals("Sep")) {
			result = "09";
		} else if (month.equals("Oct")) {
			result = "10";
		} else if (month.equals("Nov")) {
			result = "11";
		} else if (month.equals("Dec")) {
			result = "12";
		}

		return result;
	}
}
