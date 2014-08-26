package com.sec.framework.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;

import com.sec.framework.validate.annotation.Length;
import com.sec.framework.validate.annotation.Required;

public class InputText extends BaseInput {

	private static final long serialVersionUID = -3431993969907264894L;
	protected String type = "text";

	protected String readonly;

	protected Integer height;

	// TODO regular expression format to restrict
	protected String format;

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

			int h = height == null ? 35 : height;

			model.append("<div style='width:100%;height:" + h + "px;'>");
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

	/**
	 * 获取组件提示字符串
	 * 
	 * @return
	 */
	protected String getPlaceHolderText() {
		String name = formClass.getSimpleName().toLowerCase() + "."
				+ field.getName();
		String lengthStr = "";
		String valueStr = valueToString(value, field.getType());
		Length length = field.getAnnotation(Length.class);
		if (length != null) {
			lengthStr = " maxlength='" + length.max() + "'";
		}

		String readonlyStr = readonly != null
				&& readonly.equalsIgnoreCase("true") ? " readonly =\"readonly\" "
				: "";
		return "<input type='" + type + "' " + lengthStr + readonlyStr
				+ " value='" + valueStr + "' name='" + name + "'  id='" + id
				+ "' placeholder='请输入" + label + "'>";
	}

	@Override
	public int doEndTag() throws JspException {

		return Tag.EVAL_PAGE;
	}

	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

}
