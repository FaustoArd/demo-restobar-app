package com.lord.arbam.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.lord.arbam.exception.EmployeeNotSelectedException;
import com.lord.arbam.exception.ItemNotFoundException;
import com.lord.arbam.exception.NegativeNumberException;
import com.lord.arbam.exception.ProductOutOfStockException;
import com.lord.arbam.exception.RestoTableOpenException;
import com.lord.arbam.exception.ValueAlreadyExistException;
import com.lord.arbam.exception.ValueDeletionInvalidException;

@ControllerAdvice
public class HandleAppException extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ItemNotFoundException.class)
	ResponseEntity<String> handleItemNotFound(ItemNotFoundException ex){
		return new ResponseEntity<String>(ex.getMessage(),HttpStatus.EXPECTATION_FAILED);
	}
	
	@ExceptionHandler(NegativeNumberException.class)
	ResponseEntity<String> handleNegativeNumber(NegativeNumberException ex){
		return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AuthenticationException.class)
	ResponseEntity<String> handleAuthentication(AuthenticationException ex){
		return new ResponseEntity<String>(ex.getMessage(),HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(ProductOutOfStockException.class)
	ResponseEntity<String> handleOutOfStock(ProductOutOfStockException ex){
		return new ResponseEntity<String>(ex.getMessage(),HttpStatus.EXPECTATION_FAILED);
	}
	@ExceptionHandler(ValueAlreadyExistException.class)
	ResponseEntity<String> handleValueAlreadyExist(ValueAlreadyExistException ex){
		return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(EmployeeNotSelectedException.class)
	ResponseEntity<String> handleEmployeeNotSelected(EmployeeNotSelectedException ex){
		return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(RestoTableOpenException.class)
	ResponseEntity<String> handleRestoTableOpenException(RestoTableOpenException ex){
		return new ResponseEntity<String>(ex.getMessage(),HttpStatus.EXPECTATION_FAILED);
	}
	
	@ExceptionHandler(ValueDeletionInvalidException.class)
	ResponseEntity<String> handleValueDeleteInvalid(ValueDeletionInvalidException ex){
		return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
	}

}
