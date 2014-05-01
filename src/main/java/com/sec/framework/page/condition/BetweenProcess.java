package com.sec.framework.page.condition;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.avaje.ebean.ExpressionList;
import com.sec.framework.entity.BaseEntity;
import com.sec.framework.form.BaseForm;
import com.sec.framework.page.ConditionProcess;

public class BetweenProcess implements ConditionProcess {

	private static Log log = LogFactory.getLog(BetweenProcess.class.getSimpleName());

	public void process(Object value, Object property, ExpressionList<? extends BaseEntity> where, BaseForm form) {
		String[] propertys = (String[]) property;
		if (value != null) {
			where.le(propertys[0], value);
		}
		Object value1 = null;
		try {
			value1 = BeanUtils.getProperty(form, propertys[1]);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
		if (value1 != null) {
			where.ge(propertys[0], value1);
		}
	}
}
