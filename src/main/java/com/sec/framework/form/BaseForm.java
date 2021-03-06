package com.sec.framework.form;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.sec.framework.entity.BaseEntity;
import com.sec.framework.entity.Finder;
import com.sec.framework.util.StringUtil;
import com.sec.framework.validate.Validatee;
import com.sec.framework.validate.exception.ValidationError;

public class BaseForm implements Validatee {

	public Map<String, ValidationError> errors = new HashMap<String, ValidationError>();

	public BaseEntity toEntity() {
		return null;
	}

	public void putError(String field, ValidationError validationError) {
		errors.put(field, validationError);
	}

	public void clearError() {
		errors.clear();
	}

	public ValidationError getError(String field) {
		return errors.get(field);
	}

	public boolean hasError() {
		return !errors.isEmpty();
	}

	@SuppressWarnings("rawtypes")
	public BaseEntity toEntity(BaseForm form, Class entityClazz) {
		try {
			BaseEntity entity = (BaseEntity) entityClazz.newInstance();
			Field[] fields = form.getClass().getDeclaredFields();
			Field[] entityFields = entity.getClass().getDeclaredFields();
			for (Field field : fields) {
				for (Field entityField : entityFields) {
					if (field.getName().equals(entityField.getName())) {
						entityField.setAccessible(true);
						// TODO primitive and Java class, int-> Integer
						if (!field.getType().equals(entityField.getType())) {
							if (field.get(form) == null) {
								continue;
							}

							System.out
									.println("field.type= " + field.getType());
							System.out.println("entityField.type= "
									+ entityField.getType());

							if (field.getType().getName()
									.equals("java.lang.String")
									&& entityField.getType().getName()
											.equals("java.util.Date")) {
								field.setAccessible(true);
								try {
									entityField.set(entity,
											StringUtil.parseDate((String) field
													.get(form)));
								} catch (ParseException e) {
									e.printStackTrace();
								}
							} else if (field
									.getType()
									.getName()
									.equals("org.springframework.web.multipart.MultipartFile")
									&& entityField.getType().getName()
											.equals("java.lang.String")) {

								entityField.set(entity, ((MultipartFile) field
										.get(form)).getOriginalFilename());

							} else {
								entityField.set(
										entity,
										getSubEntity(entityField.getType(),
												Long.parseLong(String
														.valueOf(field
																.get(form)))));
							}
						} else {
							if (field.getType().equals("java.lang.Boolean")) {
								field.setAccessible(true);
								entityField.set(entity, field.get(form));
							} else {
								field.setAccessible(true);
								entityField.set(entity, field.get(form));
							}
						}
					}
				}
			}
			return entity;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object getSubEntity(Class clazz, Long id) {
		Finder<? extends BaseEntity> find = new Finder<BaseEntity>(clazz);
		return find.where().eq("id", id).findUnique();
	}

	public Map<String, ValidationError> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, ValidationError> errors) {
		this.errors = errors;
	}

}
