package br.com.servicocampo.exception;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppWideExceptionHandler {

	@ExceptionHandler(DuplicateKeyException.class)
	public String handleDuplicateKey( DuplicateKeyException e ) {
		e.printStackTrace();
		return "error/duplicateKey";
	}
	
	@ExceptionHandler(Exception.class)
	public String handleNotFound( Exception e ) {
		e.printStackTrace();
		return "error/erro";
	}

}