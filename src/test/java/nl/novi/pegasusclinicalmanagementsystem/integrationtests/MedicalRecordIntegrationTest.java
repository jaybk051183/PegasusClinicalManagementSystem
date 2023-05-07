package nl.novi.pegasusclinicalmanagementsystem.integrationtests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.pegasusclinicalmanagementsystem.dtos.MedicalRecordDto;
import nl.novi.pegasusclinicalmanagementsystem.models.MedicalRecord;
import nl.novi.pegasusclinicalmanagementsystem.models.Patient;
import nl.novi.pegasusclinicalmanagementsystem.repositories.FileUploadRepository;
import nl.novi.pegasusclinicalmanagementsystem.repositories.MedicalRecordRepository;
import nl.novi.pegasusclinicalmanagementsystem.repositories.PatientRepository;
import nl.novi.pegasusclinicalmanagementsystem.services.MedicalRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(username = "testadmin", password = "testpassword", roles = "ADMIN")
class MedicalRecordIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private FileUploadRepository fileUploadRepository;

    Patient patient1;
    MedicalRecord medicalRecord1;
    MedicalRecordDto medicalRecordDto1;

    @BeforeEach
    public void setUp() {
        if(patientRepository.count() > 0) {
            patientRepository.deleteAll();
        }
        if(medicalRecordRepository.count() > 0) {
            medicalRecordRepository.deleteAll();
        }

        Patient patient1 = new Patient(null, "John", "Dijkstra", "j.dijkstra@outlook.com", "070-456789034", "Mariasingel 23", "OHRA", "INS-12345", null, null, null);
        Patient patient2 = new Patient(null, "Janeke", "Dijksteel", "j.dijksteel@gmail.com", "073-765432143", "Boelelaan 34", "ASR", "INS-98765", null, null, null);

        MedicalRecord medicalRecord1 = new MedicalRecord(null, "Allergieën: pinda's");
        MedicalRecord medicalRecord2 = new MedicalRecord(null, "Geen bekende medische aandoeningen");

        patient1.setMedicalRecord(medicalRecord1);
        medicalRecord1.setPatient(patient1);

        patient2.setMedicalRecord(medicalRecord2);
        medicalRecord2.setPatient(patient2);

        patientRepository.save(patient1);
        patientRepository.save(patient2);
    }

    @Test
    void getAllMedicalRecords() throws Exception {
        mockMvc.perform(get("/medicalrecords"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(medicalRecord1.getId()))
                .andExpect(jsonPath("$[0].notes").value("Test notes"));
    }

    @Test
    void getMedicalRecordByPatientId() throws Exception {
        mockMvc.perform(get("/medicalrecords/" + patient1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(medicalRecord1.getId()))
                .andExpect(jsonPath("notes").value("Test notes"));
    }

    @Test
    void createMedicalRecord() throws Exception {
        MedicalRecordDto newMedicalRecordDto = new MedicalRecordDto(2L, "New test notes");
        mockMvc.perform(post("/medicalrecords/" + patient1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newMedicalRecordDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(2L))
                .andExpect(jsonPath("notes").value("New test notes"));
    }

    @Test
    void updateMedicalRecord() throws Exception {        MedicalRecordDto updatedMedicalRecordDto = new MedicalRecordDto(medicalRecord1.getId(), "Updated test notes");
        mockMvc.perform(put("/medicalrecords/" + patient1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedMedicalRecordDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(medicalRecord1.getId()))
                .andExpect(jsonPath("notes").value("Updated test notes"));
    }

    @Test
    void deleteMedicalRecordByPatientId() throws Exception {
        mockMvc.perform(delete("/medicalrecords/" + patient1.getId()))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final MedicalRecordDto obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}

