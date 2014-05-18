package com.sec.framework.validate.annotation;

import com.sec.framework.validate.Validable;
import com.sec.framework.validate.validator.UniqueValidator;

public @interface Unique {

	String entity();

	String field();

	Class<? extends Validable> validateClass() default UniqueValidator.class;

	String id();
}
