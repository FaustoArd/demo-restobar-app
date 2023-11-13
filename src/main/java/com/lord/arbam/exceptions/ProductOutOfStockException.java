package com.lord.arbam.exceptions;

public class ProductOutOfStockException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ProductOutOfStockException(String message) {
		super(message);
	}

}
