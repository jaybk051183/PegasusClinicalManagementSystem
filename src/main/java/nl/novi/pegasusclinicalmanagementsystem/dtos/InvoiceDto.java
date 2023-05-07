package nl.novi.pegasusclinicalmanagementsystem.dtos;

public class InvoiceDto {

    private Long id;
    private double invoiceAmount;
    private boolean paid;
    private boolean overdue;
    private boolean submitted;

    public InvoiceDto() {
    }

    public InvoiceDto(Long id, double invoiceAmount, boolean paid, boolean overdue, boolean submitted) {
        this.id = id;
        this.invoiceAmount = invoiceAmount;
        this.paid = paid;
        this.overdue = overdue;
        this.submitted = submitted;
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

