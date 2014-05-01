package com.sec.framework.text.yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.yaml.snakeyaml.Yaml;

import com.sec.framework.entity.BaseEntity;
import com.sec.framework.entity.Finder;

public class Analyzer {

	private Log log = LogFactory.getLog(Analyzer.class.getSimpleName());

	public void load(String fileName) {
		Yaml yaml = new Yaml();
		File dumpfile = new File(fileName);
		InputStream input;
		try {
			input = new FileInputStream(dumpfile);
			Iterable<Object> iterable = yaml.loadAll(input);
			for (Object data : iterable) {
				try {
					BaseEntity entity = toEntity(data);
					entity.save();
				} catch (Exception e) {
					e.printStackTrace();
					log.error(e.getMessage(), e);
				}

			}
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private BaseEntity toEntity(Object data) {
		Map<String, Object> map = (Map<String, Object>) data;
		String className = (String) map.get("class");

		BaseEntity entity = null;
		Class clazz = null;
		try {
			clazz = Class.forName(className);
			entity = (BaseEntity) clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				Object value = map.get(field.getName());
				assignValue(entity, field, value);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return entity;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void assignValue(BaseEntity entity, Field field, Object value) {
		try {
			if (Modifier.isFinal(field.getModifiers())) {
				return;
			}
			Class clazzType = field.getType();
			field.setAccessible(true);
			if (clazzType.getName().equals("java.lang.String")) {
				field.set(entity, String.valueOf(value));
			} else if (clazzType.getName().equals("java.lang.Integer")) {
				field.set(entity, Integer.parseInt(null != value ? String
						.valueOf(value) : "0"));
			} else if (clazzType.getName().equals("java.util.Date")) {
				field.set(entity, value);
			} else if (clazzType.getName().equals("java.lang.Long")) {
				field.set(entity, Long.parseLong(null != value ? String
						.valueOf(value) : "0"));
			} else if (clazzType.getName().equals("java.lang.Float")) {
				field.set(entity, Float.parseFloat(null != value ? String
						.valueOf(value) : "0"));
			} else if (clazzType.getName().equals("java.lang.Boolean")) {
				field.set(entity, value);
			} else {
				List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) value;
				if (list != null) {
					for (HashMap<String, Object> map : list) {
						String className = (String) map.get("class");
						Integer id = (Integer) map.get("id");
						Class clazzs = Class.forName(className);
						Finder<BaseEntity> find = new Finder<BaseEntity>(clazzs);
						BaseEntity subEntity = find.byId(id.longValue());
						field.set(entity, subEntity);
					}
				}
			}
			field.setAccessible(false);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

	}

	// @PostConstruct
	public void init() {
		try {
			String path = Analyzer.class.getResource("/").getPath();
			String newPath = path.replace("%20", " ");
			load(newPath + "\\initial.yaml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
