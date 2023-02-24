package app.drone.controller.exceptions;

public class DroneLowBatteryException extends RuntimeException {
	public DroneLowBatteryException(Long id, int battery) {
		super("The drone with id " + id + " cannot be loaded because the battery is too low. CURRENT_BATTERY = "
				+ battery + "%");
	}
}
