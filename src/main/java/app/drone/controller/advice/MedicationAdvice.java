package app.drone.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import app.drone.controller.exceptions.MedicationNotFoundException;
import app.drone.utils.CustomError;
import app.drone.utils.CustomErrorFactory;

@RestControllerAdvice
public class MedicationAdvice {
	@ExceptionHandler(MedicationNotFoundException.class)
	ResponseEntity<CustomError> medicationNotFound(MedicationNotFoundException exception) {
		return new ResponseEntity<>(CustomErrorFactory.getCustomError(exception, HttpStatus.NOT_FOUND),
				HttpStatus.NOT_FOUND);
	}
}
