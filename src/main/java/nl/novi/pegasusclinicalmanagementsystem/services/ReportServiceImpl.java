package nl.novi.pegasusclinicalmanagementsystem.services;

import nl.novi.pegasusclinicalmanagementsystem.dtos.AppointmentReportDto;
import nl.novi.pegasusclinicalmanagementsystem.dtos.InvoiceReportDto;
import nl.novi.pegasusclinicalmanagementsystem.repositories.AppointmentRepository;
import nl.novi.pegasusclinicalmanagementsystem.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    private final AppointmentRepository appointmentRepository;
    private final InvoiceRepository invoiceRepository;

    public ReportServiceImpl(AppointmentRepository appointmentRepository, InvoiceRepository invoiceRepository) {
        this.appointmentRepository = appointmentRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public List<AppointmentReportDto> generateAppointmentReport() {
        return appointmentRepository.findAppointmentsForReport();
    }

    @Override
    public List<InvoiceReportDto> generateInvoiceReport() {
        return invoiceRepository.findInvoicesForReport();
    }
}
