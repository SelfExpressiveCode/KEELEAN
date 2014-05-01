package com.sec.framework.validate.annotation;

import com.sec.framework.validate.Validable;
import com.sec.framework.validate.validator.IpValidator;

public @interface Ip {

	Class<? extends Validable> validateClass() default IpValidator.class;
}
