package com.sec.framework.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;

import com.sec.framework.validate.annotation.Required;

public class InputCheckBox extends BaseInput {

	private static final long serialVersionUID = -2500389128192016358L;

	@Override
	public int doStartTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
		StringBuffer model = new StringBuffer();
		try {
			if (fieldName != null) {
				formClass = Class.forName(fieldName.substring(0,
						fieldName.lastIndexOf(".")));
				field = formClass.getDeclaredField(fieldName
						.substring(fieldName.lastIndexOf(".") + 1));
				id = field.getName();
			}
			String requiredMark = "";

			if (form != null) {
				value = field.get(form);
			}

			boolean hasError = form != null
					&& form.errors.get(field.getName()) != null;

			String placeholderText = getPlaceHolderText();

			if (field != null) {
				if (field.getAnnotation(Required.class) != null) {
					requiredMark = "<font color=red>*</font>";
				}
			}

			model.append("<div style='width:100%;height:35px;'>");
			model.append("<div style='float:left;width:120px;'>");
			model.append("<label for='" + id + "'>" + label + requiredMark
					+ "</label>");
			model.append("</div>");

			model.append("<div style='float:left;width:200px;'>");
			model.append(placeholderText);
			model.append("</div>");

			if (hasError) {
				model.append("<div style='float:left;width:200px;'>");
				model.append("<label style='color:red'>"
						+ form.errors.get(field.getName()).error.getMessage()
						+ "</label>");
				model.append("</div>");
			}
			model.append("</div>");

			out.print(model);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return Tag.EVAL_BODY_INCLUDE;
	}

	protected String getPlaceHolderText() {
		String name = formClass.getSimpleName().toLowerCase() + "."
				+ field.getName();

		String checked = value == null ? "" : (Boolean) value ? "checked" : "";
		return "<input type='checkbox' " + checked + " name='" + name
				+ "'  id='" + id + "'>";
	}

	@Override
	public int doEndTag() throws JspException {

		return Tag.EVAL_PAGE;
	}

}
