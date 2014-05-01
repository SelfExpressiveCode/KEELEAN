package com.sec.framework.validate.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.sec.framework.validate.Validable;
import com.sec.framework.validate.validator.EmailValidator;

@Target(value = { ElementType.FIELD })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Email {

	Class<? extends Validable> validateClass() default EmailValidator.class;
}
