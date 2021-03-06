package com.whitrus.spring.tester.config.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

//TODO: refactor this and all related classes. Maybe remove this
@ControllerAdvice
public class ApiExceptionHandler
{
	@ExceptionHandler({ ConstraintViolationException.class })
	@ResponseBody
	public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request)
	{
		Map<String, Object> details = new HashMap<>();
		Map<String, List<String>> errors = new HashMap<>();

		details.put("errors", errors);

		for (ConstraintViolation<?> violation : ex.getConstraintViolations())
		{
			String path = violation.getPropertyPath().toString();

			if (!errors.containsKey(path))
			{
				errors.put(path, new ArrayList<>());
			}

			List<String> pathErrors = errors.get(path);
			pathErrors.add(violation.getMessage());
		}

		ApiErrorBuilder errorBuilder = new ApiErrorBuilder();

		errorBuilder.setStatus(HttpStatus.BAD_REQUEST);
		errorBuilder.setCode(1);
		errorBuilder.setMessage("Validation error");
		errorBuilder.setDetails(details);

		ApiError apiError = errorBuilder.build();

		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	//TODO: improve this method to better handle errors and give bettre messages
	@ExceptionHandler({ MethodArgumentNotValidException.class })
	@ResponseBody
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) throws MethodArgumentNotValidException
	{
		Map<String, Object> details = new HashMap<>();
		Map<String, List<String>> errors = new HashMap<>();

		details.put("errors", errors);

		List<ObjectError> validationErrors = ex.getBindingResult().getAllErrors();

		for (ObjectError validationError : validationErrors)
		{
			if (validationError instanceof FieldError)
			{
				FieldError fieldError = (FieldError) validationError;

				String path = fieldError.getField();

				if (!errors.containsKey(path))
				{
					errors.put(path, new ArrayList<>());
				}

				List<String> pathErrors = errors.get(path);
				pathErrors.add(fieldError.getDefaultMessage());
			}
			else if (validationError instanceof ObjectError) {
				ObjectError objectError = (ObjectError) validationError;
				
				String objectName = objectError.getObjectName();
			
				if (!errors.containsKey(objectName)) {
					errors.put(objectName, new ArrayList<>());
				}
				
				List<String> objectErrors = errors.get(objectName);
				objectErrors.add(objectError.getDefaultMessage());
			}
			else {
				throw ex;
			}
		}

		ApiErrorBuilder errorBuilder = new ApiErrorBuilder();

		errorBuilder.setStatus(HttpStatus.BAD_REQUEST);
		errorBuilder.setCode(1);
		errorBuilder.setMessage("Validation error");
		errorBuilder.setDetails(details);

		ApiError apiError = errorBuilder.build();

		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
}
