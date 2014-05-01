package com.sec.framework.validate.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.sec.framework.validate.Validable;
import com.sec.framework.validate.validator.BeforeValidator;

@Target(value = { ElementType.FIELD })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Before {

	String value();

	Class<? extends Validable> validateClass() default BeforeValidator.class;
}
