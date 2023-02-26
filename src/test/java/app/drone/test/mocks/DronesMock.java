package app.drone.test.mocks;

import app.drone.entities.Drone;
import app.drone.entities.types.DroneModel;
import app.drone.entities.types.DroneState;

public class DronesMock {
	public static final Drone DRONE_1 = new Drone(1L, "DRONE_001", DroneModel.LIGHTWEIGHT, 100, 98, DroneState.LOADED);
	public static final Drone DRONE_2 = new Drone(2L, "DRONE_002", DroneModel.MIDDLEWEIGHT, 250, 15, DroneState.IDLE);
	public static final Drone DRONE_3 = new Drone(3L, "DRONE_003", DroneModel.HEAVYWEIGHT, 500, 69, DroneState.IDLE);
}
