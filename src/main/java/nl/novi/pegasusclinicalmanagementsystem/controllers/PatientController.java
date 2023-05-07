package nl.novi.pegasusclinicalmanagementsystem.controllers;

import nl.novi.pegasusclinicalmanagementsystem.dtos.PatientDto;
import nl.novi.pegasusclinicalmanagementsystem.services.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/patients")
    public ResponseEntity<List<PatientDto>> getAllPatients() {
        List<PatientDto> dtos = patientService.getAllPatients();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/patients/{id}")
    public ResponseEntity<PatientDto> getPatient(@PathVariable("id") Long id) {
        PatientDto patientDto = patientService.getPatient(id);
        return ResponseEntity.ok(patientDto);
    }

    @PostMapping("/patients")
    public ResponseEntity<PatientDto> addPatient(@RequestBody PatientDto dto) {
        PatientDto patientDto = patientService.addPatient(dto);
        return ResponseEntity.created(null).body(patientDto);
    }

    @PutMapping("/patients/{id}")
    public ResponseEntity<PatientDto> updatePatient(@PathVariable("id") Long id, @RequestBody PatientDto patientDto) {
        patientService.updatePatient(id, patientDto);
        return ResponseEntity.ok(patientDto);
    }

    @DeleteMapping("/patients/{id}")
    public ResponseEntity<Object> deletePatient(@PathVariable("id") Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
