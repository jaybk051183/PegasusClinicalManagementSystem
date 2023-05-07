package nl.novi.pegasusclinicalmanagementsystem.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentDto {

    public Long id;

    public Long doctorId;

    public Long patientId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate date;

    @JsonFormat(pattern = "HH:mm:ss")
    public LocalTime startTime;

    @JsonFormat(pattern = "HH:mm:ss")
    public LocalTime endTime;

    public String location;

    public String status;

    public AppointmentDto() {
    }

    public AppointmentDto(Long id, Long doctorId, LocalDate date, LocalTime startTime, LocalTime endTime, String location, String status) {
        this.id = id;
        this.doctorId = doctorId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.status = status;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


}
