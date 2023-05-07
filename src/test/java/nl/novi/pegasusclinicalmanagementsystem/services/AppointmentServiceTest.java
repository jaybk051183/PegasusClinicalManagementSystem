package nl.novi.pegasusclinicalmanagementsystem.services;

import nl.novi.pegasusclinicalmanagementsystem.dtos.AppointmentDto;
import nl.novi.pegasusclinicalmanagementsystem.models.Appointment;
import nl.novi.pegasusclinicalmanagementsystem.models.Doctor;
import nl.novi.pegasusclinicalmanagementsystem.models.Patient;
import nl.novi.pegasusclinicalmanagementsystem.repositories.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@WithMockUser(username = "testuser", password = "testpassword", roles = "ADMIN")
class AppointmentServiceTest {

    @Mock
    AppointmentRepository appointmentRepository;

    @InjectMocks
    AppointmentService appointmentService;

    @Captor
    ArgumentCaptor<Appointment> captor;

    Appointment appointment1;
    Appointment appointment2;

    @BeforeEach
    void setUp() {
        Doctor doctor1 = new Doctor();
        doctor1.setId(1L);
        doctor1.setName("Mark Hoektstra");
        doctor1.setAddress("Van dale straat 14");
        doctor1.setPhoneNumber("06-123465562");
        doctor1.setEmailAddress("m.hoekstra@gezonheidcentrum.nl");
        doctor1.setSpecialization("Huisarts");

        Doctor doctor2 = new Doctor();
        doctor2.setId(2L);
        doctor2.setName("Janie Smith");
        doctor2.setAddress("Krugerlaan 12");
        doctor2.setPhoneNumber("070-5678665656");
        doctor2.setEmailAddress("j.smith@gezondheidcentrum.nl");
        doctor2.setSpecialization("Dermatoloog");

        Patient patient1 = new Patient();
        patient1.setId(1L);
        patient1.setFirstName("Michael");
        patient1.setLastName("Jansen");
        patient1.setEmail("m.jansen@outlook.com");
        patient1.setPhoneNumber("06-5652332365");
        patient1.setAddress("Heuvellaan 13");
        patient1.setInsuranceProvider("Achmea");
        patient1.setInsuranceNumber("165665AB");

        Patient patient2 = new Patient();
        patient2.setId(2L);
        patient2.setFirstName("Sarah");
        patient2.setLastName("Michels");
        patient2.setEmail("s.michels@outlook.com");
        patient2.setPhoneNumber("06-135765626");
        patient2.setAddress("Van dale straat 45");
        patient2.setInsuranceProvider("Ohra");
        patient2.setInsuranceNumber("987632AD");

        appointment1 = new Appointment(1L, LocalDate.of(2023, 5, 6), LocalTime.of(10, 0), LocalTime.of(11, 0), "Amsterdam", "Booked", patient1, doctor1);
        appointment2 = new Appointment(2L, LocalDate.of(2023, 5, 6), LocalTime.of(12, 0), LocalTime.of(13, 0), "Rotterdam", "Booked", patient2, doctor2);

        when(appointmentRepository.findAll()).thenReturn(List.of(appointment1, appointment2));
    }


    @Test
    void getAllAppointmentsWithDoctorNames() {
        when(appointmentRepository.findAll()).thenReturn(List.of(appointment1, appointment2));

        List<AppointmentDto> appointmentsFound = appointmentService.getAllAppointmentsWithDoctorNames();

        assertEquals(appointment1.getId(), appointmentsFound.get(0).getId());
        assertEquals(appointment2.getId(), appointmentsFound.get(1).getId());
    }

    @Test
    void getAppointment() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment1));

        AppointmentDto dto = appointmentService.getAppointment(1L);

        assertEquals(appointment1.getId(), dto.getId());
    }

    @Test
    void addAppointment() {
        AppointmentDto dto = new AppointmentDto(1L, 1L, LocalDate.of(2023, 5, 6), LocalTime.of(10, 0), LocalTime.of(11, 0), "Amsterdam", "Booked");

        when(appointmentRepository.save(any())).thenReturn(appointment1);

        AppointmentDto savedDto = appointmentService.addAppointment(dto);

        assertEquals(dto.getId(), savedDto.getId());
    }

    @Test
    void updateAppointment() {
        AppointmentDto dto = new AppointmentDto(1L, 1L, LocalDate.of(2023, 5, 6), LocalTime.of(10, 0), LocalTime.of(11, 0), "Amsterdam", "Booked");

        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment1));
        when(appointmentRepository.save(any())).thenReturn(appointment1);

        appointmentService.updateAppointment(1L, dto);

        verify(appointmentRepository, times(1)).save(captor.capture());
        Appointment captured = captor.getValue();

        assertEquals(dto.getId(), captured.getId());
        assertEquals(dto.getDate(), captured.getDate());
        assertEquals(dto.getStartTime(), captured.getStartTime());
        assertEquals(dto.getEndTime(), captured.getEndTime());
        assertEquals(dto.getLocation(), captured.getLocation());
        assertEquals(dto.getStatus(), captured.getStatus());
    }

    @Test
    void deleteAppointment() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment1));
        doNothing().when(appointmentRepository).deleteById(1L);

        appointmentService.deleteAppointment(1L);

        verify(appointmentRepository, times(1)).deleteById(1L);
    }
}
