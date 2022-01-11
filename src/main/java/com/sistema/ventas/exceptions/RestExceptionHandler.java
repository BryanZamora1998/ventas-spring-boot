package com.sistema.ventas.exceptions;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * Clase que permite manejar excepciones en toda la aplicación, no solo en un controlador individual.
 * Puede considerarlo como un interceptor de excepciones generadas por métodos anotados 
 * con RequestMapping o uno de los accesos directos
 * 
 * @author Raúl Yugcha
 *
 */
@ControllerAdvice
public class RestExceptionHandler {
	
   @Autowired
   private Environment env;
	
   @ExceptionHandler(value=UnauthorizedException.class)
   public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException e) {
		return new ResponseEntity<Object>(
				new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), false, e.getMessage(), 
						e.getData()!=null?new Object[] {e.getData()}:new Object[]{}
					), HttpStatus.BAD_REQUEST);
   }
   
   @ExceptionHandler(value=RestClientException.class)
   public ResponseEntity<Object> handleRestClientException(RestClientException e) {
		return new ResponseEntity<Object>(
				new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), false, e.getMessage(), 
						e.getData()!=null?new Object[] {e.getData()}:new Object[]{}
					), HttpStatus.BAD_REQUEST);
   }
   
	@ExceptionHandler(value=CustomExceptionHandler.class)
	public final ResponseEntity<Object> handleCustomExceptions(CustomExceptionHandler e) {
		return new ResponseEntity<Object>(
				new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), false, 
						e.getMessage()!=null?e.getMessage():HttpStatus.BAD_REQUEST.name(), 
						e.getData()!=null?new Object[] {e.getData()}:new Object[]{}
					), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value=MaxUploadSizeExceededException.class)
	public ResponseEntity<Object> handleMaxSizeException(MaxUploadSizeExceededException e) {
		return new ResponseEntity<Object>(
				new ApiErrorResponse(HttpStatus.EXPECTATION_FAILED.value(), false, 
						"El archivo supera el tamaño máximo permitido: " + env.getProperty("spring.servlet.multipart.max-file-size"),
						e.getMessage()!=null?new Object[] {e.getMessage()}:new Object[]{}
				), HttpStatus.EXPECTATION_FAILED);
	}

	@ExceptionHandler(value= Exception.class)
	public ResponseEntity<Object> handleAllExceptions(final Exception ex) {
		return new ResponseEntity<Object>(
				new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), false, 
						ex.getMessage(),
						new Object[] {ExceptionUtils.getStackTrace(ex)}
				), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
