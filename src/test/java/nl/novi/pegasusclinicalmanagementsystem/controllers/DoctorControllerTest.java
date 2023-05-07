package nl.novi.pegasusclinicalmanagementsystem.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.pegasusclinicalmanagementsystem.dtos.DoctorDto;
import nl.novi.pegasusclinicalmanagementsystem.security.JwtUtil;
import nl.novi.pegasusclinicalmanagementsystem.services.CustomUserDetailsService;
import nl.novi.pegasusclinicalmanagementsystem.services.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DoctorController.class)
@WithMockUser(username = "testuser", password = "testpassword", roles = "ADMIN")
public class DoctorControllerTest {

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DoctorService doctorService;

    private DoctorDto doctorDto;

    @BeforeEach
    public void setUp() {
        Set<DayOfWeek> availableDays = new HashSet<>();
        availableDays.add(DayOfWeek.MONDAY);
        availableDays.add(DayOfWeek.WEDNESDAY);

        Set<LocalTime> availableTimes = new HashSet<>();
        availableTimes.add(LocalTime.of(9, 0));
        availableTimes.add(LocalTime.of(15, 0));

        doctorDto = new DoctorDto();
        doctorDto.setId(1L);
        doctorDto.setName("Dr. John Doe");
        doctorDto.setAddress("123 Main St.");
        doctorDto.setPhoneNumber("123-456-7890");
        doctorDto.setEmailAddress("john.doe@example.com");
        doctorDto.setSpecialization("Cardiology");
        doctorDto.setAvailableDays(availableDays);
        doctorDto.setAvailableTimes(availableTimes);
    }

    @Test
    public void getAllDoctors_shouldReturnListOfDoctors() throws Exception {
        List<DoctorDto> doctorDtos = Arrays.asList(doctorDto);

        when(doctorService.getAllDoctors()).thenReturn(doctorDtos);

        mockMvc.perform(get("/api/v1/doctors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(doctorDto.getId()))
                .andExpect(jsonPath("$[0].name").value(doctorDto.getName()))
                .andExpect(jsonPath("$[0].address").value(doctorDto.getAddress()))
                .andExpect(jsonPath("$[0].phoneNumber").value(doctorDto.getPhoneNumber()))
                .andExpect(jsonPath("$[0].emailAddress").value(doctorDto.getEmailAddress()))
                .andExpect(jsonPath("$[0].specialization").value(doctorDto.getSpecialization()));

        verify(doctorService, times(1)).getAllDoctors();
    }

    @Test
    public void getDoctor_shouldReturnDoctorById() throws Exception {
        when(doctorService.getDoctor(doctorDto.getId())).thenReturn(doctorDto);

        mockMvc.perform(get("/api/v1/doctors/{id}", doctorDto.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(doctorDto.getId()))
                .andExpect(jsonPath("$.name").value(doctorDto.getName()))
                .andExpect(jsonPath("$.address").value(doctorDto.getAddress()))
                .andExpect(jsonPath("$.phoneNumber").value(doctorDto.getPhoneNumber()))
                .andExpect(jsonPath("$.emailAddress").value(doctorDto.getEmailAddress()))
                .andExpect(jsonPath("$.emailAddress").value(doctorDto.getEmailAddress()))
                .andExpect(jsonPath("$.specialization").value(doctorDto.getSpecialization()));

        verify(doctorService, times(1)).getDoctor(doctorDto.getId());
    }

    @Test
    public void addDoctor_shouldReturnCreatedDoctor() throws Exception {
        when(doctorService.addDoctor(doctorDto)).thenReturn(doctorDto);

        mockMvc.perform(post("/api/v1/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctorDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(doctorDto.getId()))
                .andExpect(jsonPath("$.name").value(doctorDto.getName()))
                .andExpect(jsonPath("$.address").value(doctorDto.getAddress()))
                .andExpect(jsonPath("$.phoneNumber").value(doctorDto.getPhoneNumber()))
                .andExpect(jsonPath("$.emailAddress").value(doctorDto.getEmailAddress()))
                .andExpect(jsonPath("$.specialization").value(doctorDto.getSpecialization()));

        verify(doctorService, times(1)).addDoctor(doctorDto);
    }

    @Test
    public void deleteDoctor_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/doctors/{id}", doctorDto.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(doctorService, times(1)).deleteDoctor(doctorDto.getId());
    }

    @Test
    public void updateDoctor_shouldReturnUpdatedDoctor() throws Exception {
        when(doctorService.updateDoctor(doctorDto.getId(), doctorDto)).thenReturn(doctorDto);

        mockMvc.perform(put("/api/v1/doctors/{id}", doctorDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctorDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(doctorDto.getId()))
                .andExpect(jsonPath("$.name").value(doctorDto.getName()))
                .andExpect(jsonPath("$.address").value(doctorDto.getAddress()))
                .andExpect(jsonPath("$.phoneNumber").value(doctorDto.getPhoneNumber()))
                .andExpect(jsonPath("$.emailAddress").value(doctorDto.getEmailAddress()))
                .andExpect(jsonPath("$.specialization").value(doctorDto.getSpecialization()));

        verify(doctorService, times(1)).updateDoctor(doctorDto.getId(), doctorDto);
    }

    @Test
    public void updateDoctorAvailability_shouldReturnUpdatedDoctor() throws Exception {
        Set<DayOfWeek> availableDays = new HashSet<>(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.FRIDAY));
        Set<LocalTime> availableTimes = new HashSet<>(Arrays.asList(LocalTime.of(10, 0), LocalTime.of(16, 0)));

        when(doctorService.updateDoctorAvailability(doctorDto.getId(), availableDays, availableTimes))
                .thenReturn(doctorDto);

        mockMvc.perform(put("/api/v1/doctors/{id}/availability", doctorDto.getId())
                        .param("availableDay", "MONDAY", "FRIDAY")
                        .param("availableTime", "10:00", "16:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(doctorDto.getId()))
                .andExpect(jsonPath("$.name").value(doctorDto.getName()))
                .andExpect(jsonPath("$.address").value(doctorDto.getAddress()))
                .andExpect(jsonPath("$.phoneNumber").value(doctorDto.getPhoneNumber()))
                .andExpect(jsonPath("$.emailAddress").value(doctorDto.getEmailAddress()))
                .andExpect(jsonPath("$.specialization").value(doctorDto.getSpecialization()));

        verify(doctorService, times(1)).updateDoctorAvailability(doctorDto.getId(), availableDays, availableTimes);
    }

    }