package com.sec.framework.validate.annotation;

import com.sec.framework.validate.Validable;
import com.sec.framework.validate.constant.Phone;
import com.sec.framework.validate.validator.TelephoneValidator;

public @interface Telephone {

	Phone type() default Phone.TELEPHONE;

	Class<? extends Validable> validateClass() default TelephoneValidator.class;
}
