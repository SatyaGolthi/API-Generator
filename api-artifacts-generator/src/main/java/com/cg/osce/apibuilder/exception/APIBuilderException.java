package com.cg.osce.apibuilder.exception;

public class APIBuilderException extends Exception {
	private static final long serialVersionUID = 1L;
	private int code;
	private String message;

	public APIBuilderException() {
		super();
	}

	public APIBuilderException(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "YamlException [code=" + code + ", status=" + message + "]";
	}

}
