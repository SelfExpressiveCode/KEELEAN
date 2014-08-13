package com.sec.framework.tags;

@SuppressWarnings("serial")
public class InputTextArea extends InputText {

	protected int rows = 3;
	protected int cols = 10;

	@Override
	protected String getPlaceHolderText() {
		String name = formClass.getSimpleName().toLowerCase() + "."
				+ field.getName();
		String valueStr = valueToString(value, field.getType());
		;
		return "<textarea id=" + id + " name=" + name
				+ " class=\"form-control\" placeholder=\"请输入" + label
				+ "\" rows=\"" + rows + "\" cols=\"" + cols + "\">" + valueStr
				+ "</textarea>";
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

}
