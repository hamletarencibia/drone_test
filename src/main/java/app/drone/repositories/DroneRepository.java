package app.drone.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.drone.entities.Drone;
import app.drone.entities.types.DroneState;

public interface DroneRepository extends JpaRepository<Drone, Long> {
	List<Drone> findByState(DroneState state);
}
