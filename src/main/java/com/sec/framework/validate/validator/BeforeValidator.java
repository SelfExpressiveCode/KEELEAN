package com.sec.framework.validate.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Date;

import com.sec.framework.validate.Validable;
import com.sec.framework.validate.Validatee;
import com.sec.framework.validate.annotation.Before;
import com.sec.framework.validate.exception.ValidateError;
import com.sec.framework.validate.exception.ValidationError;

public class BeforeValidator implements Validable {

	public void validate(Validatee info, Field field, Annotation annotation) throws Exception {
		Before before = (Before) annotation;
		Object earlier = field.get(info);
		Field laterField = info.getClass().getDeclaredField(before.value());
		Object later = laterField.get(info);
		if (earlier != null && later != null && earlier instanceof Date && later instanceof Date) {
			if (((Date) earlier).after((Date) later)) {
				info.putError(field.getName(), new ValidationError(field, new ValidateError("开始日期不能大于结束日期")));
			}
		}

	}

}
