package com.example.umc10th.global.apiPayload.handler;

import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import com.example.umc10th.global.apiPayload.code.GeneralErrorCode;
import com.example.umc10th.global.apiPayload.exception.ProjectException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GeneralExceptionAdvice {

	@ExceptionHandler(ProjectException.class)
	public ResponseEntity<ApiResponse<Void>> handleProjectException(ProjectException exception) {
		BaseErrorCode errorCode = exception.getErrorCode();
		return ResponseEntity
			.status(errorCode.getStatus())
			.body(ApiResponse.onFailure(errorCode, null));
	}

	@ExceptionHandler({
		MissingServletRequestParameterException.class,
		MethodArgumentTypeMismatchException.class
	})
	public ResponseEntity<ApiResponse<String>> handleBadRequest(Exception exception) {
		BaseErrorCode errorCode = GeneralErrorCode.BAD_REQUEST;
		return ResponseEntity
			.status(errorCode.getStatus())
			.body(ApiResponse.onFailure(errorCode, exception.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Map<String, String>>> handleMethodArgumentNotValidException(
		MethodArgumentNotValidException exception
	) {
		Map<String, String> errors = new LinkedHashMap<>();
		exception.getBindingResult()
			.getFieldErrors()
			.forEach(error -> errors.putIfAbsent(error.getField(), error.getDefaultMessage()));

		BaseErrorCode errorCode = GeneralErrorCode.BAD_REQUEST;
		return ResponseEntity
			.status(errorCode.getStatus())
			.body(ApiResponse.onFailure(errorCode, errors));
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ApiResponse<String>> handleMethodNotAllowed(Exception exception) {
		BaseErrorCode errorCode = GeneralErrorCode.METHOD_NOT_ALLOWED;
		return ResponseEntity
			.status(errorCode.getStatus())
			.body(ApiResponse.onFailure(errorCode, exception.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<String>> handleException(Exception exception) {
		BaseErrorCode errorCode = GeneralErrorCode.INTERNAL_SERVER_ERROR;
		return ResponseEntity
			.status(errorCode.getStatus())
			.body(ApiResponse.onFailure(errorCode, exception.getMessage()));
	}
}
