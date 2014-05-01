package com.sec.framework.page.condition;

import com.avaje.ebean.ExpressionList;
import com.sec.framework.entity.BaseEntity;
import com.sec.framework.form.BaseForm;
import com.sec.framework.page.ConditionProcess;

public class GtProcess implements ConditionProcess {

	public void process(Object value, Object property, ExpressionList<? extends BaseEntity> where, BaseForm form) {
		if (value != null) {
			where.gt(String.valueOf(property), value);
		}
	}

}
