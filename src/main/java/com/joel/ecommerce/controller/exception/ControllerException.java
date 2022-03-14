package com.joel.ecommerce.controller.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.joel.ecommerce.exception.EntityNotFoundException;
import com.joel.ecommerce.utils.ErrorMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ControllerException extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handleDomainNotFoundException(EntityNotFoundException exception, WebRequest request) {
		
		log.error("Error when searching an entity ", exception);
		
		ErrorMessage message = ErrorMessage.builder()
				.path(request.getContextPath())
				.status(HttpStatus.BAD_REQUEST)
				.message(exception.getMessage())
				.build();
		
		return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
}