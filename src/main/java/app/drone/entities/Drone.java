package app.drone.entities;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import app.drone.entities.types.DroneModel;
import app.drone.entities.types.DroneState;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class Drone {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull(message = "You must provide a serial number.")
	@Length(max = 100, message = "The serial number cannot exceed 100 characters.")
	private String serialNumber;
	@NotNull(message = "You must provide a model.")
	@Enumerated(EnumType.STRING)
	private DroneModel model;
	@Min(value = 1, message = "The weight limit must be greater than 0.")
	@Max(value = 500, message = "The weight limit cannot exceed 500gr.")
	private float weightLimit;
	@Min(value = 0, message = "The battery capacity cannot be negative .")
	@Max(value = 100, message = "The battery capacity cannot exceed 100%.")
	private int batteryCapacity;
	@NotNull(message = "You must provide a state.")
	@Enumerated(EnumType.STRING)
	private DroneState state;
	@ManyToMany
	private List<Medication> medications;

	public Drone() {
	}

	public Drone(Long id,
			@NotNull(message = "You must provide a serial number.") @Length(max = 100, message = "The serial number cannot exceed 100 characters.") String serialNumber,
			@NotNull(message = "You must provide a model.") DroneModel model,
			@Min(value = 1, message = "The weight limit must be greater than 0.") @Max(value = 500, message = "The weight limit cannot exceed 500gr.") float weightLimit,
			@Min(value = 0, message = "The battery capacity cannot be negative .") @Max(value = 100, message = "The battery capacity cannot exceed 100%.") int batteryCapacity,
			@NotNull(message = "You must provide a state.") DroneState state) {
		super();
		this.id = id;
		this.serialNumber = serialNumber;
		this.model = model;
		this.weightLimit = weightLimit;
		this.batteryCapacity = batteryCapacity;
		this.state = state;
		this.medications = new ArrayList<Medication>();
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

	@Transient
	@AssertTrue(message = "The drone cannot start loading with battery bellow 25%.")
	public boolean isStateAllowed() {
		return (batteryCapacity >= 25 && state == DroneState.LOADING) || state != DroneState.LOADING;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Drone)) {
			return false;
		}
		if ((this.id != ((Drone) object).getId())) {
			return false;
		}
		if (!(this.serialNumber.equals(((Drone) object).getSerialNumber()))) {
			return false;
		}
		if (!(this.model.equals(((Drone) object).getModel()))) {
			return false;
		}
		if (this.weightLimit != ((Drone) object).getWeightLimit()) {
			return false;
		}
		if (this.batteryCapacity != ((Drone) object).getBatteryCapacity()) {
			return false;
		}
		if (!(this.state.equals(((Drone) object).getState()))) {
			return false;
		}
		return true;
	}
}
