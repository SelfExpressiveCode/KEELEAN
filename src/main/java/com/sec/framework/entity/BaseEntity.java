package com.sec.framework.entity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlUpdate;
import com.sec.framework.util.DateUtil;
import com.sec.framework.util.StringUtil;

public abstract class BaseEntity implements Logical {

	public void save() {
		Ebean.beginTransaction();
		Ebean.save(this);
		Ebean.commitTransaction();
	}

	public void update() {
		Ebean.beginTransaction();
		Ebean.update(this);
		Ebean.commitTransaction();
	}

	public void fakeSave() {
		Ebean.save(this);
	}

	public void fakeUpdate() {
		Ebean.update(this);
	}

	public void delete() {
		Ebean.delete(this);
	}

	public BaseEntity findById(Long id) {
		return Ebean.find(this.getClass(), id);
	}

	public void logicalDelete() {
		this.setDeleted(true);
		this.update();
	}

	public void updateViaSql(BaseEntity object,
			Class<? extends BaseEntity> clazz) {
		StringBuilder sql = new StringBuilder();
		Annotation anno = clazz.getAnnotation(Table.class);

		String tableName = ((Table) anno).name();
		sql.append("update " + tableName + " set ");

		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (!formatName(field).equals("id")
					&& (field.getAnnotation(Column.class) != null || field
							.getAnnotation(ManyToOne.class) != null)) {
				try {
					field.setAccessible(true);
					Object value = field.get(object);
					if (value != null) {
						String name = null;
						if (field.getAnnotation(Column.class) != null) {
							name = field.getAnnotation(Column.class).name();
						}
						if (name != null && !("".equals(name))) {
							sql.append(name + " = " + formatValue(value) + ",");
						} else {
							sql.append(formatName(field) + " = "
									+ formatValue(value) + ",");
						}
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}

		sql.append(" where id= " + object.getId());

		String executable = sql.toString().replace(", where", " where");

		System.out.println(executable);

		SqlUpdate update = Ebean.createSqlUpdate(executable);
		update.execute();
	}

	private String formatName(Field field) {
		if (field.getType().getSuperclass() == BaseEntity.class) {
			return field.getName() + "_id";
		} else {
			String fieldName = field.getName();
			String sqlName = "";
			for (int i = 0; i < fieldName.length(); i++) {
				if (StringUtils.isAllUpperCase(String.valueOf(fieldName
						.charAt(i)))) {
					sqlName += "_" + fieldName.charAt(i);
				} else {
					sqlName += fieldName.charAt(i);
				}

			}
			return sqlName;
		}
	}

	private String formatValue(Object value) {
		if (value instanceof Integer) {
			return String.valueOf(value);
		} else if (value instanceof Float) {
			return String.valueOf(value);
		} else if (value instanceof Long) {
			return String.valueOf(value);
		} else if (value instanceof Boolean) {
			return ((Boolean) value) ? "1" : "0";
		} else if (value instanceof String) {
			return "'" + value + "'";
		} else if (value instanceof BaseEntity) {
			return String.valueOf(((BaseEntity) value).getId());
		} else if (value instanceof Date) {
			return "'" + StringUtil.formatTimestamp((Date) value) + "'";
		}
		return null;
	}

	public void setDefaultFields(BaseEntity user, boolean update) {
		if (!update) {
			this.setCreatedAt(DateUtil.today());
			this.setCreator(user);
		}
		this.setDeleted(false);
		this.setUpdater(user);
		this.setUpdatedAt(DateUtil.today());
	}

}
