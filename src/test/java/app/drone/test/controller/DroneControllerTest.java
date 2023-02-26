package app.drone.test.controller;

import static app.drone.test.mocks.DronesMock.DRONE_1;
import static app.drone.test.mocks.DronesMock.DRONE_2;
import static app.drone.test.mocks.DronesMock.DRONE_3;
import static app.drone.test.mocks.MedicationsMock.MED_1;
import static app.drone.test.mocks.MedicationsMock.MED_2;
import static app.drone.test.mocks.MedicationsMock.MED_3;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.drone.controller.DroneController;
import app.drone.controller.exceptions.DroneLowBatteryException;
import app.drone.controller.exceptions.DroneNotFoundException;
import app.drone.controller.exceptions.DroneNotIdleException;
import app.drone.controller.exceptions.DroneWeightLimitExcededException;
import app.drone.controller.exceptions.MedicationNotFoundException;
import app.drone.entities.Drone;
import app.drone.entities.Medication;
import app.drone.entities.types.DroneModel;
import app.drone.entities.types.DroneState;
import app.drone.repositories.DroneRepository;
import app.drone.repositories.MedicationRepository;

@WebMvcTest(DroneController.class)
public class DroneControllerTest {
	@Autowired
	MockMvc mock;
	@Autowired
	ObjectMapper mapper;

	@MockBean
	DroneRepository droneRepository;
	@MockBean
	MedicationRepository medicationRepository;

	@Test
	public void all_success() throws Exception {
		List<Drone> drones = new ArrayList<>(Arrays.asList(DRONE_1, DRONE_2, DRONE_3));

		Mockito.when(droneRepository.findAll()).thenReturn(drones);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/drone")
				.contentType(MediaType.APPLICATION_JSON);

		mock.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[1].serialNumber", is(DRONE_2.getSerialNumber())));
	}

	@Test
	public void find_success() throws Exception {
		Mockito.when(droneRepository.findById(DRONE_1.getId())).thenReturn(Optional.of(DRONE_1));

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/drone/1")
				.contentType(MediaType.APPLICATION_JSON);

		mock.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.serialNumber", is(DRONE_1.getSerialNumber())));
	}

