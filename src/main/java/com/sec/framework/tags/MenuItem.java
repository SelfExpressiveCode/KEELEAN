package com.sec.framework.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

@SuppressWarnings("serial")
public class MenuItem extends BaseInput {

	public String url;

	@Override
	public int doEndTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
		StringBuffer model = new StringBuffer();
		model.append("<div id=\"")
				.append(id)
				.append("\" class=\"divClassUrl\" style=\"background-color: rgb(255, 255, 255); font-size:12px;cursor:hand; padding-left:15px; padding-bottom:0px;\">")
				.append("<img src=\"../images/menuSelect.gif\">" + label
						+ "</div>");
		model.append("<input type=\"hidden\" id = \"menu_item_" + id
				+ "_title\" value=\"" + label + "\">");
		model.append("<input type=\"hidden\" id = \"menu_item_" + id
				+ "_url\" value=\"" + url + "\">");

		try {
			out.print(model);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {
		return super.doStartTag();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
