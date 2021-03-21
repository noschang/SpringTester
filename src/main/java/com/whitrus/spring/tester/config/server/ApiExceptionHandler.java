package com.whitrus.spring.tester.config.server;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
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
	// TODO: handle data integrity violation exception
//	private static final Pattern DB_CONSTRAINT_PATTERN = Pattern.compile("(?<name>(?<type>(NN|UK|PK|CHK))_#(\\d|\\w)+(_(\\d|\\w)+)*_(\\$(\\d|\\w)+)(_\\$(\\d|\\w)+)*)");
//	
//	public static void main(String[] args) {
//		String error = "ORA-00001: restrição exclusiva (MAIN_DEV.UK_#USER_$LOGIN) violada";
//		
//		var matcher = DB_CONSTRAINT_PATTERN.matcher(error);
//		
//		if (matcher.find()) {
//			var type = matcher.group("type");
//			var name = matcher.group("name");
//			var message = new StringBuilder();
//			
//			switch (type) {
//			case "NN" -> message.append("NULL constraint violated: %s".formatted(name));
//			case "UK" -> message.append("UNIQUE constraint violated: %s".formatted(name));
//			case "PK" -> message.append("PRIMARY KEY constraint violated: %s".formatted(name));
//			case "CHK" -> message.append("CHECK constraint violated: %s".formatted(name));
//			default -> message.append("");
//			}
//			
//			System.out.println(message.toString());
//		}
//		else {
//			System.out.println("SHIT!");
//		}
//	}
//	
//	
//	@ExceptionHandler({ DataIntegrityViolationException.class })
//	@ResponseBody
//	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) throws DataIntegrityViolationException
//	{
//		Throwable cause = ex.getMostSpecificCause();
//		
//		if (cause instanceof SQLIntegrityConstraintViolationException) {
//			var sqlException = (SQLIntegrityConstraintViolationException) cause;
//			var message = sqlException.getMessage();
//			
//			
//		}
//		
//		return null;
//	}
	
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

	//TODO: improve this method to better handle errors and give better messages
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
