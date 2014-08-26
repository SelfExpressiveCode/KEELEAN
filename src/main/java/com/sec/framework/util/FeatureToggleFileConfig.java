package com.sec.framework.util;

public class FeatureToggleFileConfig implements FeatureToggleConfig {

	public boolean isEnabled(String feature) {
		String value = (String) KeeleanPropertyPlaceHolderConfigurer
				.getContextProperty(feature);

		return value.equalsIgnoreCase("true");
	}

}
