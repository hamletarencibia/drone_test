package app.drone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import app.drone.entities.Drone;

public interface DroneRepository extends JpaRepository<Drone, Long> {

}
