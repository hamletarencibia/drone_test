package app.drone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import app.drone.entities.Medication;

public interface MedicationRepository extends JpaRepository<Medication, Long> {

}
