package app.drone.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import app.drone.controller.exceptions.DroneNotFoundException;

@RestControllerAdvice
public class DroneAdvice {
	@ResponseBody
	@ExceptionHandler(DroneNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String droneNotFound(DroneNotFoundException exception) {
		return exception.getMessage();
	}
}
