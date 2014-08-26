package com.sec.framework.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.sec.framework.util.FeatureToggleConfig;
import com.sec.framework.util.FeatureToggleConfigFactory;
import com.sec.framework.util.StringUtil;

public class FeatureToggle extends TagSupport {

	private String feature;

	private String source;

	@Override
	public int doEndTag() throws JspException {

		return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {

		// String value = (String) FeatureTogglePropertyPlaceHolderConfigurer
		// .getContextProperty(feature);
		//
		// if (!value.equalsIgnoreCase("true")) {
		// return SKIP_BODY;
		// }
		String className = source;

		Class clazz = Object.class;

		if (className != null && !StringUtil.isNullOrEmpty(className)) {
			try {
				clazz = Class.forName(className);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		FeatureToggleConfig config = FeatureToggleConfigFactory.create(clazz);

		if (!config.isEnabled(feature)) {
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
