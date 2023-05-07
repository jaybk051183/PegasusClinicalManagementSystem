package nl.novi.pegasusclinicalmanagementsystem.controllers;

import nl.novi.pegasusclinicalmanagementsystem.dtos.InvoiceDto;
import nl.novi.pegasusclinicalmanagementsystem.services.InvoiceService;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/invoices")
    public ResponseEntity<List<InvoiceDto>> getAllInvoices() {
        List<InvoiceDto> invoiceDtos = invoiceService.getAllInvoices();
        return ResponseEntity.ok(invoiceDtos);
    }

    @GetMapping("/invoices/{id}")
    public ResponseEntity<InvoiceDto> getInvoice(@PathVariable("id") Long id) {
        InvoiceDto invoiceDto = invoiceService.getInvoice(id);
        return ResponseEntity.ok(invoiceDto);
    }

    @PostMapping("/invoices")
    public ResponseEntity<InvoiceDto> addInvoice(@RequestBody InvoiceDto invoiceDto) {
        InvoiceDto createdInvoiceDto = invoiceService.addInvoice(invoiceDto);
        return ResponseEntity.created(null).body(createdInvoiceDto);
    }

    @PutMapping("/invoices/{id}")
    public ResponseEntity<InvoiceDto> updateInvoice(@PathVariable("id") Long id, @RequestBody InvoiceDto invoiceDto) {
        InvoiceDto updatedInvoiceDto = invoiceService.updateInvoice(id, invoiceDto);
        return ResponseEntity.ok(updatedInvoiceDto);
    }

    @DeleteMapping("/invoices/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable("id") Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/invoices/{id}/mark-paid")
    public ResponseEntity<Void> markInvoiceAsPaid(@PathVariable("id") Long id) {
        invoiceService.markInvoiceAsPaid(id);
        return ResponseEntity.noContent().build();
    }

        @GetMapping("/invoices/{id}/download")
        public ResponseEntity<byte[]> downloadInvoice(@PathVariable("id") Long id) {
            byte[] pdfData = invoiceService.generateInvoicePdf(id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename("invoice_" + id + ".pdf").build());
            return ResponseEntity.ok().headers(headers).body(pdfData);
        }

    }


