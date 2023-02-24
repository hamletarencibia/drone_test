package app.drone.controller.exceptions;

public class DroneWeightLimitExcededException extends RuntimeException {
	public DroneWeightLimitExcededException(Long id, float weightLimit, float medicationWeight) {
		super("The drone with id " + id + " cannot be loaded because it exceded its maximum capacity. MAX_CAPACITY = "
				+ weightLimit + ". MEDICATION_WEIGHT = " + medicationWeight);
	}
}
