package com.sec.framework.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtil {

	public static String formatFloat(Float number) {
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(number);
	}

	public static String formatDate(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	public static String parseTimestamp(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	public static Date parseDate(String value) throws ParseException {

		return parseDate(value, "yyyy-MM-dd");
	}

	public static Date parseDate(String value, String pattern)
			throws ParseException {

		System.out.println("parsing.." + value);

		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = sdf.parse(value);
		return date;
	}

	public static Date parseTimestamp(String value) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse(value);
		return date;
	}

	public static String formatYear(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		return sdf.format(date);
	}

	public static String formatTimestamp(Date value) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(value);
	}

}
