package nl.novi.pegasusclinicalmanagementsystem.repositories;

import nl.novi.pegasusclinicalmanagementsystem.models.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    Optional<MedicalRecord> findByPatientId(Long patientId);
}
