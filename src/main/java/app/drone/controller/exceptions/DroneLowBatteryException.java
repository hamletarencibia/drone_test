package app.drone.controller.exceptions;

public class DroneLowBatteryException extends RuntimeException {
	public DroneLowBatteryException(int battery) {
		super("The drone cannot be loading because the battery is too low. CURRENT_BATTERY = " + battery + "%");
	}
}
