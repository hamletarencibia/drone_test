package app.drone.utils;

import java.util.LinkedList;

import org.springframework.http.HttpStatus;

import jakarta.validation.ConstraintViolationException;

public class CustomErrorFactory {
	public static CustomError getCustomError(RuntimeException exception, HttpStatus status) {
		return new CustomError(status, new String[] { exception.getMessage() });
	}

	public static CustomError getCustomError(ConstraintViolationException exception, HttpStatus status) {
		CustomError error = new CustomError();
		error.setStatus(status);
		LinkedList<String> messages = new LinkedList<String>();
		exception.getConstraintViolations().forEach((violation) -> messages.add(violation.getMessage()));
		error.setMessages(messages.toArray(new String[0]));
		return error;
	}
}
