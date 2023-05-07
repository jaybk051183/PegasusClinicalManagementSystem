package nl.novi.pegasusclinicalmanagementsystem.services;

import nl.novi.pegasusclinicalmanagementsystem.models.Appointment;
import nl.novi.pegasusclinicalmanagementsystem.models.Doctor;
import nl.novi.pegasusclinicalmanagementsystem.repositories.AppointmentRepository;
import nl.novi.pegasusclinicalmanagementsystem.repositories.DoctorRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;

    public StatisticsService(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
    }

    public Long calculateAppointments() {
        return appointmentRepository.count();
    }

    public Long calculateCancellations() {
        return appointmentRepository.countCancelledAppointments();
    }

    public Map<String, Double> calculateHoursPerDoctor() {
        List<Doctor> doctors = doctorRepository.findAll();
        Map<String, Double> hoursPerDoctor = new HashMap<>();

        for (Doctor doctor : doctors) {
            List<Appointment> appointments = appointmentRepository.findAllByDoctorId(doctor.getId());
            double totalHours = 0;

            for (Appointment appointment : appointments) {
                Duration duration = Duration.between(appointment.getStartTime(), appointment.getEndTime());
                totalHours += duration.toMinutes() / 60.0;
            }

            hoursPerDoctor.put(doctor.getName(), totalHours);
        }

        return hoursPerDoctor;
    }
}
