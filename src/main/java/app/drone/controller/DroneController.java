package app.drone.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import app.drone.controller.exceptions.DroneNotFoundException;
import app.drone.entities.Drone;
import app.drone.repositories.DroneRepository;

@RestController
public class DroneController {
	private final DroneRepository repository;

	DroneController(DroneRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/drone")
	List<Drone> all() {
		return repository.findAll();
	}

	@PostMapping("/drone")
	Drone create(@RequestBody Drone drone) {
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
}
