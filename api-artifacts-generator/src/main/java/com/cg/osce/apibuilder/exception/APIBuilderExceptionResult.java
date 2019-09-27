package com.cg.osce.apibuilder.exception;

public class APIBuilderExceptionResult {
	private int errorCode;
	private String errorStatus;

	public APIBuilderExceptionResult() {
		super();
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorStatus() {
		return errorStatus;
	}

	public APIBuilderExceptionResult(int errorCode, String errorStatus) {
		super();
		this.errorCode = errorCode;
		this.errorStatus = errorStatus;
	}

	public void setErrorStatus(String errorStatus) {
		this.errorStatus = errorStatus;
	}

}
