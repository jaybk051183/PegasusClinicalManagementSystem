package nl.novi.pegasusclinicalmanagementsystem.services;

import nl.novi.pegasusclinicalmanagementsystem.dtos.DoctorDto;
import nl.novi.pegasusclinicalmanagementsystem.exceptions.DoctorNotFoundException;
import nl.novi.pegasusclinicalmanagementsystem.models.Doctor;
import nl.novi.pegasusclinicalmanagementsystem.repositories.DoctorRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<DoctorDto> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctors.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public DoctorDto getDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new  DoctorNotFoundException("Doctor not found"));
        return convertToDto(doctor);
    }

    public DoctorDto addDoctor(DoctorDto doctorDto) {
        Doctor doctor = convertToEntity(doctorDto);
        Doctor savedDoctor = doctorRepository.save(doctor);
        return convertToDto(savedDoctor);
    }

    public DoctorDto updateDoctor(Long id, DoctorDto doctorDto) {
        Doctor existingDoctor = doctorRepository.findById(id).orElseThrow(() -> new  DoctorNotFoundException("Doctor not found"));
        existingDoctor.setName(doctorDto.getName());
        existingDoctor.setAddress(doctorDto.getAddress());
        existingDoctor.setPhoneNumber(doctorDto.getPhoneNumber());
        existingDoctor.setEmailAddress(doctorDto.getEmailAddress());
        existingDoctor.setSpecialization(doctorDto.getSpecialization());
        existingDoctor.setAvailableDays(doctorDto.getAvailableDays());
        existingDoctor.setAvailableTimes(doctorDto.getAvailableTimes());
        Doctor updatedDoctor = doctorRepository.save(existingDoctor);
        return convertToDto(updatedDoctor);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    public DoctorDto updateDoctorAvailability(Long id, Set<DayOfWeek> availableDay, Set<LocalTime> availableTime) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));
        doctor.setAvailableDays(availableDay);
        doctor.setAvailableTimes(availableTime);
        Doctor updatedDoctor = doctorRepository.save(doctor);
        return convertToDto(updatedDoctor);
    }

    private DoctorDto convertToDto(Doctor doctor) {
        DoctorDto doctorDto = new DoctorDto();
        doctorDto.setId(doctor.getId());
        doctorDto.setName(doctor.getName());
        doctorDto.setAddress(doctor.getAddress());
        doctorDto.setPhoneNumber(doctor.getPhoneNumber());
        doctorDto.setEmailAddress(doctor.getEmailAddress());
        doctorDto.setSpecialization(doctor.getSpecialization());
        doctorDto.setAvailableDays(doctor.getAvailableDays());
        doctorDto.setAvailableTimes(doctor.getAvailableTimes());
        return doctorDto;
    }

    private Doctor convertToEntity(DoctorDto doctorDto) {
        Doctor doctor = new Doctor();
        doctor.setName(doctorDto.getName());
        doctor.setAddress(doctorDto.getAddress());
        doctor.setPhoneNumber(doctorDto.getPhoneNumber());
        doctor.setEmailAddress(doctorDto.getEmailAddress());
        doctor.setSpecialization(doctorDto.getSpecialization());
        doctor.setAvailableDays(doctorDto.getAvailableDays());
        doctor.setAvailableTimes(doctorDto.getAvailableTimes());
        return doctor;
    }
}
