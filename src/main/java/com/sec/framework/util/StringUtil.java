package com.sec.framework.util;

import java.security.MessageDigest;
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

		Date date = parseDate(value, "yyyy-MM-dd");
		if (date == null) {
			date = parseDate(value, "MM/dd/yyyy");
		}
		if (date == null) {
			date = parseDate(value, "M/d/yyyy");
		}
		if (date == null) {
			date = parseDate(value, "yyyy-M-d");
		}
		return date;
	}

	public static Date parseDate(String value, String pattern)
			throws ParseException {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			date = sdf.parse(value);
		} catch (ParseException e) {
			return null;
		}
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

	public static String formatShrinkedTimestamp(Date value) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(value);
	}

	public final static String md5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean isNullOrEmpty(String text) {
		return text == null || text.equals("");
	}

}
