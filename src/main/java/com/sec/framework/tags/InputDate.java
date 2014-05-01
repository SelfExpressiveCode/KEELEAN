package com.sec.framework.tags;

import com.sec.framework.validate.annotation.Length;

public class InputDate extends InputText {

	private static final long serialVersionUID = 1L;

	@Override
	protected String getPlaceHolderText() {
		String name = formClass.getSimpleName().toLowerCase() + "."
				+ field.getName();
		String lengthStr = "";
		String valueStr = valueToString(value, field.getType());
		Length length = field.getAnnotation(Length.class);
		if (length != null) {
			lengthStr = " maxlength=\"" + length.max() + "\"";
		}
		return "<input type=\""
				+ type
				+ "\" "
				+ lengthStr
				+ " value=\""
				+ valueStr
				+ "\"  name=\""
				+ name
				+ "\" class=\"form-control Wdate\" id=\""
				+ id
				+ "\" onclick=\"WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})\" >";
	}
}
