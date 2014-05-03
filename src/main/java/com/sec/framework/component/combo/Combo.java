package com.sec.framework.component.combo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.sec.framework.component.combo.annotation.ComboId;
import com.sec.framework.component.combo.annotation.ComboLabel;
import com.sec.framework.component.combo.annotation.ParentId;
import com.sec.framework.entity.BaseEntity;
import com.sec.framework.entity.Finder;

public class Combo<T extends BaseEntity> {
	private List<ComboItem> items;
	Class<T> clazz;

	public Combo() {

	}

	public Combo(Class<T> clazz) {
		this.clazz = clazz;
	}

	public void selectedData(Long key) {
		for (ComboItem item : items) {
			if (item.key == key.longValue()) {
				item.selected = true;
			}
		}
	}

	public void load(Long parentId) {
		Finder<T> find = new Finder<T>(clazz);
		Field keyField = findField(clazz, ComboId.class);
		Field valueField = findField(clazz, ComboLabel.class);
		Field parentField = findField(clazz, ParentId.class);
		if (parentField == null) {
			List<T> entities = find.alive();
			toCombo(entities, keyField, valueField);
		} else {
			List<T> entities = find.where()
					.eq(parentField.getName() + "_id", parentId)
					.eq("deleted", "false").findList();
			toCombo(entities, keyField, valueField);
		}
	}

	public void load() {
		Finder<T> find = new Finder<T>(clazz);
		Field keyField = findField(clazz, ComboId.class);
		Field valueField = findField(clazz, ComboLabel.class);
		List<T> entities = find.alive();
		toCombo(entities, keyField, valueField);
	}

	public void loadList(List<T> entities) {
		Field keyField = findField(clazz, ComboId.class);
		Field valueField = findField(clazz, ComboLabel.class);
		toCombo(entities, keyField, valueField);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Field findField(Class<? extends BaseEntity> clazz, Class anno) {
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (field.getAnnotation(anno) != null) {
				return field;
			}
		}
		return null;
	}

	public void toCombo(List<? extends BaseEntity> entities, Field keyField,
			Field valueField) {
		items = new ArrayList<ComboItem>();
		try {
			for (int i = 0; i < entities.size(); i++) {
				ComboItem item = new ComboItem();
				item.key = (Long) keyField.get(entities.get(i));
				item.value = (String) valueField.get(entities.get(i));
				items.add(item);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void toCombo(List<? extends BaseEntity> entities, String key,
			String value) {
		if (CollectionUtils.isEmpty(entities)) {
			return;
		}
		items = new ArrayList<ComboItem>();
		try {
			Field keyField = entities.get(0).getClass().getDeclaredField(key);
			Field valueField = entities.get(0).getClass()
					.getDeclaredField(value);
			for (int i = 0; i < entities.size(); i++) {
				ComboItem item = new ComboItem();
				item.key = Long.valueOf(String.valueOf(keyField.get(entities
						.get(i))));
				item.value = (String) valueField.get(entities.get(i));
				items.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<ComboItem> getItems() {
		return items;
	}

	public void setItems(List<ComboItem> items) {
		this.items = items;
	}

}
