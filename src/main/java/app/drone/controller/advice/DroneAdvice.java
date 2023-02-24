package app.drone.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import app.drone.controller.exceptions.DroneLowBatteryException;
import app.drone.controller.exceptions.DroneNotFoundException;
import app.drone.controller.exceptions.DroneNotIdleException;
import app.drone.controller.exceptions.DroneWeightLimitExcededException;

@RestControllerAdvice
public class DroneAdvice {
	@ResponseBody
	@ExceptionHandler(DroneNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String droneNotFound(DroneNotFoundException exception) {
		return exception.getMessage();
	}

	@ResponseBody
	@ExceptionHandler(DroneNotIdleException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	String droneNotidle(DroneNotIdleException exception) {
		return exception.getMessage();
	}

	@ResponseBody
	@ExceptionHandler(DroneWeightLimitExcededException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	String droneWeightLimitExceded(DroneWeightLimitExcededException exception) {
		return exception.getMessage();
	}

	@ResponseBody
	@ExceptionHandler(DroneLowBatteryException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	String droneLowBattery(DroneLowBatteryException exception) {
		return exception.getMessage();
	}
}
