package app.drone.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Medication {
	@Id
	@GeneratedValue
	private Long id;
	private float weight;
	private String code;
	private String image;
	
	public Medication() {}
	
	public Medication(float weight, String code, String image) {
		this.weight = weight;
		this.code = code;
		this.image = image;
	}
	public Long getId() {
		return id;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
}
