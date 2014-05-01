package com.sec.framework.tags;

import java.lang.reflect.Field;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;

@SuppressWarnings("serial")
public class InputLabel extends BaseInput {

	@SuppressWarnings({ "rawtypes" })
	@Override
	public int doStartTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
		StringBuffer model = new StringBuffer();
		model.append("<div style=\"width:100%;height:35px\">");
		model.append(
				"<div align=\"right\" style=\"float:left;width:100px;height:30px;padding-right:10px;\">")
				.append(label);
		try {
			Class clazz = Class.forName(fieldName.substring(0,
					fieldName.lastIndexOf(".")));
			Field field = clazz.getDeclaredField(fieldName.substring(fieldName
					.lastIndexOf(".") + 1));
			model.append("</div>");
			model.append("<div style=\"float:left;width:200px;height:30px\">")
					.append(valueToString(value, field.getType())).append("</div>");
			model.append("</div>");

			out.print(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Tag.EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {

		return Tag.EVAL_PAGE;
	}
}
