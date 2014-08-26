package com.sec.framework.util;

public class FeatureToggleConfigFactory {

	public static FeatureToggleConfig create(Class clazz) {
		if (clazz.equals(Object.class)) {
			return new FeatureToggleFileConfig();
		} else {
			return new FeatureToggleDatabaseConfig(clazz);
		}
	}

}
