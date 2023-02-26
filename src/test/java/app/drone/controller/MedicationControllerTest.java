package app.drone;

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

import app.drone.controller.MedicationController;
import app.drone.controller.exceptions.MedicationNotFoundException;
import app.drone.entities.Medication;
import app.drone.repositories.MedicationRepository;

@WebMvcTest(MedicationController.class)
public class MedicationControllerTest {
	@Autowired
	MockMvc mock;
	@Autowired
	ObjectMapper mapper;

	@MockBean
	MedicationRepository medicationRepository;

	Medication MED_1 = new Medication(1L, "Cefixime-200", 200, "CEFIXIME_200", "Image-1.png");
	Medication MED_2 = new Medication(2L, "Aspirine-300", 300, "ASPIRINE_300", "Image-2.png");
	Medication MED_3 = new Medication(3L, "Cefalexine-250", 250, "CEFALEXINE_250", "Image-3.png");

	@Test
	public void all_success() throws Exception {
		List<Medication> medications = new ArrayList<>(Arrays.asList(MED_1, MED_2, MED_3));

		Mockito.when(medicationRepository.findAll()).thenReturn(medications);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/medication")
				.contentType(MediaType.APPLICATION_JSON);

		mock.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[1].name", is("Aspirine-300")));
	}

	@Test
	public void find_success() throws Exception {
		Mockito.when(medicationRepository.findById(MED_1.getId())).thenReturn(Optional.of(MED_1));

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/medication/1")
				.contentType(MediaType.APPLICATION_JSON);

		mock.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.name", is("Cefixime-200")));
	}

	@Test
	public void find_notFound() throws Exception {
		Long id = 8L;
		Mockito.when(medicationRepository.findById(id)).thenReturn(Optional.empty());

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/medication/8")
				.contentType(MediaType.APPLICATION_JSON);

		mock.perform(request).andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof MedicationNotFoundException))
				.andExpect(result -> assertEquals("Medication with id " + id + " does not exists",
						result.getResolvedException().getMessage()));
	}

	@Test
	public void create_success() throws Exception {
		Medication medication = new Medication(5L, "Tylenol", 100F, "TYLENOL", "Image-5.png");

		Mockito.when(medicationRepository.save(medication)).thenReturn(medication);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/medication")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(medication));

		mock.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.name", is("Tylenol")));
	}

	@Test
	public void update_success() throws Exception {
		Medication updatedMedication = new Medication(1L, "Cefixime-250", 250, "CEFIXIME_250", "Image-31.png");

		Mockito.when(medicationRepository.findById(MED_1.getId())).thenReturn(Optional.of(MED_1));
		Mockito.when(medicationRepository.save(updatedMedication)).thenReturn(updatedMedication);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/medication/1")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(updatedMedication));

		mock.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.name", is("Cefixime-250")));
	}

	@Test
	public void update_notFound() throws Exception {
		Medication updatedMedication = new Medication(8L, "Cefixime-250", 250, "CEFIXIME_250", "Image-31.png");

		Mockito.when(medicationRepository.findById(updatedMedication.getId())).thenReturn(Optional.empty());

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/medication/8")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(updatedMedication));

		mock.perform(request).andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof MedicationNotFoundException))
				.andExpect(
						result -> assertEquals("Medication with id " + updatedMedication.getId() + " does not exists",
								result.getResolvedException().getMessage()));
	}

	@Test
	public void delete_success() throws Exception {
		Mockito.when(medicationRepository.findById(MED_3.getId())).thenReturn(Optional.of(MED_3));

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/medication/3")
				.contentType(MediaType.APPLICATION_JSON);

		mock.perform(request).andExpect(status().isOk());
	}

	@Test
	public void delete_notFound() throws Exception {
		Long id = 8L;
		Mockito.when(medicationRepository.findById(id)).thenReturn(Optional.empty());

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/medication/8")
				.contentType(MediaType.APPLICATION_JSON);

		mock.perform(request).andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof MedicationNotFoundException))
				.andExpect(result -> assertEquals("Medication with id " + id + " does not exists",
						result.getResolvedException().getMessage()));
	}
}
