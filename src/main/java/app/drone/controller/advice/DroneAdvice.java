package app.drone.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import app.drone.controller.exceptions.DroneLowBatteryException;
import app.drone.controller.exceptions.DroneNotFoundException;
import app.drone.controller.exceptions.DroneNotIdleException;
import app.drone.controller.exceptions.DroneWeightLimitExcededException;
import app.drone.utils.CustomError;
import app.drone.utils.CustomErrorFactory;

@RestControllerAdvice
public class DroneAdvice {
	@ExceptionHandler(DroneNotFoundException.class)
	ResponseEntity<CustomError> droneNotFound(DroneNotFoundException exception) {
		return new ResponseEntity<>(CustomErrorFactory.getCustomError(exception, HttpStatus.NOT_FOUND),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DroneNotIdleException.class)
	ResponseEntity<CustomError> droneNotidle(DroneNotIdleException exception) {
		return new ResponseEntity<>(CustomErrorFactory.getCustomError(exception, HttpStatus.CONFLICT),
				HttpStatus.CONFLICT);
	}

	@ExceptionHandler(DroneWeightLimitExcededException.class)
	ResponseEntity<CustomError> droneWeightLimitExceded(DroneWeightLimitExcededException exception) {
		return new ResponseEntity<>(CustomErrorFactory.getCustomError(exception, HttpStatus.CONFLICT),
				HttpStatus.CONFLICT);
	}

	@ExceptionHandler(DroneLowBatteryException.class)
	ResponseEntity<CustomError> droneLowBattery(DroneLowBatteryException exception) {
		return new ResponseEntity<>(CustomErrorFactory.getCustomError(exception, HttpStatus.CONFLICT),
				HttpStatus.CONFLICT);
	}
}
