/** 
 * @Title: DateFormatUtil.java 
 * @Package com.wonders.frame.core.utils 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author lushuaifeng
 * @version V1.0 
 */
package com.wonders.frame.core.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wonders.frame.core.model.vo.GenericEnum;

/**
 * @ClassName: TestDateFormatUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class DateFormatUtil {
	private final static Logger logger = LoggerFactory
			.getLogger(DateFormatUtil.class);
	public static final String DATE_FORMAT = "yyyy-MM-dd";

	public static final String TIME_FORMAT = "HH:mm:ss";

	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final String DATETIME_FORMAT_TZ = "yyyy-MM-dd'T'HH:mm:ss";

	public static final String FILE_DATETIME_FORMAT = "yyyyMMddHHmmss";

	public static String getCurrentDate() {
		return timeFormat(DATE_FORMAT, new Date());
	}

	public static String getCurrentTime() {
		return timeFormat(TIME_FORMAT, new Date());
	}

	public static String getCurrentDateTime() {
		return timeFormat(DATETIME_FORMAT, new Date());
	}

	public static String getFileDateTime() {
		return timeFormat(FILE_DATETIME_FORMAT, new Date());
	}

	public static String formatDateTime(String s) {
		Date date = timeParse(DATETIME_FORMAT_TZ, s);
		if (date == null) {
			date = timeParse(TIME_FORMAT, s);
		}
		if (date == null) {
			return s;
		} else {
			return timeFormat(TIME_FORMAT, date);
		}
	}

	public static String timeFormat(String format, Date day) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			String date = sdf.format(day);
			return date;
		} catch (Exception e) {
			logger.error("Exception Throwable", e);
		}
		return null;
	}

	public static Date timeParse(String format, String time) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date date = sdf.parse(time);
			return date;
		} catch (Exception e) {
			logger.error("Exception Throwable", e);
		}
		return null;
	}

	public static String getDay(int num) {
		Date date = new Date();
		if (num != 0) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, num);
			date = calendar.getTime();
		}
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		String day = formatter.format(date);

		return day;
	}

	/**
	 * @param time yyyy-mm-dd hh:mm:ss[.f...]
	 * @return
	 */
	public static Timestamp stringToTimestamp(String time) {
		Timestamp timestamp = null;
		if (time == null) {
			timestamp = null;
		} else {
			try {
				timestamp = Timestamp.valueOf(time);
			} catch (Exception e) {
				logger.error("Exception Throwable", e);
				timestamp = null;
			}
		}
		return timestamp;
	}

	public static Timestamp stringToTimestamp(String format, String timestampStr) {
		if (timestampStr == null || timestampStr.trim().equals("")) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		try {
			Date date = dateFormat.parse(timestampStr);
			return new Timestamp(date.getTime());
		} catch (Exception e) {
			logger.error("Exception Throwable", e);
		}
		return null;
	}

	public static String getTime(String interval) {
		Date date = new Date();
		String timeInterval = interval.toUpperCase();
		int intervalType;
		if (timeInterval.endsWith("H")) {
			interval = timeInterval.replace("H", "");
			intervalType = Calendar.HOUR;
		} else if (timeInterval.endsWith("M")) {
			interval = timeInterval.replace("M", "");
			intervalType = Calendar.MINUTE;
		} else {
			interval = timeInterval.replace("S", "");
			intervalType = Calendar.SECOND;
		}
		if (!interval.equals("")) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(intervalType, Integer.parseInt(interval));
			date = calendar.getTime();
		}
		SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT);
		String time = formatter.format(date);

		return time;
	}

	public static boolean isValidDate(String pattern,String str) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			// 设置lenient为false.
			// 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			format.setLenient(false);
			format.parse(str);
			return true;
		} catch (ParseException e) {
			e.printStackTrace();
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
	}

	// "2008-10-17"
	// "2008/10/17"
	// "2008-08-01"
	// "2008-8-1"
	// "2008/8/1"
	//
	// "2008-10-17  21:12:15"
	// "2008/10/17  08:5:05"
	// "2008-08-01 8:15:20"
	// "2008-8-1 21:12:15"
	// "2008/8/1 21:02:05"
	public static enum DataFormat implements GenericEnum {
		DATE(1, DATE_FORMAT), DATETIME(2, DATETIME_FORMAT);

		private int code;
		private String description;

		private DataFormat(int code, String description) {
			this.code = code;
			this.description = description;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public int code() {
			return code;
		}

		public String description() {
			return description;
		}
	}

	public static void main(String[] args) {

		Date d = timeParse("yyyy-MM-dd HH:mm:ss", "2013-01-12 12:43:32");
		System.out.println(d);
		String time = timeFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", d);
		System.out.println(time);
		for (DataFormat s : DataFormat.values()) {
			System.out.println(s.code() + ", ordinal " + s.description());
		}
		System.out.println(stringToTimestamp("2013/01/12 12:43:32", "yyyy/MM/dd"));
	}
}
