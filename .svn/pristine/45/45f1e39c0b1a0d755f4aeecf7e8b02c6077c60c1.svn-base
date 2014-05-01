package com.sec.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtil {
    protected static final Logger logger = LoggerFactory
            .getLogger(CommonUtil.class);

    public static void printExceptionToLog(Class clazz, String method,
            Exception e) {
        StackTraceElement[] message = e.getStackTrace();
        String className = clazz.getName();
        StringBuffer error = new StringBuffer();
        for (StackTraceElement stackTraceElement : message) {
            error.append(stackTraceElement.toString());
        }
        logger.error(className + "." + method, error);
    }
}
