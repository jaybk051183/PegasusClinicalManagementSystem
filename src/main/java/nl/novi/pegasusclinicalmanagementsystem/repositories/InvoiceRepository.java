package nl.novi.pegasusclinicalmanagementsystem.repositories;

import nl.novi.pegasusclinicalmanagementsystem.dtos.InvoiceReportDto;
import nl.novi.pegasusclinicalmanagementsystem.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Query("SELECT new nl.novi.pegasusclinicalmanagementsystem.dtos.InvoiceReportDto(i.id, CONCAT(p.firstName, ' ', p.lastName), i.invoiceAmount, CASE WHEN i.paid = true THEN 'Paid' ELSE 'Not Paid' END) FROM Invoice i JOIN i.patient p")
    List<InvoiceReportDto> findInvoicesForReport();

}

