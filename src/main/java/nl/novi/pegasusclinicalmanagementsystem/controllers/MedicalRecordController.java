package nl.novi.pegasusclinicalmanagementsystem.controllers;

import nl.novi.pegasusclinicalmanagementsystem.dtos.MedicalRecordDto;
import nl.novi.pegasusclinicalmanagementsystem.models.FileUploadResponse;
import nl.novi.pegasusclinicalmanagementsystem.models.MedicalRecord;
import nl.novi.pegasusclinicalmanagementsystem.services.MedicalRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;
    private final FileUploadDownloadController fileUploadDownloadController;

    public  MedicalRecordController(MedicalRecordService medicalRecordService, FileUploadDownloadController  fileUploadDownloadController){
        this.medicalRecordService = medicalRecordService;
        this.fileUploadDownloadController = fileUploadDownloadController;
    }

    @GetMapping("/medical-records")
    public ResponseEntity<List<MedicalRecord>> getAllMedicalRecords() {
        List<MedicalRecord> medicalRecords = medicalRecordService.getAllMedicalRecords();
        return new ResponseEntity<>(medicalRecords, HttpStatus.OK);
    }

    @GetMapping("/medical-records/{patientId}")
    public ResponseEntity<MedicalRecord> getMedicalRecordByPatientId(@PathVariable Long patientId) {
        MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordByPatientId(patientId);
        return new ResponseEntity<>(medicalRecord, HttpStatus.OK);
    }

    @PostMapping("/medical-records/{patientId}")
    public ResponseEntity<MedicalRecord> createMedicalRecord(@PathVariable Long patientId, @RequestBody MedicalRecordDto medicalRecordDto) {
        MedicalRecord createdMedicalRecord = medicalRecordService.createMedicalRecord(patientId, medicalRecordDto);
        return new ResponseEntity<>(createdMedicalRecord, HttpStatus.CREATED);
    }

    @PutMapping("/medical-records/{patientId}")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@PathVariable Long patientId, @RequestBody MedicalRecordDto medicalRecordDto) {
        MedicalRecord updatedMedicalRecord = medicalRecordService.updateMedicalRecord(patientId, medicalRecordDto);
        return new ResponseEntity<>(updatedMedicalRecord, HttpStatus.OK);
    }

    @DeleteMapping("/medical-records/{patientId}")
    public ResponseEntity<Void> deleteMedicalRecordByPatientId(@PathVariable Long patientId) {
        medicalRecordService.deleteMedicalRecordByPatientId(patientId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/file")
    public void assignFileToMedicalRecord(@PathVariable("id") Long id,
                                     @RequestBody MultipartFile file) {

        FileUploadResponse fileUpload = fileUploadDownloadController.singleFileUpload(file);

        medicalRecordService.assignFileToMedicalRecord(fileUpload.getFileName(), id);

    }

}

