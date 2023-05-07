package nl.novi.pegasusclinicalmanagementsystem.repositories;

import nl.novi.pegasusclinicalmanagementsystem.dtos.AppointmentReportDto;
import nl.novi.pegasusclinicalmanagementsystem.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a FROM Appointment a WHERE a.date = :date AND a.location = :location AND a.doctor.id = :doctorId")
    List<Appointment> findAvailableAppointments(@Param("date") LocalDate date, @Param("location") String location, @Param("doctorId") Long doctorId);

    @Query("SELECT new nl.novi.pegasusclinicalmanagementsystem.dtos.AppointmentReportDto(a.date, a.startTime, d.name, CONCAT(p.firstName, ' ', p.lastName)) FROM Appointment a JOIN a.doctor d JOIN a.patient p")
    List<AppointmentReportDto> findAppointmentsForReport();

    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.status = 'CANCELLED'")
    Long countCancelledAppointments();

    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId")
    List<Appointment> findAllByDoctorId(@Param("doctorId") Long doctorId);

    @Query("SELECT a FROM Appointment a JOIN a.doctor d")
    List<Appointment> findAllWithDoctorNames();

}
