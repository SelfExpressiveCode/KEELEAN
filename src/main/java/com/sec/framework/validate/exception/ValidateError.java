package com.sec.framework.validate.exception;

public class ValidateError {

	public ValidateError(String message) {
		this.message = message;
	}

	public ValidateError(String message, int value) {
		this.message = message.replace("%1", String.valueOf(value));
	}

	public ValidateError(String message, float value) {
		this.message = message.replace("%1", String.valueOf(value));
	}

	private String message;

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return this.message;
	}
}
