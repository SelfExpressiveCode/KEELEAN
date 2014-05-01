package com.sec.framework.validate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Validator {

	private static Log log = LogFactory.getLog(Validator.class.getSimpleName());

	public static void validate(Validatee target) {
		try {
			Field[] fields = target.getClass().getDeclaredFields();
			for (Field field : fields) {
				Annotation[] annos = field.getDeclaredAnnotations();
				for (Annotation anno : annos) {
					Class<? extends Validable> validableClass = getValidateClass(anno);
					if (null != validableClass) {
						validableClass.newInstance().validate(target, field, anno);
					}
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	private static Class<? extends Validable> getValidateClass(Annotation annotation) throws SecurityException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		try {
			return (Class<? extends Validable>) annotation.getClass().getDeclaredMethod("validateClass").invoke(annotation);
		} catch (NoSuchMethodException e) {
			// 捕获异常但是不处理.有可能声明一些其他的注解就是没有这个函数的
		}
		return null;
	}

}
