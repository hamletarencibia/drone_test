package app.drone.entities;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import app.drone.entities.types.DroneModel;
import app.drone.entities.types.DroneState;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Max;

@Entity
public class Drone {
	@Id
	@GeneratedValue
	private Long id;
	@Length(max = 100, message = "The serial number cannot exceed 100 characters.")
	private String serialNumber;
	@Enumerated(EnumType.STRING)
	private DroneModel model;
	@Max(value = 500, message = "The weight limit cannot exceed 500gr.")
	private float weightLimit;
	@Max(value = 100, message = "The battery capacity cannot exceed 100%.")
	private int batteryCapacity;
	@Enumerated(EnumType.STRING)
	private DroneState state;
	@ManyToMany
	private List<Medication> medications;

	public Drone() {
	}

	public Drone(String serialNumber, DroneModel model, float weightLimit, int batteryCapacity, DroneState state,
			List<Medication> medications) {
		this.serialNumber = serialNumber;
		this.model = model;
		this.weightLimit = weightLimit;
		this.batteryCapacity = batteryCapacity;
		this.state = state;
		this.medications = medications;
	}

	public Long getId() {
		return id;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public DroneModel getModel() {
		return model;
	}

	public void setModel(DroneModel model) {
		this.model = model;
	}

	public float getWeightLimit() {
		return weightLimit;
	}

	public void setWeightLimit(float weightLimit) {
		this.weightLimit = weightLimit;
	}

	public int getBatteryCapacity() {
		return batteryCapacity;
	}

	public void setBatteryCapacity(int batteryCapacity) {
		this.batteryCapacity = batteryCapacity;
	}

	public DroneState getState() {
		return state;
	}

	public void setState(DroneState state) {
		this.state = state;
	}

	public List<Medication> getMedications() {
		return medications;
	}

	public void setMedications(List<Medication> medications) {
		this.medications = medications;
	}
}
