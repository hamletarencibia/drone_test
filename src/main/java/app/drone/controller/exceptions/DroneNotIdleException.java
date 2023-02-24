package app.drone.controller.exceptions;

import app.drone.entities.types.DroneState;

public class DroneNotIdleException extends RuntimeException {
	public DroneNotIdleException(Long id, DroneState state) {
		super("The drone with id " + id + " cannot be loaded because it is not currently idle. STATE = "
				+ state.toString());
	}
}
