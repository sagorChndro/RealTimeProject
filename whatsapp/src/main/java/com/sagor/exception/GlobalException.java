package com.sagor.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalException {

	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorDetails> UserExceptionHandler(UserException e, WebRequest req) {
		ErrorDetails err = new ErrorDetails(e.getMessage(), req.getDescription(false), LocalDateTime.now());
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MessageException.class)
	public ResponseEntity<ErrorDetails> messageExceptionHandler(MessageException mgs, WebRequest req) {
		ErrorDetails err = new ErrorDetails(mgs.getMessage(), req.getDescription(false), LocalDateTime.now());
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ChatException.class)
	public ResponseEntity<ErrorDetails> chatExceptionHandler(ChatException mgs, WebRequest req) {
		ErrorDetails err = new ErrorDetails(mgs.getMessage(), req.getDescription(false), LocalDateTime.now());
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDetails> methodArgumentNotValidException(MethodArgumentNotValidException mgs,
			WebRequest req) {
		String error = mgs.getBindingResult().getFieldError().getDefaultMessage();
		ErrorDetails err = new ErrorDetails("Validation Error", error, LocalDateTime.now());
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorDetails> noHandlerFoundException(NoHandlerFoundException e, WebRequest req) {
		ErrorDetails err = new ErrorDetails("Endpoint not found", e.getMessage(), LocalDateTime.now());
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> otherExceptionHandler(Exception e, WebRequest req) {
		ErrorDetails err = new ErrorDetails(e.getMessage(), req.getDescription(false), LocalDateTime.now());
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}

}
