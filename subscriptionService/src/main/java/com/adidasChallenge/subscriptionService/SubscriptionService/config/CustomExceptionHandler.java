package com.adidasChallenge.subscriptionService.SubscriptionService.config;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

/**
 * Handler for rest controller. Each method has the handler exception in annotation ExceptionHandler
 * @author ernesto.romero
 *
 */

@RestControllerAdvice
public class CustomExceptionHandler  {
	
	private final static Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

	
	@ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Object> exceptionHandlerInvalidFormatException(InvalidFormatException ex, HandlerMethod handlerMethod, HttpServletRequest request) throws JsonProcessingException {
        
        Map<String, Object> error = new LinkedHashMap<>();
        
        error.put("timestamp", new Date());
        error.put("status", HttpStatus.BAD_REQUEST.value());
        error.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        error.put("messages", "Invalid Value: " + ex.getValue());
        error.put("path", request.getServletPath());  
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON);

        return new ResponseEntity<Object>(error, headers, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> exceptionHandlerMethodArgumentNotValidException(MethodArgumentNotValidException ex, 
    		HandlerMethod handlerMethod, HttpServletRequest request) throws JsonProcessingException {
        
		Map<String, Object> error = new LinkedHashMap<>();
        
		List<String> messages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());
        
        error.put("timestamp", new Date());
        error.put("status", HttpStatus.BAD_REQUEST.value());
        error.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        error.put("messages", messages);
        error.put("path", request.getServletPath()); 
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON);

        return new ResponseEntity<Object>(error, headers, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> exceptionHandlerHttpMessageNotReadableException(HttpMessageNotReadableException ex, 
    		HandlerMethod handlerMethod, HttpServletRequest request) throws JsonProcessingException {
        
		Map<String, Object> error = new LinkedHashMap<>();		
        
		error.put("timestamp", new Date());
        error.put("status", HttpStatus.BAD_REQUEST.value());
        error.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        error.put("messages", "Malformed message");
        error.put("path", request.getServletPath());  
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON);

        return new ResponseEntity<Object>(error, headers, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> exceptionHandlerNotFoundException(ResponseStatusException ex, 
    		HandlerMethod handlerMethod, HttpServletRequest request) throws JsonProcessingException {
        
		Map<String, Object> error = new LinkedHashMap<>();		
        
		error.put("timestamp", new Date());
		error.put("status", HttpStatus.NOT_FOUND.value());
        error.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        error.put("messages", ex.getLocalizedMessage());
        error.put("path", request.getServletPath());  
                        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON);  
        
        return new ResponseEntity<Object>(error, headers, HttpStatus.BAD_REQUEST);
    }
	
	
	
	
}