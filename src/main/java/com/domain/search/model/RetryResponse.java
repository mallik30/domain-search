package com.domain.search.model;

public class RetryResponse {

	private String code;
	private String message;
	private Integer retryAfterSec;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getRetryAfterSec() {
		return retryAfterSec;
	}

	public void setRetryAfterSec(Integer retryAfterSec) {
		this.retryAfterSec = retryAfterSec;
	}

	@Override
	public String toString() {
		return "RetryResponse [code=" + code + ", message=" + message + ", retryAfterSec=" + retryAfterSec + "]";
	}

}
