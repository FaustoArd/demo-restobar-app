package com.lord.arbam.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.lord.arbam.exceptions.ItemNotFoundException;
import com.lord.arbam.exceptions.NegativeNumberException;

@ControllerAdvice
public class HandleAppException extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ItemNotFoundException.class)
	ResponseEntity<String> handleItemNotFound(ItemNotFoundException ex){
		return new ResponseEntity<String>(ex.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NegativeNumberException.class)
	ResponseEntity<String> handleNegativeNumber(NegativeNumberException ex){
		return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
	}

}
