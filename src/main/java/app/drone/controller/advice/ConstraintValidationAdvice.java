package app.drone.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import app.drone.utils.CustomError;
import app.drone.utils.CustomErrorFactory;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class ConstraintValidationAdvice {
	@ExceptionHandler(ConstraintViolationException.class)
	ResponseEntity<CustomError> constraintValidation(ConstraintViolationException exception) {
		return new ResponseEntity<>(CustomErrorFactory.getCustomError(exception, HttpStatus.BAD_REQUEST),
				HttpStatus.BAD_REQUEST);
	}
}
