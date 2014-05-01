package com.sec.framework.tags;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.sec.framework.component.combo.Combo;
import com.sec.framework.component.combo.ComboItem;

@SuppressWarnings("serial")
public class SingleInputCombo extends InputText {
	protected Combo<?> combo;
	protected String id;
	protected String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Combo<?> getCombo() {
		return combo;
	}

	public void setCombo(Combo<?> combo) {
		this.combo = combo;
	}

	@Override
	protected String getPlaceHolderText() {
		StringBuilder result = new StringBuilder();
		result.append("<select name=\"" + name
				+ "\" style='width:120px;' id=\"" + id + "\">");
		if (null != combo && CollectionUtils.isNotEmpty(combo.getItems())) {
			List<ComboItem> items = combo.getItems();
			for (ComboItem item : items) {
				result.append("<option value=\"").append(item.key)
						.append("\" ");
				if (item.selected) {
					result.append(" selected=\"selected\"");
				}
				result.append(">").append(item.value).append("</option>");
			}
		}
		result.append("</select>");
		return result.toString();
	}
}
