package nl.novi.pegasusclinicalmanagementsystem.services;

import nl.novi.pegasusclinicalmanagementsystem.dtos.PatientDto;
import nl.novi.pegasusclinicalmanagementsystem.exceptions.PatientNotFoundException;
import nl.novi.pegasusclinicalmanagementsystem.models.Patient;
import nl.novi.pegasusclinicalmanagementsystem.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientDto> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public PatientDto getPatient(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient not found"));
        return convertToDto(patient);
    }

    public PatientDto addPatient(PatientDto patientDto) {
        Patient patient = convertToEntity(patientDto);
        Patient savedPatient = patientRepository.save(patient);
        return convertToDto(savedPatient);
    }

    public PatientDto updatePatient(Long id, PatientDto patientDto) {
        Patient existingPatient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient not found"));
        existingPatient.setFirstName(patientDto.getFirstName());
        existingPatient.setLastName(patientDto.getLastName());
        existingPatient.setAddress(patientDto.getAddress());
        existingPatient.setPhoneNumber(patientDto.getPhoneNumber());
        existingPatient.setEmail(patientDto.getEmail());
        existingPatient.setInsuranceProvider(patientDto.getInsuranceProvider());
        existingPatient.setInsuranceNumber(patientDto.getInsuranceNumber());
        Patient updatedPatient = patientRepository.save(existingPatient);
        return convertToDto(updatedPatient);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    private PatientDto convertToDto(Patient patient) {
        PatientDto patientDto = new PatientDto();
        patientDto.setId(patient.getId());
        patientDto.setFirstName(patient.getFirstName());
        patientDto.setLastName(patient.getLastName());
        patientDto.setAddress(patient.getAddress());
        patientDto.setPhoneNumber(patient.getPhoneNumber());
        patientDto.setEmail(patient.getEmail());
        patientDto.setInsuranceProvider(patient.getInsuranceProvider());
        patientDto.setInsuranceNumber(patient.getInsuranceNumber());
        return patientDto;
    }

    private Patient convertToEntity(PatientDto patientDto) {
        Patient patient = new Patient();
        patient.setFirstName(patientDto.getFirstName());
        patient.setLastName(patientDto.getLastName());
        patient.setAddress(patientDto.getAddress());
        patient.setPhoneNumber(patientDto.getPhoneNumber());
        patient.setEmail(patientDto.getEmail());
        patient.setInsuranceProvider(patientDto.getInsuranceProvider());
        patient.setInsuranceNumber(patientDto.getInsuranceNumber());
        return patient;
    }
}
