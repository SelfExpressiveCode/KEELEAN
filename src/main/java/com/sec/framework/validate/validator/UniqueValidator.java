package com.sec.framework.validate.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

import com.avaje.ebean.Ebean;
import com.sec.framework.validate.Validable;
import com.sec.framework.validate.Validatee;
import com.sec.framework.validate.annotation.Unique;
import com.sec.framework.validate.exception.ValidateError;
import com.sec.framework.validate.exception.ValidationError;

public class UniqueValidator implements Validable {

	public void validate(Validatee info, Field field, Annotation annotation)
			throws Exception {

		Unique unique = (Unique) annotation;
		Class clazz = Class.forName(unique.entity());
		Field idField = clazz.getDeclaredField(unique.id());
		Field uniqueField = clazz.getDeclaredField(unique.field());

		Object value = uniqueField.get(info);
		Object id = idField.get(info);

		List objects = Ebean.find(clazz).where().eq("deleted", false)
				.eq(unique.field(), value).findList();

		for (Object obj : objects) {
			if (!idField.get(obj).equals(id)
					&& uniqueField.get(obj).equals(value)) {
				info.putError(field.getName(), new ValidationError(field,
						new ValidateError(uniqueField.getName() + "必须是唯一的。")));
				return;
			}
		}

	}
}
