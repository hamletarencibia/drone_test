package app.drone.controller.exceptions;

public class DroneNotFoundException extends RuntimeException {
	public DroneNotFoundException(Long id) {
		super("Drone with id " + id + " does not exists");
	}
}
