package com.sec.framework.util;

import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.sec.framework.controller.ToggleKey;
import com.sec.framework.controller.ToggleValue;

public class FeatureToggleDatabaseConfig implements FeatureToggleConfig {

	String tableName;
	Field keyField;
	Field valueField;

	String keyName;
	String valueName;

	public FeatureToggleDatabaseConfig(Class clazz) {

		Table table = (Table) clazz.getAnnotation(Table.class);
		if (table != null) {
			tableName = table.name();
		}

		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {

			ToggleKey key = field.getAnnotation(ToggleKey.class);
			if (key != null
					&& field.getType().getName().equals("java.lang.String")) {
				keyField = field;
				Column keyColumn = keyField.getAnnotation(Column.class);
				keyName = keyColumn.name();
			}

			ToggleValue value = field.getAnnotation(ToggleValue.class);
			if (value != null
					&& field.getType().getName().equals("java.lang.Boolean")) {
				valueField = field;
				Column keyColumn = valueField.getAnnotation(Column.class);
				valueName = keyColumn.name();
			}
		}

	}

	public boolean isEnabled(String feature) {

		// SQL Injection??
		String sql = "select " + valueName + " from " + tableName + " where "
				+ keyName + " = '" + feature + "'";

		SqlQuery query = Ebean.createSqlQuery(sql);
		List<SqlRow> rows = query.findList();
		for (SqlRow row : rows) {
			Boolean value = (Boolean) row.get(valueName);
			return value;
		}

		return false;
	}
}
