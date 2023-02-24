package app.drone.controller.exceptions;

public class MedicationNotFoundException extends RuntimeException {
	public MedicationNotFoundException(Long id) {
		super("Medication with id " + id + " does not exists");
	}
}
