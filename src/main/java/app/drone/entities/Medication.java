package app.drone.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
public class Medication {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull(message = "You must provide a name.")
	@Pattern(regexp = "^([0-9A-Za-z\\-_]+)$", message = "Invalid name. Allowed only letters, numbers, '-', '_'.")
	private String name;
	@Min(value = 1, message = "The weight must be greater than 0.")
	private float weight;
	@NotNull(message = "You must provide a code.")
	@Pattern(regexp = "^([0-9A-Z_]+)$", message = "Invalid code. Allowed only upper case letters, underscore and numbers.")
	private String code;
	@NotNull(message = "You must provide an image.")
	private String image;

	public Medication() {
	}

	public Medication(Long id,
			@NotNull(message = "You must provide a name.") @Pattern(regexp = "^([0-9A-Za-z\\-_]+)$", message = "Invalid name. Allowed only letters, numbers, '-', '_'.") String name,
			@Min(value = 1, message = "The weight must be greater than 0.") float weight,
			@NotNull(message = "You must provide a code.") @Pattern(regexp = "^([0-9A-Z_]+)$", message = "Invalid code. Allowed only upper case letters, underscore and numbers.") String code,
			@NotNull(message = "You must provide an image.") String image) {
		super();
		this.id = id;
		this.name = name;
		this.weight = weight;
		this.code = code;
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Long getId() {
		return id;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Medication)) {
			return false;
		}
		if ((this.id != ((Medication) object).getId())) {
			return false;
		}
		if (!(this.name.equals(((Medication) object).getName()))) {
			return false;
		}
		if (this.weight != ((Medication) object).getWeight()) {
			return false;
		}
		if (!(this.code.equals(((Medication) object).getCode()))) {
			return false;
		}
		if (!(this.image.equals(((Medication) object).getImage()))) {
			return false;
		}
		return true;
	}

}
