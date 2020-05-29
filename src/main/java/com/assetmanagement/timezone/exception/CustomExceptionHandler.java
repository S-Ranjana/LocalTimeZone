package com.assetmanagement.timezone.exception;

import java.io.IOException;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	/**
	 * @param DeviceNotFoundException
	 * @return ResponseEntity
	 */
	@ExceptionHandler(value = DeviceNotFoundException.class)
	public ResponseEntity<Object> deviceNotFoundException(DeviceNotFoundException exception) {
		return new ResponseEntity<Object>("Device Not found", HttpStatus.BAD_REQUEST);
	}

	/**
	 * @param ConstraintViolationException
	 * @return ResponseEntity
	 */
	@ExceptionHandler(value = ConstraintViolationException.class)
	public ResponseEntity<Object> constraintViolationException(ConstraintViolationException exception) {
		return new ResponseEntity<Object>("Invalid Device Id", HttpStatus.BAD_REQUEST);
	}

	/**
	 * @param TimeZoneNotFoundException
	 * @return ResponseEntity
	 */
	@ExceptionHandler(value = TimeZoneNotFoundException.class)
	public ResponseEntity<Object> timeZoneNotFoundException(TimeZoneNotFoundException exception) {
		return new ResponseEntity<Object>("Time Zone Not found", HttpStatus.BAD_REQUEST);
	}

	/**
	 * @param IOException
	 * @return ResponseEntity
	 */
	@ExceptionHandler(value = IOException.class)
	public ResponseEntity<Object> ioException(IOException exception) {
		return new ResponseEntity<Object>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * @param InterruptedException
	 * @return ResponseEntity
	 */
	@ExceptionHandler(value = InterruptedException.class)
	public ResponseEntity<Object> interruptedException(InterruptedException exception) {
		return new ResponseEntity<Object>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
