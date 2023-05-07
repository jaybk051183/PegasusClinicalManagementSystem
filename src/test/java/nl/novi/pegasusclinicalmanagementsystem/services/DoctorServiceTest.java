package nl.novi.pegasusclinicalmanagementsystem.services;

import nl.novi.pegasusclinicalmanagementsystem.dtos.DoctorDto;
import nl.novi.pegasusclinicalmanagementsystem.models.Doctor;
import nl.novi.pegasusclinicalmanagementsystem.repositories.DoctorRepository;
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

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DoctorServiceTest {

    @Mock
    DoctorRepository doctorRepository;

    @InjectMocks
    DoctorService doctorService;

    @Captor
    ArgumentCaptor<Doctor> captor;

    Doctor doctor1;
    Doctor doctor2;

    @BeforeEach
    void setUp() {
        doctor1 = new Doctor();
        doctor1.setId(1L);
        doctor1.setName("Dr. Dirk Jansen");
        doctor1.setSpecialization("Huisarts");

        doctor2 = new Doctor();
        doctor2.setId(2L);
        doctor2.setName("Dr. Jannie Smith");
        doctor2.setSpecialization("Kinderarts");
    }

    @Test
    void getAllDoctors() {
        when(doctorRepository.findAll()).thenReturn(List.of(doctor1, doctor2));

        List<DoctorDto> doctorsFound = doctorService.getAllDoctors();

        assertEquals(doctor1.getName(), doctorsFound.get(0).getName());
        assertEquals(doctor2.getName(), doctorsFound.get(1).getName());
    }

    @Test
    void getDoctor() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor1));

        DoctorDto dto = doctorService.getDoctor(1L);

        assertEquals(doctor1.getName(), dto.getName());
    }

    @Test
    void addDoctor() {
        DoctorDto doctorDto = new DoctorDto();
        doctorDto.setName("Dr. Dirk Jansen");
        doctorDto.setSpecialization("Huisarts");

        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor1);

        doctorService.addDoctor(doctorDto);
        verify(doctorRepository, times(1)).save(captor.capture());
        Doctor doctor = captor.getValue();

        assertEquals(doctor1.getName(), doctor.getName());
        assertEquals(doctor1.getSpecialization(), doctor.getSpecialization());
    }

    @Test
    void updateDoctor() {
        DoctorDto doctorDto = new DoctorDto();
        doctorDto.setName("Dr. Updated Doctor");
        doctorDto.setSpecialization("KNO arts");

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor1));
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor1);

        doctorService.updateDoctor(1L, doctorDto);
        verify(doctorRepository, times(1)).save(captor.capture());
        Doctor doctor = captor.getValue();

        assertEquals(doctor1.getName(), doctor.getName());
        assertEquals(doctor1.getSpecialization(), doctor.getSpecialization());
    }

    @Test
    void deleteDoctor() {
        doctorService.deleteDoctor(1L);

        verify(doctorRepository).deleteById(1L);
    }

    @Test
    void updateDoctorAvailability() {
        Set<DayOfWeek> availableDays = Set.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY);
        Set<LocalTime> availableTimes = Set.of(LocalTime.of(9, 0), LocalTime.of(13, 0));

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor1));
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor1);

        doctorService.updateDoctorAvailability(1L, availableDays, availableTimes);
        verify(doctorRepository, times(1)).save(captor.capture());
        Doctor doctor = captor.getValue();

        assertEquals(availableDays, doctor.getAvailableDays());
        assertEquals(availableTimes, doctor.getAvailableTimes());
    }
}


