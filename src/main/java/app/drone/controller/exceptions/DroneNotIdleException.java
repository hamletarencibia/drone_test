package app.drone.controller.exceptions;

import app.drone.entities.types.DroneState;

public class DroneNotIdleException extends RuntimeException {
	public DroneNotIdleException(DroneState state) {
		super("The drone cannot be loaded because it is not currently idle. STATE = " + state.toString());
	}
}
