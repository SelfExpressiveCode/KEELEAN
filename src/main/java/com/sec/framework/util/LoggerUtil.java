package com.sec.framework.util;

import org.slf4j.Logger;

public class LoggerUtil {

	public static void logException(Logger log, Exception e) {

		log.error(e.toString());
		log.error(e.getMessage());
		StackTraceElement[] stes = e.getStackTrace();
		for (StackTraceElement ste : stes) {
			log.error(ste.toString());
		}
	}

}
