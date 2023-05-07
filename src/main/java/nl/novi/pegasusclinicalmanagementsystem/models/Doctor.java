package nl.novi.pegasusclinicalmanagementsystem.models;

import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private String emailAddress;
    private String specialization;

    @ElementCollection
    @Column(name = "available_days", columnDefinition = "smallint")
    @Enumerated(EnumType.ORDINAL)
    private Set<DayOfWeek> availableDays;

    @ElementCollection
    private Set<LocalTime> availableTimes;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments;

    public Doctor() {
    }

    public Doctor(Long id, String name, String address, String phoneNumber, String emailAddress, String specialization, Set<DayOfWeek> availableDays, Set<LocalTime> availableTimes, List<Appointment> appointments) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.specialization = specialization;
        this.availableDays = availableDays;
        this.availableTimes = availableTimes;
        this.appointments = appointments;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
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

