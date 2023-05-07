package nl.novi.pegasusclinicalmanagementsystem.dtos;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

public class DoctorDto {

    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private String emailAddress;
    private String specialization;
    private Set<DayOfWeek> availableDays;
    private Set<LocalTime> availableTimes;

    public DoctorDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Set<DayOfWeek> getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(Set<DayOfWeek> availableDays) {
        this.availableDays = availableDays;
    }

    public Set<LocalTime> getAvailableTimes() {
        return availableTimes;
    }

    public void setAvailableTimes(Set<LocalTime> availableTimes) {
        this.availableTimes = availableTimes;
    }
}
