package app.drone.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import app.drone.controller.exceptions.MedicationNotFoundException;
import app.drone.entities.Medication;
import app.drone.repositories.MedicationRepository;

@RestController
public class MedicationController {
	private final MedicationRepository repository;

	MedicationController(MedicationRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/medication")
	List<Medication> all() {
		return repository.findAll();
	}

	@PostMapping("/medication")
	Medication create(@RequestBody Medication medication) {
		return repository.save(medication);
	}

	@GetMapping("/medication/{id}")
	Medication find(@PathVariable Long id) {
		return repository.findById(id).orElseThrow(() -> new MedicationNotFoundException(id));
	}

	@PutMapping("/medication/{id}")
	Medication update(@PathVariable Long id, @RequestBody Medication newMedication) {
		Medication medication = repository.findById(id).orElseThrow(() -> new MedicationNotFoundException(id));
		medication.setCode(newMedication.getCode());
		medication.setImage(newMedication.getImage());
		medication.setWeight(newMedication.getWeight());
		return repository.save(medication);
	}

	@DeleteMapping("/medication/{id}")
	void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}
}
