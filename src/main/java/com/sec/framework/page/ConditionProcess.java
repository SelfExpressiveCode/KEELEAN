package com.sec.framework.page;

import com.avaje.ebean.ExpressionList;
import com.sec.framework.entity.BaseEntity;
import com.sec.framework.form.BaseForm;

public interface ConditionProcess {

	public void process(Object value, Object property, ExpressionList<? extends BaseEntity> where, BaseForm form);
}
