package com.sec.framework.text.excel.parser;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sec.framework.entity.BaseEntity;
import com.sec.framework.text.excel.annotation.ExcelColumn;
import com.sec.framework.text.excel.exception.ExcelException;
import com.sec.framework.text.excel.parser.Excel.WorkCell;
import com.sec.framework.text.excel.parser.Excel.WorkRow;
import com.sec.framework.validate.Validatee;
import com.sec.framework.validate.Validator;
import com.sec.framework.validate.exception.ValidationError;

public abstract class ExcelBean implements Validatee {

	protected static Log log = LogFactory.getLog(ExcelBean.class.getSimpleName());

	public Map<String, ValidationError> errors = new HashMap<String, ValidationError>();

	public void putError(String field, ValidationError validationError) {
		errors.put(field, validationError);
	}

	public void clearError() {
		errors.clear();
	}

	public boolean hasError() {
		return !errors.isEmpty();
	}

	public void validate() throws ExcelException {
		Validator.validate(this);
		if (!errors.isEmpty()) {
			throw new ExcelException(errors.toString());
		}
	}

	public <T extends BaseEntity> T toEntity(Class<T> entityClass) {
		Field[] fields = this.getClass().getDeclaredFields();
		T entity = null;
		try {
			entity = entityClass.newInstance();
			for (Field field : fields) {
				Object value = field.get(this);
				Field entityField = FieldUtils.getDeclaredField(entityClass, field.getName());
				entityField.setAccessible(true);
				entityField.set(entity, value);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return entity;
	}

	public void fill(WorkRow row) {
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			ExcelColumn column = field.getAnnotation(ExcelColumn.class);
			if (column != null) {
				try {
					BeanUtils.setProperty(this, field.getName(), getCellValue(row, column));
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
	}

	private Object getCellValue(WorkRow row, ExcelColumn column) {
		WorkCell cell = row.getWorkCell(column.value());
		return cell.getContent();
	}
}
