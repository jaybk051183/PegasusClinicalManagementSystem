package nl.novi.pegasusclinicalmanagementsystem.dtos;

public class MedicalRecordDto {

    private Long patientId;
    private String notes;

    public MedicalRecordDto() {
    }

    public MedicalRecordDto(Long patientId, String notes) {
        this.patientId = patientId;
        this.notes = notes;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

