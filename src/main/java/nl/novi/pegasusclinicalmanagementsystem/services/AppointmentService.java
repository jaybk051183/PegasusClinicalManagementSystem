package nl.novi.pegasusclinicalmanagementsystem.services;

import nl.novi.pegasusclinicalmanagementsystem.dtos.AppointmentDto;
import nl.novi.pegasusclinicalmanagementsystem.exceptions.AppointmentNotFoundException;
import nl.novi.pegasusclinicalmanagementsystem.exceptions.DoctorNotFoundException;
import nl.novi.pegasusclinicalmanagementsystem.exceptions.PatientNotFoundException;
import nl.novi.pegasusclinicalmanagementsystem.models.Appointment;
import nl.novi.pegasusclinicalmanagementsystem.models.Doctor;
import nl.novi.pegasusclinicalmanagementsystem.repositories.AppointmentRepository;
import nl.novi.pegasusclinicalmanagementsystem.repositories.DoctorRepository;
import nl.novi.pegasusclinicalmanagementsystem.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository, PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    public AppointmentDto getAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .map(this::transferToDto)
                .orElseThrow(() -> new AppointmentNotFoundException("No appointment found"));
    }

    public List<AppointmentDto> getAllAppointmentsWithDoctorNames() {
        return appointmentRepository.findAllWithDoctorNames()
                .stream()
                .map(this::transferToDto)
                .collect(Collectors.toList());
    }

    public AppointmentDto getAppointment(long id) {
        return appointmentRepository.findById(id)
                .map(this::transferToDto)
                .orElseThrow(() -> new AppointmentNotFoundException("No appointment found"));
    }

    public AppointmentDto addAppointment(AppointmentDto appointmentDto) {
        Appointment savedAppointment = appointmentRepository.save(transferToAppointment(appointmentDto));
        appointmentDto.setId(savedAppointment.getId());
        return appointmentDto;
    }

    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    public void updateAppointment(Long id, AppointmentDto appointmentDto) {
        if (!appointmentRepository.existsById(id)) {
            throw new AppointmentNotFoundException("No appointment found");
        }
        Appointment storedAppointment = appointmentRepository.findById(id).orElse(null);
        storedAppointment.setId(appointmentDto.getId());
        storedAppointment.setDate(appointmentDto.getDate());
        storedAppointment.setLocation(appointmentDto.getLocation());
        storedAppointment.setStartTime(appointmentDto.getStartTime());
        storedAppointment.setEndTime(appointmentDto.getEndTime());
        storedAppointment.setDoctor(doctorRepository.findById(appointmentDto.getDoctorId()).orElseThrow(() -> new DoctorNotFoundException("Doctor not found")));

        appointmentRepository.save(storedAppointment);
    }

    public List<AppointmentDto> searchAvailableAppointments(LocalDate date, String location, String doctorName) {

        Doctor doctor = doctorRepository.findByName(doctorName);
        Long doctorId = doctor.getId();

        List<Appointment> availableAppointments = appointmentRepository.findAvailableAppointments(date, location, doctorId);
        return availableAppointments.stream()
                .map(this::transferToDto)
                .collect(Collectors.toList());
    }

    public Appointment transferToAppointment(AppointmentDto dto) {
        Appointment appointment = new Appointment();
        appointment.setId(dto.getId());
        appointment.setDate(dto.getDate());
        appointment.setLocation(dto.getLocation());
        appointment.setStartTime(dto.getStartTime());
        appointment.setEndTime(dto.getEndTime());
        if (dto.getDoctorId() != null) {
            appointment.setDoctor(doctorRepository.findById(dto.getDoctorId()).orElseThrow(() -> new DoctorNotFoundException("Doctor not found")));
        }
        if (dto.getPatientId() != null) {
            appointment.setPatient(patientRepository.findById(dto.getPatientId()).orElseThrow(() -> new PatientNotFoundException("Patient not found")));
        }
        return appointment;
    }

    public AppointmentDto transferToDto(Appointment appointment) {
        var dto = new AppointmentDto();

        dto.id = appointment.getId();
        dto.doctorId = appointment.getDoctor().getId();
        dto.patientId = appointment.getPatient().getId();
        dto.date = appointment.getDate();
        dto.location = appointment.getLocation();
        dto.startTime = appointment.getStartTime();
        dto.endTime = appointment.getEndTime();

        return dto;
    }
}
