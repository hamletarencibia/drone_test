package app.drone.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import app.drone.controller.exceptions.DroneLowBatteryException;
import app.drone.controller.exceptions.DroneNotFoundException;
import app.drone.controller.exceptions.DroneNotIdleException;
import app.drone.controller.exceptions.DroneWeightLimitExcededException;
import app.drone.controller.exceptions.MedicationNotFoundException;
import app.drone.entities.Drone;
import app.drone.entities.Medication;
import app.drone.entities.types.DroneState;
import app.drone.repositories.DroneRepository;
import app.drone.repositories.MedicationRepository;

@RestController
public class DroneController {
	private final DroneRepository repository;
	private final MedicationRepository medRepository;

	DroneController(DroneRepository repository, MedicationRepository medRepository) {
		this.repository = repository;
		this.medRepository = medRepository;
	}

	@GetMapping("/drone")
	List<Drone> all() {
		return repository.findAll();
	}

	@PostMapping("/drone")
	Drone create(@RequestBody Drone drone) {
		drone.setMedications(new ArrayList<Medication>());
		return repository.save(drone);
	}

	@GetMapping("/drone/{id}")
	Drone find(@PathVariable Long id) {
		return repository.findById(id).orElseThrow(() -> new DroneNotFoundException(id));
	}

	@PutMapping("/drone/{id}")
	Drone update(@PathVariable Long id, @RequestBody Drone newDrone) {
		Drone drone = repository.findById(id).orElseThrow(() -> new DroneNotFoundException(id));
		drone.setSerialNumber(newDrone.getSerialNumber());
		drone.setBatteryCapacity(newDrone.getBatteryCapacity());
		drone.setWeightLimit(newDrone.getWeightLimit());
		drone.setModel(newDrone.getModel());
		drone.setState(newDrone.getState());
		return repository.save(drone);
	}

	@DeleteMapping("/drone/{id}")
	void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}

	@GetMapping("/drone/{id}/battery")
	int getBattery(@PathVariable Long id) {
		return repository.findById(id).orElseThrow(() -> new DroneNotFoundException(id)).getBatteryCapacity();
	}

	@GetMapping("/available-drones")
	List<Drone> findAvailable() {
		return repository.findByState(DroneState.IDLE);
	}

	@GetMapping("/drone/{id}/medications")
	List<Medication> getMedications(@PathVariable Long id) {
		return repository.findById(id).orElseThrow(() -> new DroneNotFoundException(id)).getMedications();
	}

	@PutMapping("/drone/{id}/load")
	Drone loadMedications(@PathVariable Long id, @RequestBody Long[] medications) {
		Drone drone = repository.findById(id).orElseThrow(() -> new DroneNotFoundException(id));
		if (drone.getState() != DroneState.IDLE) {
			throw new DroneNotIdleException(drone.getState());
		}
		if (drone.getBatteryCapacity() < 25) {
			throw new DroneLowBatteryException(drone.getBatteryCapacity());
		}
		drone.setState(DroneState.LOADING);
		repository.save(drone);
		drone.setMedications(new LinkedList<Medication>());
		float medicationWeight = 0;
		for (Long medicationId : medications) {
			Medication medication = medRepository.findById(medicationId)
					.orElseThrow(() -> new MedicationNotFoundException(medicationId));
			drone.getMedications().add(medication);
			medicationWeight += medication.getWeight();
		}
		if (medicationWeight > drone.getWeightLimit()) {
			throw new DroneWeightLimitExcededException(drone.getWeightLimit(), medicationWeight);
		}
		drone.setState(DroneState.LOADED);
		return repository.save(drone);
	}
}
