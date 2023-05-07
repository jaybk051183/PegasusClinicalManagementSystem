package nl.novi.pegasusclinicalmanagementsystem.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.pegasusclinicalmanagementsystem.dtos.AppointmentDto;
import nl.novi.pegasusclinicalmanagementsystem.security.JwtUtil;
import nl.novi.pegasusclinicalmanagementsystem.services.AppointmentService;
import nl.novi.pegasusclinicalmanagementsystem.services.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(AppointmentController.class)
@WithMockUser(username = "testuser", password = "testpassword", roles = "ADMIN")
class AppointmentControllerTest {

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentService appointmentService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private AppointmentDto appointmentDto1;
    private AppointmentDto appointmentDto2;

    @BeforeEach
    void setUp() {
        appointmentDto1 = new AppointmentDto(1L, 1L, LocalDate.of(2023, 6, 12), LocalTime.of(14, 0), LocalTime.of(15, 0), "Amsterdam", "Confirmed");
        appointmentDto2 = new AppointmentDto(2L, 2L, LocalDate.of(2023, 6, 13), LocalTime.of(10, 0), LocalTime.of(11, 0), "Rotterdam", "Confirmed");
    }

    @Test
    void getAllAppointmentsWithDoctorNames() throws Exception {
        List<AppointmentDto> appointmentDtos = Arrays.asList(appointmentDto1, appointmentDto2);
        when(appointmentService.getAllAppointmentsWithDoctorNames()).thenReturn(appointmentDtos);

        mockMvc.perform(get("/api/v1/appointments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(appointmentDtos.size()))
                .andExpect(jsonPath("$[0].id").value(appointmentDto1.getId()))
                .andExpect(jsonPath("$[1].id").value(appointmentDto2.getId()));
    }
    @Test
    void getAppointment() throws Exception {
        Long appointmentId = 1L;
        when(appointmentService.getAppointmentById(appointmentId)).thenReturn(appointmentDto1);

        mockMvc.perform(get("/api/v1/appointments/{id}", appointmentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(appointmentId));
    }

    @Test
    void addAppointment() throws Exception {
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setDoctorId(1L);
        appointmentDto.setPatientId(1L);
        appointmentDto.setDate(LocalDate.of(2023, 6, 1));
        appointmentDto.setStartTime(LocalTime.of(10, 0));
        appointmentDto.setEndTime(LocalTime.of(11, 0));
        appointmentDto.setLocation("Amsterdam");
        appointmentDto.setStatus("Scheduled");

        String appointmentJson = "{ \"doctorId\": 1, \"patientId\": 1, \"date\": \"2023-06-01\", \"startTime\": \"10:00:00\", \"endTime\": \"11:00:00\", \"location\": \"Amsterdam\", \"status\": \"Scheduled\" }";

        mockMvc.perform(post("/api/v1/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(appointmentJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("doctorId").value(appointmentDto.getDoctorId()));
    }

    @Test
    void deleteAppointment() throws Exception {
        Long appointmentId = 1L;

        mockMvc.perform(delete("/api/v1/appointments/{id}", appointmentId))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateAppointment() throws Exception {
        Long appointmentId = 1L;
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setDoctorId(2L);

        String appointmentJson = "{ \"doctorId\": 2 }";

        mockMvc.perform(put("/api/v1/appointments/{id}", appointmentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(appointmentJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("doctorId").value(appointmentDto.getDoctorId()));
    }

    @Test
    void searchAvailableAppointments() throws Exception {
        LocalDate date = LocalDate.of(2023, 6, 1);
        String location = "Amsterdam";
        String doctorName = "Dr. Jan Derksen";

        mockMvc.perform(get("/api/v1/appointments/search")
                        .param("date", date.toString())
                        .param("location", location)
                        .param("doctorName", doctorName))
                .andExpect(status().isOk());
    }
}
