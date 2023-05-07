package nl.novi.pegasusclinicalmanagementsystem.services;

import nl.novi.pegasusclinicalmanagementsystem.dtos.MedicalRecordDto;
import nl.novi.pegasusclinicalmanagementsystem.models.FileUploadResponse;
import nl.novi.pegasusclinicalmanagementsystem.models.MedicalRecord;
import nl.novi.pegasusclinicalmanagementsystem.models.Patient;
import nl.novi.pegasusclinicalmanagementsystem.repositories.FileUploadRepository;
import nl.novi.pegasusclinicalmanagementsystem.repositories.MedicalRecordRepository;
import nl.novi.pegasusclinicalmanagementsystem.repositories.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MedicalRecordServiceTest {

    @InjectMocks
    private MedicalRecordService medicalRecordService;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private FileUploadRepository fileUploadRepository;

    private MedicalRecord testMedicalRecord;
    private Patient testPatient;

    @BeforeEach
    public void setUp() {
        testPatient = new Patient();
        testPatient.setId(1L);

        testMedicalRecord = new MedicalRecord();
        testMedicalRecord.setId(1L);
        testMedicalRecord.setPatient(testPatient);
        testMedicalRecord.setNotes("Test notes");
    }

    @Test
    public void getAllMedicalRecords() {
        when(medicalRecordRepository.findAll()).thenReturn(Arrays.asList(testMedicalRecord));
        List<MedicalRecord> medicalRecordList = medicalRecordService.getAllMedicalRecords();
        assertEquals(1, medicalRecordList.size());
        assertEquals(testMedicalRecord.getId(), medicalRecordList.get(0).getId());
    }

    @Test
    public void getMedicalRecordByPatientId() {
        when(medicalRecordRepository.findById(anyLong())).thenReturn(Optional.of(testMedicalRecord));
        MedicalRecord foundMedicalRecord = medicalRecordService.getMedicalRecordByPatientId(1L);
        assertEquals(testMedicalRecord.getId(), foundMedicalRecord.getId());
    }

    @Test
    public void createMedicalRecord() {
        MedicalRecordDto medicalRecordDto = new MedicalRecordDto();
        medicalRecordDto.setNotes("Test notes");

        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(testPatient));
        when(medicalRecordRepository.save(any(MedicalRecord.class))).thenReturn(testMedicalRecord);

        MedicalRecord createdMedicalRecord = medicalRecordService.createMedicalRecord(1L, medicalRecordDto);
        assertNotNull(createdMedicalRecord);
        assertEquals(testMedicalRecord.getNotes(), createdMedicalRecord.getNotes());
    }

    @Test
    public void updateMedicalRecord() {
        MedicalRecordDto updatedMedicalRecordDto = new MedicalRecordDto();
        updatedMedicalRecordDto.setNotes("Updated test notes");

        testPatient.setMedicalRecord(testMedicalRecord);
        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(testPatient));
        when(medicalRecordRepository.save(any(MedicalRecord.class))).thenReturn(testMedicalRecord);

        MedicalRecord updatedMedicalRecord = medicalRecordService.updateMedicalRecord(1L, updatedMedicalRecordDto);
        assertNotNull(updatedMedicalRecord);
        assertEquals(updatedMedicalRecordDto.getNotes(), updatedMedicalRecord.getNotes());
    }

    @Test
    public void assignFileToMedicalRecord() {
        FileUploadResponse testFileUploadResponse = new FileUploadResponse();
        testFileUploadResponse.setFileName("testFile.pdf");

        when(medicalRecordRepository.findById(anyLong())).thenReturn(Optional.of(testMedicalRecord));
        when(fileUploadRepository.findByFileName(anyString())).thenReturn(Optional.of(testFileUploadResponse));
        when(medicalRecordRepository.save(any(MedicalRecord.class))).thenReturn(testMedicalRecord);

        medicalRecordService.assignFileToMedicalRecord("testFile.pdf", 1L);
        verify(medicalRecordRepository, times(1)).save(testMedicalRecord);
        assertEquals(testFileUploadResponse, testMedicalRecord.getFile());
    }

    @Test
    public void deleteMedicalRecordByPatientId() {
        when(medicalRecordRepository.findByPatientId(anyLong())).thenReturn(Optional.of(testMedicalRecord));
        doNothing().when(medicalRecordRepository).delete(any(MedicalRecord.class));

        medicalRecordService.deleteMedicalRecordByPatientId(1L);
        verify(medicalRecordRepository, times(1)).delete(testMedicalRecord);
    }
}
