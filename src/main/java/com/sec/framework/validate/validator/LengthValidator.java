package com.sec.framework.validate.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.sec.framework.validate.Validable;
import com.sec.framework.validate.Validatee;
import com.sec.framework.validate.annotation.Length;
import com.sec.framework.validate.exception.ValidateError;
import com.sec.framework.validate.exception.ValidationError;

public class LengthValidator implements Validable {

	public void validate(Validatee info, Field field, Annotation annotation) throws Exception {
		Object obj = field.get(info);
		Length length = (Length) annotation;
		if (obj != null && obj.toString().length() < length.min()) {
			info.putError(field.getName(), new ValidationError(field, new ValidateError("最小长度：%1", length.min())));
		} else if (obj != null && obj.toString().length() > length.max()) {
			info.putError(field.getName(), new ValidationError(field, new ValidateError("最大长度：%1", length.max())));
		}

	}

}
