package nl.novi.pegasusclinicalmanagementsystem.services;

import com.itextpdf.layout.Document;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import nl.novi.pegasusclinicalmanagementsystem.dtos.InvoiceDto;
import nl.novi.pegasusclinicalmanagementsystem.exceptions.RecordNotFoundException;
import nl.novi.pegasusclinicalmanagementsystem.models.Invoice;
import nl.novi.pegasusclinicalmanagementsystem.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public List<InvoiceDto> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return invoices.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public InvoiceDto getInvoice(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Invoice not found"));
        return convertToDto(invoice);
    }

    public InvoiceDto addInvoice(InvoiceDto invoiceDto) {
        Invoice invoice = convertToEntity(invoiceDto);
        Invoice savedInvoice = invoiceRepository.save(invoice);
        return convertToDto(savedInvoice);
    }

    public InvoiceDto updateInvoice(Long id, InvoiceDto invoiceDto) {
        Invoice existingInvoice = invoiceRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Invoice not found"));
        existingInvoice.setInvoiceAmount(invoiceDto.getInvoiceAmount());
        existingInvoice.setPaid(invoiceDto.isPaid());
        existingInvoice.setSubmitted(invoiceDto.isSubmitted());
        Invoice updatedInvoice = invoiceRepository.save(existingInvoice);
        return convertToDto(updatedInvoice);
    }

    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }

    public void markInvoiceAsPaid(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Invoice not found"));
        invoice.setPaid(true);
        invoiceRepository.save(invoice);
    }

    public byte[] generateInvoicePdf(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Invoice not found"));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Invoice ID: " + invoice.getId()));
        document.add(new Paragraph("Invoice Amount: " + invoice.getInvoiceAmount()));
        document.add(new Paragraph("Paid: " + invoice.isPaid()));
        document.add(new Paragraph("Submitted: " + invoice.isSubmitted()));

        document.close();

        return outputStream.toByteArray();
    }

    private InvoiceDto convertToDto(Invoice invoice) {
        InvoiceDto invoiceDto = new InvoiceDto();
        invoiceDto.setId(invoice.getId());
        invoiceDto.setInvoiceAmount(invoice.getInvoiceAmount());
        invoiceDto.setPaid(invoice.isPaid());
        invoiceDto.setSubmitted(invoice.isSubmitted());
        return invoiceDto;
    }

    private Invoice convertToEntity(InvoiceDto invoiceDto) {
        Invoice invoice = new Invoice();
        invoice.setInvoiceAmount(invoiceDto.getInvoiceAmount());
        invoice.setPaid(invoiceDto.isPaid());
        invoice.setSubmitted(invoiceDto.isSubmitted());
        return invoice;
    }
}
