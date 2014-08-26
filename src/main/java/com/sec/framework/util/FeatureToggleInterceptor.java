package com.sec.framework.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.sec.framework.controller.FeatureToggle;

public class FeatureToggleInterceptor implements HandlerInterceptor {

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		HandlerMethod method = (HandlerMethod) handler;

		FeatureToggle featureToggle = method.getMethod().getDeclaringClass()
				.getAnnotation(FeatureToggle.class);

		String path = request.getContextPath();
		if (featureToggle != null) {

			FeatureToggleConfig config = FeatureToggleConfigFactory
					.create(featureToggle.source());

			if (!config.isEnabled(featureToggle.value())) {
				// TODO throw a 404 exception
				response.sendRedirect(path + "/not_found");
				return false;
			}
		}

		featureToggle = method.getMethodAnnotation(FeatureToggle.class);

		if (featureToggle != null) {

			FeatureToggleConfig config = FeatureToggleConfigFactory
					.create(featureToggle.source());
			if (!config.isEnabled(featureToggle.value())) {
				// TODO throw a 404 exception
				response.sendRedirect(path + "/not_found");
				return false;
			}
		}

		return true;
	}
}
