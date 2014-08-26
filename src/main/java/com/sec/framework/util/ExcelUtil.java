package com.sec.framework.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;

public class ExcelUtil {

	public static String getStringValue(HSSFCell cell) {
		int type = cell.getCellType();

		String value = "";
		System.out.println("type= " + type);
		switch (type) {
		case HSSFCell.CELL_TYPE_STRING:
			value = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			double cellValue = cell.getNumericCellValue();

			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				System.out.println("formated cell..."
						+ cell.getCellStyle().getDataFormat());
				if (cell.getCellStyle().getDataFormat() == HSSFDataFormat
						.getBuiltinFormat("yyyy-MM-dd")) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date date = org.apache.poi.ss.usermodel.DateUtil
							.getJavaDate(cellValue);
					value = sdf.format(date);
				} else if (cell.getCellStyle().getDataFormat() == 14) { // 14 ==
																		// yyyy-M-d
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
					Date date = org.apache.poi.ss.usermodel.DateUtil
							.getJavaDate(cellValue);
					value = sdf.format(date);
				}
			} else {
				value = String.valueOf(cellValue);
				String[] parts = value.split("\\.");
				if (parts.length > 1) {
					System.out.println("part[1]= " + parts[1]);
					if (isAllZero(parts[1])) {
						System.out.println("all zero");
						value = parts[0];
					}
				} else {
					System.out.println("part[0]= " + parts[0]);
				}
			}
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			value = cell.getStringCellValue();
			break;
		}

		System.out.println("value= " + value);
		return value;
	}

	private static boolean isAllZero(String text) {

		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c != '0') {
				return false;
			}
		}
		return true;
	}
}
