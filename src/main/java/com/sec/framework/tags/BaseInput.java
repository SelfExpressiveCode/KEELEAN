package com.sec.framework.tags;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;

import javax.servlet.jsp.tagext.TagSupport;

import com.sec.framework.form.BaseForm;

public class BaseInput extends TagSupport {

	protected String id;
	protected static final long serialVersionUID = 1L;
	protected String label;
	protected Object value;

	protected String fieldName;
	@SuppressWarnings("rawtypes")
	protected Class formClass;
	protected Field field;
	protected BaseForm form;

	protected String labelWidth = "1";
	protected String inputWidth = "2";
	protected String errorWidth = "1";
	protected String labelStyle;

	public Object getValue() {
		try {
			return Class.forName(fieldName.substring(0,
					fieldName.lastIndexOf(".")));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public void setClazz(Class clazz) {
		this.formClass = clazz;
	}

	public Field getField() {
		try {
			return formClass.getDeclaredField(fieldName.substring(fieldName
					.lastIndexOf(".") + 1));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public BaseForm getForm() {
		try {
			return (BaseForm) formClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setForm(BaseForm form) {
		this.form = form;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Class getClazz() {
		return formClass;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	@SuppressWarnings("rawtypes")
	protected String valueToString(Object value, Class type) {

		if (value == null) {
			return "";
		}

		if (type.getName().equals("java.lang.Integer")) {
			return String.valueOf(value);
		}

		if (type.getName().equals("java.lang.Float")) {
			return String.valueOf(value);
		}

		if (type.getName().equals("java.lang.Boolean")) {
			return String.valueOf(value);
		}

		if (type.getName().equals("java.lang.Double")) {
			return String.valueOf(value);
		}

		if (type.getName().equals("java.lang.Long")) {
			return String.valueOf(value);
		}

		if (type.getName().equals("java.lang.String")) {
			return String.valueOf(value);
		}

		if (type.getName().equals("java.util.Date")) {
			// TODO use @Format to output with expected format.
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(value);

		}

		return String.valueOf(value);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabelWidth() {
		return labelWidth;
	}

	public void setLabelWidth(String labelWidth) {
		this.labelWidth = labelWidth;
	}

	public String getInputWidth() {
		return inputWidth;
	}

	public void setInputWidth(String inputWidth) {
		this.inputWidth = inputWidth;
	}

	public String getErrorWidth() {
		return errorWidth;
	}

	public void setErrorWidth(String errorWidth) {
		this.errorWidth = errorWidth;
	}

	public String getLabelStyle() {
		return labelStyle;
	}

	public void setLabelStyle(String labelStyle) {
		this.labelStyle = labelStyle;
	}

}
