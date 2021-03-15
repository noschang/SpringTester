package com.whitrus.spring.tester.config.server;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiErrorBuilder
{
	private HttpStatus status = null;
	private int code = 0;
	private String message;
	private Map<String, Object> details = null;

	public ApiErrorBuilder()
	{
	}

	public ApiErrorBuilder setStatus(int status)
	{
		this.status = HttpStatus.valueOf(status);
		return this;
	}

	public ApiErrorBuilder setStatus(HttpStatus status)
	{
		this.status = status;
		return this;
	}

	public ApiErrorBuilder setCode(int code)
	{
		this.code = code;
		return this;
	}

	public ApiErrorBuilder setMessage(String message)
	{
		this.message = message;
		return this;
	}

	public void setDetails(Map<String, Object> details)
	{
		this.details = details;
	}

	public ApiError build()
	{
		if (status == null)
		{
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		if (message == null)
		{
			message = "An unhandled error ocurred";
		}

		if (details == null)
		{
			return new ApiError(status, code, message);
		}

		return new ApiError(status, code, message, details);
	}
	
	public static ResponseEntity<Object> resourceNotFoundError(String resourceName, Long resourceId)
	{
		ApiErrorBuilder errorBuilder = new ApiErrorBuilder();

		errorBuilder.setCode(2);
		errorBuilder.setMessage(String.format("The resource of type '%s' with id %d doesn't exists", resourceName, resourceId));
		errorBuilder.setStatus(HttpStatus.NOT_FOUND);

		return new ResponseEntity<Object>(errorBuilder.build(), HttpStatus.NOT_FOUND);
	}
	
	public static ResponseEntity<Object> resourceNotFoundError(String resourceName, String resourceId)
	{
		ApiErrorBuilder errorBuilder = new ApiErrorBuilder();

		errorBuilder.setCode(2);
		errorBuilder.setMessage(String.format("The resource of type '%s' with id %s doesn't exists", resourceName, resourceId));
		errorBuilder.setStatus(HttpStatus.NOT_FOUND);

		return new ResponseEntity<Object>(errorBuilder.build(), HttpStatus.NOT_FOUND);
	}
	public static ResponseEntity<Object> notAcceptableError(String message){
		ApiErrorBuilder errorBuilder = new ApiErrorBuilder();

		errorBuilder.setCode(2);
		errorBuilder.setMessage(message);
		errorBuilder.setStatus(HttpStatus.NOT_ACCEPTABLE);

		return new ResponseEntity<Object>(errorBuilder.build(), HttpStatus.NOT_ACCEPTABLE);
	}
}