package app.drone.test.mocks;

import app.drone.entities.Medication;

public class MedicationsMock {
	public static final Medication MED_1 = new Medication(1L, "Cefixime-200", 200, "CEFIXIME_200", "Image-1.png");
	public static final Medication MED_2 = new Medication(2L, "Aspirine-300", 300, "ASPIRINE_300", "Image-2.png");
	public static final Medication MED_3 = new Medication(3L, "Cefalexine-250", 250, "CEFALEXINE_250", "Image-3.png");
}
