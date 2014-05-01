package com.sec.framework.validate.exception;

import java.lang.reflect.Field;

@SuppressWarnings("serial")
public class ValidationError extends RuntimeException {

	public ValidationError(Field field, ValidateError formError) {
		this.field = field;
		this.error = formError;
	}

	public Field field;
	
	public ValidateError error;
	
	@Override
	public String toString() {
		return "属性:"+field.getName()+",错误信息:"+error.toString();
	}
}
