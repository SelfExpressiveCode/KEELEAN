package com.sec.framework.tags;

@SuppressWarnings("serial")
public class InputTextArea extends InputText {

	protected String rows = "3";

	@Override
	protected String getPlaceHolderText() {
		String name = formClass.getSimpleName().toLowerCase() + "."
				+ field.getName();
		String valueStr = valueToString(value, field.getType());
		;
		return "<textarea id=" + id + " name=" + name
				+ " class=\"form-control\" placeholder=\"请输入" + label
				+ "\" rows=\"" + rows + "\">" + valueStr + "</textarea>";
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

}
