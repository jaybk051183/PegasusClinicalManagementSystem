package nl.novi.pegasusclinicalmanagementsystem.models;

import jakarta.persistence.*;

@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double invoiceAmount;
    private boolean paid;
    private boolean overdue;
    private boolean submitted;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public Invoice() {
    }

    public Invoice(Long id, double invoiceAmount, boolean paid, boolean overdue, boolean submitted, Patient patient) {
        this.id = id;
        this.invoiceAmount = invoiceAmount;
        this.paid = paid;
        this.overdue = overdue;
        this.submitted = submitted;
        this.patient = patient;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean isOverdue() {
        return overdue;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }
}
