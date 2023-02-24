package app.drone.entities;

import app.drone.entities.types.DroneModel;
import app.drone.entities.types.DroneState;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Drone {
	@Id
	@GeneratedValue
	private Long id;
	private String serialNumber;
	private DroneModel model;
	private float weightLimit;
	private int batteryCapacity;
	private DroneState state;
	
	public Drone() {}
	
	public Drone(String serialNumber, DroneModel model, float weightLimit, int batteryCapacity, DroneState state) {
		this.serialNumber = serialNumber;
		this.model = model;
		this.weightLimit = weightLimit;
		this.batteryCapacity = batteryCapacity;
		this.state = state;
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
}
