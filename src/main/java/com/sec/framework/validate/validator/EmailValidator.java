package com.sec.framework.validate.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sec.framework.validate.Validable;
import com.sec.framework.validate.Validatee;
import com.sec.framework.validate.exception.ValidateError;
import com.sec.framework.validate.exception.ValidationError;

public class EmailValidator implements Validable {

	public void validate(Validatee info, Field field, Annotation annotation) throws Exception {
		Object value = field.get(info);
		String email = String.valueOf(value);
		Pattern rex = Pattern.compile("^([a-zA-Z0-9]{1})+([a-zA-Z0-9_\\.\\-])*\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z]{1})+([a-zA-Z0-9]{0,2})+([a-zA-Z]{1})+$");
		Matcher matcher = rex.matcher(email);
		if (!matcher.find()) {
			info.putError(field.getName(), new ValidationError(field, new ValidateError("请输入正确的邮箱地址")));
		}
	}

}
