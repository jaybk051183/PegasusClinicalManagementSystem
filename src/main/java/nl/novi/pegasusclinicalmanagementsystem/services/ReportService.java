package nl.novi.pegasusclinicalmanagementsystem.services;

import nl.novi.pegasusclinicalmanagementsystem.dtos.AppointmentReportDto;
import nl.novi.pegasusclinicalmanagementsystem.dtos.InvoiceReportDto;

import java.util.List;

public interface ReportService {
    List<AppointmentReportDto> generateAppointmentReport();
    List<InvoiceReportDto> generateInvoiceReport();
}

