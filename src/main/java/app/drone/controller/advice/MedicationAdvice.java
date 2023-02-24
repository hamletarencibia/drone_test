package app.drone.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import app.drone.controller.exceptions.MedicationNotFoundException;

@RestControllerAdvice
public class MedicationAdvice {
	@ResponseBody
	@ExceptionHandler(MedicationNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String medicationNotFound(MedicationNotFoundException exception) {
		return exception.getMessage();
	}
}
