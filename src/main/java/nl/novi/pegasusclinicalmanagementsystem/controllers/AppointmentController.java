package nl.novi.pegasusclinicalmanagementsystem.controllers;

import nl.novi.pegasusclinicalmanagementsystem.dtos.AppointmentDto;
import nl.novi.pegasusclinicalmanagementsystem.dtos.AppointmentSearchResponseDto;
import nl.novi.pegasusclinicalmanagementsystem.services.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public  AppointmentController(AppointmentService appointmentService){
        this.appointmentService = appointmentService;
    }

    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentDto>> getAllAppointmentsWithDoctorNames() {
        List<AppointmentDto> dtos = appointmentService.getAllAppointmentsWithDoctorNames();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/appointments/{id}")
    public ResponseEntity<AppointmentDto> getAppointment(@PathVariable ("id")Long id) {
        AppointmentDto appointmentDto = appointmentService.getAppointment(id);
        return ResponseEntity.ok(appointmentDto);
    }

    @PostMapping("/appointments")
    public ResponseEntity<AppointmentDto> addAppointment(@RequestBody AppointmentDto dto) {
        AppointmentDto appointmentDto = appointmentService.addAppointment(dto);
        return ResponseEntity.created(null).body(appointmentDto);
    }

    @DeleteMapping("/appointments/{id}")
    public ResponseEntity<Object> deleteAppointment(@PathVariable("id") Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/appointments/{id}")
    public ResponseEntity<AppointmentDto> updateAppointment(@PathVariable("id") Long id, @RequestBody AppointmentDto appointmentDto) {
      appointmentService.updateAppointment(id, appointmentDto);
        return ResponseEntity.ok(appointmentDto);
    }

    @GetMapping("/appointments/search")
    public ResponseEntity<AppointmentSearchResponseDto> searchAvailableAppointments(
            @RequestParam("date") LocalDate date,
            @RequestParam("location") String location,
            @RequestParam("doctorName") String doctorName) {

        List<AppointmentDto> availableAppointments = appointmentService.searchAvailableAppointments(date, location, doctorName);
        String message;
        if (availableAppointments.isEmpty()) {
            message = "Geen afspraken beschikbaar voor de opgegeven criteria.";
        } else {
            message = "Afspraak beschikbaar.";
        }
        AppointmentSearchResponseDto response = new AppointmentSearchResponseDto(message, availableAppointments);
        return ResponseEntity.ok(response);
    }

}
