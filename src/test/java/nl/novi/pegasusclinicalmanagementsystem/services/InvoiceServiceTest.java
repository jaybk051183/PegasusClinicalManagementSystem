package nl.novi.pegasusclinicalmanagementsystem.services;

import nl.novi.pegasusclinicalmanagementsystem.dtos.InvoiceDto;
import nl.novi.pegasusclinicalmanagementsystem.models.Invoice;
import nl.novi.pegasusclinicalmanagementsystem.repositories.InvoiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class InvoiceServiceTest {

    @InjectMocks
    private InvoiceService invoiceService;

    @Mock
    private InvoiceRepository invoiceRepository;

    private Invoice testInvoice;

    @BeforeEach
    public void setUp() {
        testInvoice = new Invoice();
        testInvoice.setId(1L);
        testInvoice.setInvoiceAmount(100.0);
        testInvoice.setPaid(false);
        testInvoice.setSubmitted(true);
    }

    @Test
    public void getAllInvoices() {
        when(invoiceRepository.findAll()).thenReturn(Arrays.asList(testInvoice));
        List<InvoiceDto> invoiceList = invoiceService.getAllInvoices();
        assertEquals(1, invoiceList.size());
        assertEquals(testInvoice.getId(), invoiceList.get(0).getId());
    }

    @Test
    public void getInvoice() {
        when(invoiceRepository.findById(anyLong())).thenReturn(Optional.of(testInvoice));
        InvoiceDto foundInvoice = invoiceService.getInvoice(1L);
        assertEquals(testInvoice.getId(), foundInvoice.getId());
    }

    @Test
    public void addInvoice() {
        InvoiceDto invoiceDto = new InvoiceDto();
        invoiceDto.setInvoiceAmount(200.0);
        invoiceDto.setPaid(false);
        invoiceDto.setSubmitted(true);

        when(invoiceRepository.save(any(Invoice.class))).thenReturn(testInvoice);
        InvoiceDto savedInvoice = invoiceService.addInvoice(invoiceDto);
        assertNotNull(savedInvoice);
        assertEquals(testInvoice.getInvoiceAmount(), savedInvoice.getInvoiceAmount());
    }

    @Test
    public void updateInvoice() {
        InvoiceDto updatedInvoiceDto = new InvoiceDto();
        updatedInvoiceDto.setInvoiceAmount(200.0);
        updatedInvoiceDto.setPaid(true);
        updatedInvoiceDto.setSubmitted(true);

        when(invoiceRepository.findById(anyLong())).thenReturn(Optional.of(testInvoice));
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(testInvoice);

        InvoiceDto updatedInvoice = invoiceService.updateInvoice(1L, updatedInvoiceDto);
        assertNotNull(updatedInvoice);
        assertEquals(updatedInvoiceDto.getInvoiceAmount(), updatedInvoice.getInvoiceAmount());
    }

    @Test
    public void deleteInvoice() {
        when(invoiceRepository.findById(anyLong())).thenReturn(Optional.of(testInvoice));
        invoiceService.deleteInvoice(1L);
        verify(invoiceRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void markInvoiceAsPaid() {
        when(invoiceRepository.findById(anyLong())).thenReturn(Optional.of(testInvoice));
        invoiceService.markInvoiceAsPaid(1L);
        assertTrue(testInvoice.isPaid());
    }
}
