package com.sec.framework.tags;

public class InputDate extends InputText {

	private static final long serialVersionUID = 1L;

	@Override
	protected String getPlaceHolderText() {
		String name = formClass.getSimpleName().toLowerCase() + "."
				+ field.getName();
		String valueStr = valueToString(value, field.getType());

		String text = "<input type=\"" + type + "\" " + " maxlength=\"" + 10
				+ "\"" + " value=\"" + valueStr + "\"  name=\"" + name
				+ "\" class=\"form-control Wdate\" id=\"" + id
				+ "\" placeholder='请输入" + label + "'>";
		System.out.println("text= " + text);
		return text;
	}
}
