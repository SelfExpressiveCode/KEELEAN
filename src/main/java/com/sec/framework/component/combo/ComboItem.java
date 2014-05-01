package com.sec.framework.component.combo;

public class ComboItem {
	public long key;
	public String value;
	public boolean selected = false;

	public ComboItem(long key, String value) {
		this.key = key;
		this.value = value;
		this.selected = false;
	}

	public ComboItem() {
	}

	public long getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public boolean isSelected() {
		return selected;
	}

}
