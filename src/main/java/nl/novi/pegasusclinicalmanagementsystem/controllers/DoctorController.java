package nl.novi.pegasusclinicalmanagementsystem.controllers;

import nl.novi.pegasusclinicalmanagementsystem.dtos.DoctorDto;
import nl.novi.pegasusclinicalmanagementsystem.services.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")
public class DoctorController {

    private final DoctorService doctorService;

    public  DoctorController(DoctorService doctorService){
        this.doctorService = doctorService;
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorDto>> getAllDoctors() {
        List<DoctorDto> dtos = doctorService.getAllDoctors();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/doctors/{id}")
    public ResponseEntity<DoctorDto> getDoctor(@PathVariable("id")Long id) {
        DoctorDto doctorDto = doctorService.getDoctor(id);
        return ResponseEntity.ok(doctorDto);
    }

    @PostMapping("/doctors")
    public ResponseEntity<DoctorDto> addDoctor(@RequestBody DoctorDto dto) {
        DoctorDto doctorDto = doctorService.addDoctor(dto);
        return ResponseEntity.created(null).body(doctorDto);
    }

    @DeleteMapping("/doctors/{id}")
    public ResponseEntity<Object> deleteDoctor(@PathVariable("id") Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/doctors/{id}")
    public ResponseEntity<DoctorDto> updateDoctor(@PathVariable("id") Long id, @RequestBody DoctorDto doctorDto) {
        doctorService.updateDoctor(id, doctorDto);
        return ResponseEntity.ok(doctorDto);
    }

    @PutMapping("/doctors/{id}/availability")
    public ResponseEntity<DoctorDto> updateDoctorAvailability(
            @PathVariable("id") Long id,
            @RequestParam("availableDay") Set<DayOfWeek> availableDay,
            @RequestParam("availableTime") Set<LocalTime> availableTime) {
        DoctorDto updatedDoctor = doctorService.updateDoctorAvailability(id, availableDay, availableTime);
        return ResponseEntity.ok(updatedDoctor);
    }

}
