package com.hasan.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<?> handleCustomerNotFound(CustomerNotFoundException ex) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(Map.of("timestamp", LocalDateTime.now(), "message", ex.getMessage()));
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<?> handleBusiness(BusinessException ex) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(Map.of("timestamp", LocalDateTime.now(), "message", ex.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception ex) {

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(Map.of("timestamp", LocalDateTime.now(), "message", ex.getMessage()));
	}
}