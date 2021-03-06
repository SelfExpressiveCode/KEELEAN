package com.sec.framework.validate.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.sec.framework.validate.Validable;
import com.sec.framework.validate.Validatee;
import com.sec.framework.validate.exception.ValidateError;
import com.sec.framework.validate.exception.ValidationError;

public class RequiredValidator implements Validable {

	public void validate(Validatee info, Field field, Annotation annotation) throws Exception {
		Object value = field.get(info);
		if (value == null) {
			info.putError(field.getName(), new ValidationError(field, new ValidateError("不能为空")));
		} else if (value instanceof String && String.valueOf(value).trim().equals("")) {
			info.putError(field.getName(), new ValidationError(field, new ValidateError("不能为空")));
		}
	}

}