	@Test
	public void find_notFound() throws Exception {
		Long id = 8L;
		Mockito.when(droneRepository.findById(id)).thenReturn(Optional.empty());

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/drone/8")
				.contentType(MediaType.APPLICATION_JSON);

		mock.perform(request).andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof DroneNotFoundException))
				.andExpect(result -> assertEquals("Drone with id " + id + " does not exists",
						result.getResolvedException().getMessage()));
	}

	@Test
	public void create_success() throws Exception {
		Drone drone = new Drone(5L, "DRONE_005", DroneModel.CRUISERWEIGHT, 400, 87, DroneState.DELIVERED);

		Mockito.when(droneRepository.save(drone)).thenReturn(drone);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/drone")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(drone));

		mock.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.serialNumber", is(drone.getSerialNumber())));
	}

	@Test
	public void update_success() throws Exception {
		Drone updatedDrone = new Drone(1L, "DRONE_001", DroneModel.HEAVYWEIGHT, 500, 100, DroneState.IDLE);

		Mockito.when(droneRepository.findById(DRONE_1.getId())).thenReturn(Optional.of(DRONE_1));
		Mockito.when(droneRepository.save(updatedDrone)).thenReturn(updatedDrone);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/drone/1")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(updatedDrone));

		mock.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.serialNumber", is(updatedDrone.getSerialNumber())));
	}

	@Test
	public void update_notFound() throws Exception {
		Drone updatedDrone = new Drone(8L, "DRONE_008", DroneModel.MIDDLEWEIGHT, 250, 8, DroneState.IDLE);

		Mockito.when(droneRepository.findById(updatedDrone.getId())).thenReturn(Optional.empty());

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/drone/8")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(updatedDrone));

		mock.perform(request).andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof DroneNotFoundException))
				.andExpect(result -> assertEquals("Drone with id " + updatedDrone.getId() + " does not exists",
						result.getResolvedException().getMessage()));
	}

	@Test
	public void delete_success() throws Exception {
		Mockito.when(droneRepository.findById(DRONE_3.getId())).thenReturn(Optional.of(DRONE_3));

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/drone/3")
				.contentType(MediaType.APPLICATION_JSON);

		mock.perform(request).andExpect(status().isOk());
	}

	@Test
	public void delete_notFound() throws Exception {
		Long id = 8L;
		Mockito.when(droneRepository.findById(id)).thenReturn(Optional.empty());

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/drone/8")
				.contentType(MediaType.APPLICATION_JSON);

		mock.perform(request).andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof DroneNotFoundException))
				.andExpect(result -> assertEquals("Drone with id " + id + " does not exists",
						result.getResolvedException().getMessage()));
	}

	@Test
	public void checkBattery_success() throws Exception {
		Mockito.when(droneRepository.findById(DRONE_2.getId())).thenReturn(Optional.of(DRONE_2));

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/drone/2/battery")
				.contentType(MediaType.APPLICATION_JSON);

		mock.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$", is(DRONE_2.getBatteryCapacity())));
	}

	@Test
	public void checkBattery_notFound() throws Exception {
		Long id = 8L;
		Mockito.when(droneRepository.findById(id)).thenReturn(Optional.empty());

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/drone/8/battery")
				.contentType(MediaType.APPLICATION_JSON);

		mock.perform(request).andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof DroneNotFoundException))
				.andExpect(result -> assertEquals("Drone with id " + id + " does not exists",
						result.getResolvedException().getMessage()));
	}

	@Test
	public void findAvailable_success() throws Exception {
		List<Drone> drones = new ArrayList<>(Arrays.asList(DRONE_2, DRONE_3));

		Mockito.when(droneRepository.findByState(DroneState.IDLE)).thenReturn(drones);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/available-drones")
				.contentType(MediaType.APPLICATION_JSON);

		mock.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].serialNumber", is(DRONE_2.getSerialNumber())));
	}

	@Test
	public void getMedications_success() throws Exception {
		List<Medication> medications = new ArrayList<>(Arrays.asList(MED_1, MED_2));
		DRONE_3.setMedications(medications);

		Mockito.when(droneRepository.findById(DRONE_3.getId())).thenReturn(Optional.of(DRONE_3));

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/drone/3/medications")
				.contentType(MediaType.APPLICATION_JSON);

		mock.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[1].name", is(MED_2.getName())));
	}

	@Test
	public void getMedications_notFound() throws Exception {
		Long id = 8L;
		Mockito.when(droneRepository.findById(id)).thenReturn(Optional.empty());

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/drone/8/medications")
				.contentType(MediaType.APPLICATION_JSON);

		mock.perform(request).andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof DroneNotFoundException))
				.andExpect(result -> assertEquals("Drone with id " + id + " does not exists",
						result.getResolvedException().getMessage()));
	}

	@Test
	public void loadMedications_success() throws Exception {
		Long[] ids = { 1L, 2L };
		Mockito.when(droneRepository.findById(DRONE_3.getId())).thenReturn(Optional.of(DRONE_3));
		Mockito.when(medicationRepository.findById(MED_1.getId())).thenReturn(Optional.of(MED_1));
		Mockito.when(medicationRepository.findById(MED_2.getId())).thenReturn(Optional.of(MED_2));
		Mockito.when(droneRepository.save(DRONE_3)).thenReturn(DRONE_3);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/drone/3/load")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(ids));

		mock.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.serialNumber", is(DRONE_3.getSerialNumber())))
				.andExpect(jsonPath("$.medications", hasSize(2)))
				.andExpect(jsonPath("$.medications[1].name", is(MED_2.getName())));
	}

	@Test
	public void loadMedications_notFound() throws Exception {
		Long id = 8L;
		Long[] ids = { 1L, 2L };
		Mockito.when(droneRepository.findById(id)).thenReturn(Optional.empty());

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/drone/8/load")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(ids));

		mock.perform(request).andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof DroneNotFoundException))
				.andExpect(result -> assertEquals("Drone with id " + id + " does not exists",
						result.getResolvedException().getMessage()));
	}

	@Test
	public void loadMedications_notIdle() throws Exception {
		Long[] ids = { 1L, 2L };
		Mockito.when(droneRepository.findById(DRONE_1.getId())).thenReturn(Optional.of(DRONE_1));

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/drone/1/load")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(ids));

		mock.perform(request).andExpect(status().isConflict())
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof DroneNotIdleException))
				.andExpect(
						result -> assertEquals("The drone cannot be loaded because it is not currently idle. STATE = "
								+ DRONE_1.getState().toString(), result.getResolvedException().getMessage()));
	}

	@Test
	public void loadMedications_lowBattery() throws Exception {
		Long[] ids = { 1L, 2L };
		Mockito.when(droneRepository.findById(DRONE_2.getId())).thenReturn(Optional.of(DRONE_2));

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/drone/2/load")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(ids));

		mock.perform(request).andExpect(status().isConflict())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof DroneLowBatteryException))
				.andExpect(
						result -> assertEquals(
								"The drone cannot be loading because the battery is too low. CURRENT_BATTERY = "
										+ DRONE_2.getBatteryCapacity() + "%",
								result.getResolvedException().getMessage()));
	}

	@Test
	public void loadMedications_weightLimitExceeded() throws Exception {
		Long[] ids = { 1L, 2L, 3L };
		float medicationWeight = MED_1.getWeight() + MED_2.getWeight() + MED_3.getWeight();
		Mockito.when(droneRepository.findById(DRONE_3.getId())).thenReturn(Optional.of(DRONE_3));
		Mockito.when(medicationRepository.findById(MED_1.getId())).thenReturn(Optional.of(MED_1));
		Mockito.when(medicationRepository.findById(MED_2.getId())).thenReturn(Optional.of(MED_2));
		Mockito.when(medicationRepository.findById(MED_3.getId())).thenReturn(Optional.of(MED_3));

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/drone/3/load")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(ids));

		mock.perform(request).andExpect(status().isConflict())
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof DroneWeightLimitExcededException))
				.andExpect(result -> assertEquals(
						"The drone cannot be loaded because it exceded its maximum capacity. MAX_CAPACITY = "
								+ DRONE_3.getWeightLimit() + ". MEDICATION_WEIGHT = " + medicationWeight,
						result.getResolvedException().getMessage()));
	}

	@Test
	public void loadMedications_medicationNotFound() throws Exception {
		Long[] ids = { 8L };
		Mockito.when(droneRepository.findById(DRONE_3.getId())).thenReturn(Optional.of(DRONE_3));
		Mockito.when(medicationRepository.findById(ids[0])).thenReturn(Optional.empty());

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/drone/3/load")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(ids));

		mock.perform(request).andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof MedicationNotFoundException))
				.andExpect(result -> assertEquals("Medication with id " + ids[0] + " does not exists",
						result.getResolvedException().getMessage()));
	}
}
