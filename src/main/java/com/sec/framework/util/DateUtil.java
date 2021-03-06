package com.sec.framework.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static Date today() {
		Date date = new Date(System.currentTimeMillis());
		return date;
	}

	public static Date getDate(String text) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			return sdf.parse(text);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Date afterDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);
		return c.getTime();
	}

	public static Date beforeDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);
		return c.getTime();
	}

	public static double convertMillisecondToHour(long times) {
		return new BigDecimal(times / 1000 / 60 / 60).setScale(1,
				BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static Date after(Date date, int offset) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + offset);
		return c.getTime();
	}

	public static long compare(Date one, Date other) {
		Calendar one1 = Calendar.getInstance();
		one1.setTime(one);
		Calendar other1 = Calendar.getInstance();
		other1.setTime(other);

		return one1.getTimeInMillis() - other1.getTimeInMillis();
	}
}
