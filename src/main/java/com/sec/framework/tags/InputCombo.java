package com.sec.framework.tags;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.sec.framework.component.combo.Combo;
import com.sec.framework.component.combo.ComboItem;

@SuppressWarnings("serial")
public class InputCombo extends InputText {
	protected Combo combo;

	public Combo getCombo() {
		return combo;
	}

	public void setCombo(Combo combo) {
		this.combo = combo;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String getPlaceHolderText() {

		String name = formClass.getSimpleName().toLowerCase() + "."
				+ field.getName();

		StringBuilder result = new StringBuilder();
		result.append("<select name='" + name + "' style='width:120px;' id='"
				+ id + "'>");
		if (null != combo && CollectionUtils.isNotEmpty(combo.getItems())) {
			List<ComboItem> items = combo.getItems();
			for (ComboItem item : items) {
				result.append("<option value='").append(item.key).append("' ");
				if (item.selected) {
					result.append(" selected='selected'");
				}
				result.append(">").append(item.value).append("</option>");
			}
		}
		result.append("</select>");
		return result.toString();
	}

}
