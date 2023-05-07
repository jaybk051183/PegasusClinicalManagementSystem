package nl.novi.pegasusclinicalmanagementsystem.dtos;

public class InvoiceReportDto {
    private Long invoiceId;
    private String patientName;
    private Double invoiceAmount;
    private String paymentStatus;

    public InvoiceReportDto(Long invoiceId, String patientName, Double invoiceAmount, String paymentStatus) {
        this.invoiceId = invoiceId;
        this.patientName = patientName;
        this.invoiceAmount = invoiceAmount;
        this.paymentStatus = paymentStatus;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Double getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(Double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
