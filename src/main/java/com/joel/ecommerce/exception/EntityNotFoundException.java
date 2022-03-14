package com.joel.ecommerce.exception;

public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -9116338189587470989L;

	public EntityNotFoundException() {
		
		super();
	}
	
	public EntityNotFoundException(String message) {
		
		super(message);
	}
}
