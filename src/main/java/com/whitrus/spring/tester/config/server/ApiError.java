package com.whitrus.spring.tester.config.server;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

public class ApiError
{
	private final HttpStatus status;
	private final int errorCode;
	private final String message;

	private Map<String, Object> details = null;

	public ApiError(HttpStatus status, int errorCode, String message)
	{
		Assert.notNull(status, "HttpStatus can't be null");
		Assert.hasText(message, "The error message can't be null");

		this.status = status;
		this.errorCode = errorCode;
		this.message = message;
	}

	public ApiError(HttpStatus status, int errorCode, String message, Map<String, Object> details)
	{
		Assert.notNull(status, "HttpStatus can't be null");
		Assert.hasText(message, "The error message can't be null");

		this.status = status;
		this.errorCode = errorCode;
		this.message = message;
		this.details = details;
	}

	public HttpStatus getStatus()
	{
		return status;
	}

	public int getErrorCode()
	{
		return errorCode;
	}

	public String getMessage()
	{
		return message;
	}
	
	public int getStatusCode()
	{
		return status.value();
	}

	public Map<String, Object> getDetails()
	{
		return details;
	}

	@Override
	public String toString()
	{
		return new StringBuilder().append(getStatus().value()).append(" (").append(getStatus().getReasonPhrase()).append(" )").toString();
	}
}