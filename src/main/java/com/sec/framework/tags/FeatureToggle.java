package com.sec.framework.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.sec.framework.util.FeatureTogglePropertyPlaceHolderConfigurer;

public class FeatureToggle extends TagSupport {

	private String feature;

	@Override
	public int doEndTag() throws JspException {

		return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {

		String value = (String) FeatureTogglePropertyPlaceHolderConfigurer
				.getContextProperty(feature);

		if (!value.equalsIgnoreCase("true")) {
			return SKIP_BODY;
		}

		return EVAL_BODY_INCLUDE;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

}
