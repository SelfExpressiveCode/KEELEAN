package com.sec.framework.form;

import java.lang.reflect.Field;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.format.annotation.DateTimeFormat;

import com.sec.framework.util.StringUtil;

public class FormFactory {

	@SuppressWarnings("rawtypes")
	public static BaseForm parseFromRequest(HttpServletRequest request,
			Class formClazz) {

		try {
			BaseForm form = (BaseForm) formClazz.newInstance();
			Field[] fields = formClazz.getDeclaredFields();
			for (Field field : fields) {
				Object value = request.getParameter(getClassName(formClazz)
						+ "." + field.getName());

				Field formField = form.getClass().getDeclaredField(
						field.getName());
				formField.setAccessible(true);
				assignValue(form, formField, value);
			}
			return form;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	private static String getClassName(Class clazz) {
		String className = clazz.getName().toLowerCase();
		return className.substring(className.lastIndexOf(".") + 1);
	}

	@SuppressWarnings("rawtypes")
	public static void assignValue(BaseForm form, Field formField, Object value) {
		if ((value == null || value == "") && !formField.equals("beginTime")
				&& !formField.equals("endTime")) {
			return;
		}
		try {
			Class clazzType = formField.getType();

			if (clazzType.getName().equals("java.lang.String")) {
				formField.set(form, value);
			} else if (clazzType.getName().equals("java.lang.Integer")) {
				formField.set(form, Integer.parseInt((String) value));
			} else if (clazzType.getName().equals("java.util.Date")) {
				DateTimeFormat anno = formField
						.getAnnotation(DateTimeFormat.class);
				if (anno == null) {
					formField.set(form, StringUtil.parseDate((String) value));
				} else {
					formField.set(form, StringUtil.parseDate((String) value,
							anno.pattern()));
				}
			} else if (clazzType.getName().equals("java.lang.Long")) {
				formField.set(form, Long.parseLong((String) value));
			} else if (clazzType.getName().equals("java.lang.Float")) {
				formField.set(form, Float.parseFloat((String) value));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
