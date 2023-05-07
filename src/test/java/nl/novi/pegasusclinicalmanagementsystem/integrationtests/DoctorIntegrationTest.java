package nl.novi.pegasusclinicalmanagementsystem.integrationtests;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.pegasusclinicalmanagementsystem.dtos.DoctorDto;
import nl.novi.pegasusclinicalmanagementsystem.models.Doctor;
import nl.novi.pegasusclinicalmanagementsystem.repositories.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.containsInAnyOrder;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(username = "testadmin", password = "testpassword", roles = "ADMIN")
public class DoctorIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DoctorRepository doctorRepository;

    @BeforeEach
    public void setUp() {
        if (doctorRepository.count() > 0) {
            doctorRepository.deleteAll();
        }

        Doctor doctor1 = new Doctor(null, "Dr. Jannie Smith", "Van Zeggelenlaan 34", "123-456-7890", "j.smith@gezondheidscentrum.nl", "Huisarts", Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY), Set.of(LocalTime.of(9, 0), LocalTime.of(17, 0)), null);
        Doctor doctor2 = new Doctor(null, "Dr. Jan Derksen", "Maria Boulevaart 45", "098-765-4321", "j.derksen@gezondheidscentrum.nl", "KNO arts", Set.of(DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY), Set.of(LocalTime.of(8, 0), LocalTime.of(16, 0)), null);

        doctorRepository.save(doctor1);
        doctorRepository.save(doctor2);
    }

    @Test
    public void getAllDoctors() throws Exception {
        mockMvc.perform(get("/api/doctors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    public void getDoctorById() throws Exception {
        Doctor existingDoctor = doctorRepository.findByName("Dr. Jannie Smith");

        mockMvc.perform(get("/api/doctors/{id}", existingDoctor.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Dr. Jannie Smith"))
                .andExpect(jsonPath("$.specialization").value("Huisarts"));
    }

    @Test
    public void addDoctor() throws Exception {
        DoctorDto newDoctorDto = new DoctorDto();
        newDoctorDto.setName("Dr. Alice Hoekstra");
        newDoctorDto.setAddress("Mariahoeve 14");
        newDoctorDto.setPhoneNumber("06-9876543344");
        newDoctorDto.setEmailAddress("a.hoekstra@gezondheidcentrum.nl");
        newDoctorDto.setSpecialization("Huisarts");
        newDoctorDto.setAvailableDays(Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY));
        newDoctorDto.setAvailableTimes(Set.of(LocalTime.of(9, 0), LocalTime.of(17, 0)));

        mockMvc.perform(post("/api/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newDoctorDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Dr. Alice Hoekstra"))
                .andExpect(jsonPath("$.specialization").value("Huisarts"));

        mockMvc.perform(get("/api/doctors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3));
    }

    @Test
    public void updateDoctor() throws Exception {
        Doctor existingDoctor = doctorRepository.findByName("Dr. Dirk Hansen");

        DoctorDto updatedDoctorDto = new DoctorDto();
        updatedDoctorDto.setName("Dr. Dirk Hansen");
        updatedDoctorDto.setAddress("Moerwijk 14");
        updatedDoctorDto.setPhoneNumber("06-1234567455");
        updatedDoctorDto.setEmailAddress("d.hansen@gezondheidcentrum.nl");
        updatedDoctorDto.setSpecialization("Huisarts");
        updatedDoctorDto.setAvailableDays(Set.of(DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY));
        updatedDoctorDto.setAvailableTimes(Set.of(LocalTime.of(10, 0), LocalTime.of(18, 0)));

        mockMvc.perform(put("/api/doctors/{id}", existingDoctor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedDoctorDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Dr. Dirk Hansen"))
                .andExpect(jsonPath("$.specialization").value("Huisarts"));
    }

    @Test
    public void deleteDoctor() throws Exception {
        Doctor existingDoctor = doctorRepository.findByName("Dr. Jannie Smith");

        mockMvc.perform(delete("/api/doctors/{id}", existingDoctor.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/doctors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    public void updateDoctorAvailability() throws Exception {
        Doctor existingDoctor = doctorRepository.findByName("Dr. Jan Derksen");

        mockMvc.perform(patch("/api/doctors/{id}/availability", existingDoctor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("availableDays", "MONDAY,FRIDAY")
                        .param("availableTimes", "10:00,18:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.availableDays").value(containsInAnyOrder("MONDAY", "FRIDAY")))
                .andExpect(jsonPath("$.availableTimes").value(containsInAnyOrder("10:00:00", "18:00:00")));
    }
}

