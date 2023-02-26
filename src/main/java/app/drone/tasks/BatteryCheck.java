package app.drone.tasks;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import app.drone.entities.Drone;
import app.drone.repositories.DroneRepository;

@Component
public class BatteryCheck {
	private static final Logger log = LoggerFactory.getLogger(BatteryCheck.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");

	private final DroneRepository repository;

	BatteryCheck(DroneRepository repository) {
		this.repository = repository;
	}

	@Scheduled(fixedRate = 300000) // Every 5 minutes
	public void reportDronesBattery() {
		List<Drone> drones = repository.findAll();
		drones.forEach((drone) -> {
			if (drone.getBatteryCapacity() < 25) {
				log.warn("{}, DRONE_ID: {}, BATTERY_LEVEL: {}%", dateFormat.format(new Date()), drone.getId(),
						drone.getBatteryCapacity());
			} else {
				log.info("{}, DRONE_ID: {}, BATTERY_LEVEL: {}%", dateFormat.format(new Date()), drone.getId(),
						drone.getBatteryCapacity());
			}

		});
	}
}
