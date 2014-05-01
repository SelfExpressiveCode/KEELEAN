package com.sec.framework.page.dao;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.sec.framework.entity.BaseEntity;
import com.sec.framework.form.BaseForm;
import com.sec.framework.page.ConditionProcess;
import com.sec.framework.page.vo.PageResult;

public class PagingFinder {

	private Class<? extends BaseEntity> entityClass;

	private static Log log = LogFactory.getLog(PagingFinder.class.getSimpleName());

	public PagingFinder(Class<? extends BaseEntity> entityClass) {
		this.entityClass = entityClass;
	}

	public PageResult queryByPage(int pageNo, int pageSize, BaseForm form) {
		PageResult pageResult = new PageResult();
		Query<? extends BaseEntity> query = Ebean.find(entityClass);
		List<? extends BaseEntity> datas = null;
		int count = 0;
		if (form != null) {
			ExpressionList<? extends BaseEntity> where = query.where();
			List<Condition> conditions = getConditionsByForm(form);
			for (Condition condition : conditions) {
				// 调用相应的条件处理类进行去处理
				condition.condition.process(condition.value, condition.property, where, form);
			}
			count = where.findRowCount();
			where.setFirstRow((pageNo - 1) * pageSize).setMaxRows(pageSize);
			datas = where.findList();
		} else {
			count = query.findRowCount();
			query.setFirstRow((pageNo - 1) * pageSize).setMaxRows(pageSize);
			datas = query.findList();
		}
		pageResult.setResults(datas);
		pageResult.setCount(count);
		return pageResult;
	}

	private List<Condition> getConditionsByForm(BaseForm form) {
		List<Condition> conditions = new ArrayList<Condition>();
		try {
			Field[] fields = form.getClass().getDeclaredFields();
			for (Field field : fields) {
				/*
				 * 查找声明在field上面的注解，然后组装condition对象
				 */
				Condition condition = new Condition();
				setCondition(field, condition);
				condition.value = getFieldValue(form, field);
				conditions.add(condition);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
		return conditions;
	}

	private Object getFieldValue(BaseForm form, Field field) throws IllegalArgumentException, IllegalAccessException {
		field.setAccessible(true);
		return field.get(form);
	}

	/**
	 * 获取where条件注解的处理类的类对象
	 * 
	 * @param field
	 * @return
	 * @throws Exception
	 */
	private void setCondition(Field field, Condition condition) throws Exception {
		Annotation[] annotations = field.getAnnotations();
		for (Annotation anno : annotations) {
			Method method = null;
			try {
				method = anno.getClass().getDeclaredMethod("processClass");
			} catch (Exception e) {
				// 如果找不到函数不报错继续执行
			}
			if (method != null) {
				Class<? extends ConditionProcess> processClass = (Class<? extends ConditionProcess>) method.invoke(anno);
				condition.condition = processClass.newInstance();
				condition.property = anno.getClass().getDeclaredMethod("property").invoke(anno);
			}
		}
	}

	private class Condition {

		public Object property;

		public Object value;

		public ConditionProcess condition;

	}
}
