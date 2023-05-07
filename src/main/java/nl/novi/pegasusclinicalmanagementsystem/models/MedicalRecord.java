package nl.novi.pegasusclinicalmanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Patient patient;

    @OneToOne
    FileUploadResponse file;

    public MedicalRecord() {
    }

    public MedicalRecord(Patient patient, String notes) {
        this.patient = patient;
        this.notes = notes;
    }

    public FileUploadResponse getFile() {
        return file;
    }

    public void setFile(FileUploadResponse file) {
        this.file = file;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
