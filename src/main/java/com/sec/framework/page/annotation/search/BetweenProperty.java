package com.sec.framework.page.annotation.search;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.sec.framework.page.ConditionProcess;
import com.sec.framework.page.condition.BetweenPropertyProcess;

@Target(value = { ElementType.FIELD })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface BetweenProperty {

	String[] property();

	Class<? extends ConditionProcess> processClass() default BetweenPropertyProcess.class;
}
