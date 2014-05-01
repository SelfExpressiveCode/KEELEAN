package com.sec.framework.page.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.sec.framework.entity.BaseEntity;
import com.sec.framework.form.BaseForm;
import com.sec.framework.page.PageVisitor;

@Target(value = { ElementType.METHOD })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface PageService {

	Class<? extends BaseEntity> entityClass();

	Class<? extends PageVisitor> visitorClass() default PageVisitor.class;

	Class<? extends BaseForm> searchFormClass();
}
