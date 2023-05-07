package nl.novi.pegasusclinicalmanagementsystem.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentReportDto {

    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate date;

    @JsonFormat(pattern = "HH:mm:ss")
    public LocalTime startTime;

    public String doctorName;
    public String patientName;

    public AppointmentReportDto() {
    }

    public AppointmentReportDto(LocalDate date, LocalTime startTime, String doctorName, String patientName) {
        this.date = date;
        this.startTime = startTime;
        this.doctorName = doctorName;
        this.patientName = patientName;
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

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
}

