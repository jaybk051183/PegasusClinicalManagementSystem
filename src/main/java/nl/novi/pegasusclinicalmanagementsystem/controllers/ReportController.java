package nl.novi.pegasusclinicalmanagementsystem.controllers;

import nl.novi.pegasusclinicalmanagementsystem.dtos.AppointmentReportDto;
import nl.novi.pegasusclinicalmanagementsystem.dtos.InvoiceReportDto;
import nl.novi.pegasusclinicalmanagementsystem.services.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/appointments")
    public List<AppointmentReportDto> getAppointmentsReport() {
        return reportService.generateAppointmentReport();
    }

    @GetMapping("/invoices")
    public List<InvoiceReportDto> getInvoicesReport() {
        return reportService.generateInvoiceReport();
    }
}

