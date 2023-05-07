package nl.novi.pegasusclinicalmanagementsystem.services;

import nl.novi.pegasusclinicalmanagementsystem.dtos.MedicalRecordDto;
import nl.novi.pegasusclinicalmanagementsystem.exceptions.PatientNotFoundException;
import nl.novi.pegasusclinicalmanagementsystem.exceptions.RecordNotFoundException;
import nl.novi.pegasusclinicalmanagementsystem.models.FileUploadResponse;
import nl.novi.pegasusclinicalmanagementsystem.models.MedicalRecord;
import nl.novi.pegasusclinicalmanagementsystem.models.Patient;
import nl.novi.pegasusclinicalmanagementsystem.repositories.FileUploadRepository;
import nl.novi.pegasusclinicalmanagementsystem.repositories.MedicalRecordRepository;
import nl.novi.pegasusclinicalmanagementsystem.repositories.PatientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final PatientRepository patientRepository;
    private final FileUploadRepository uploadRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository, PatientRepository patientRepository, FileUploadRepository uploadRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.patientRepository = patientRepository;
        this.uploadRepository = uploadRepository;
    }

    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordRepository.findAll();
    }

    public MedicalRecord getMedicalRecordByPatientId(Long patientId) {
        return medicalRecordRepository.findById(patientId).orElseThrow(() -> new RecordNotFoundException("Medical record not found for patient id: " + patientId));
    }

    public void deleteMedicalRecordByPatientId(Long patientId) {
        Optional<MedicalRecord> medicalRecordOptional = medicalRecordRepository.findByPatientId(patientId);
        if (medicalRecordOptional.isPresent()) {
            medicalRecordRepository.delete(medicalRecordOptional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Medical record not found");
        }
    }

    public MedicalRecord createMedicalRecord(Long patientId, MedicalRecordDto medicalRecordDto) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + patientId));

        if (patient.getMedicalRecord() != null) {
            throw new IllegalStateException("A medical record already exists for this patient.");
        }

        MedicalRecord medicalRecord = new MedicalRecord(patient, medicalRecordDto.getNotes());
        return medicalRecordRepository.save(medicalRecord);
    }

    public MedicalRecord updateMedicalRecord(Long patientId, MedicalRecordDto medicalRecordDto) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + patientId));

        MedicalRecord existingMedicalRecord = patient.getMedicalRecord();

        if (existingMedicalRecord == null) {
            throw new RecordNotFoundException("No medical record exists for this patient.");
        }

        existingMedicalRecord.setNotes(medicalRecordDto.getNotes());
        return medicalRecordRepository.save(existingMedicalRecord);
    }

    public void assignFileToMedicalRecord(String name, Long id) {

        Optional<MedicalRecord> optionalMedicalRecord = medicalRecordRepository.findById(id);

        Optional<FileUploadResponse> fileUploadResponse = uploadRepository.findByFileName(name);

        if (optionalMedicalRecord.isPresent() && fileUploadResponse.isPresent()) {

            FileUploadResponse file = fileUploadResponse.get();

            MedicalRecord medicalRecord = optionalMedicalRecord.get();

            medicalRecord.setFile(file);

            medicalRecordRepository.save(medicalRecord);

        }

    }


}

