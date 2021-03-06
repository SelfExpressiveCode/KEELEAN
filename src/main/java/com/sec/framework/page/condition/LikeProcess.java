package com.sec.framework.page.condition;

import com.avaje.ebean.ExpressionList;
import com.sec.framework.entity.BaseEntity;
import com.sec.framework.form.BaseForm;
import com.sec.framework.page.ConditionProcess;

public class LikeProcess implements ConditionProcess {

	public void process(Object value, Object property, ExpressionList<? extends BaseEntity> where, BaseForm form) {
		if (value != null) {
			String valueStr = String.valueOf(value);
			where.like(String.valueOf(property), valueStr);
		}
	}
}
