package nl.novi.pegasusclinicalmanagementsystem.dtos;

import java.util.List;

public class AppointmentSearchResponseDto {

    private String message;
    private List<AppointmentDto> availableAppointments;

    public AppointmentSearchResponseDto() {
    }

    public AppointmentSearchResponseDto(String message, List<AppointmentDto> availableAppointments) {
        this.message = message;
        this.availableAppointments = availableAppointments;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<AppointmentDto> getAvailableAppointments() {
        return availableAppointments;
    }

    public void setAvailableAppointments(List<AppointmentDto> availableAppointments) {
        this.availableAppointments = availableAppointments;
    }
}
